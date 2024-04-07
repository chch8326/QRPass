package com.qurepass.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qurepass.service.PrescriptionService;
import com.qurepass.utils.QRUtils;
import com.qurepass.utils.StrUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/qr")
public class QRController {
	
	@Value("${qr.file.path}")
	private String qrPath;
	
	private final PrescriptionService prescriptionService;
	
	@PostMapping("/create")
	public void qrView(@RequestBody Map<String, Object> map, HttpServletResponse response) throws Exception {
		Map<String, Object> inMap            = (Map<String, Object>)map.get("data");
		List<Map<String, Object>> uiyakpList = (List<Map<String, Object>>)inMap.get("uiyakpList");
		
		int width       = 500;
		int height      = 500;
		String imgType  = "png";
		String filePath = qrPath;
		String presDt   = (String)inMap.get("presDt");
		String patNm    = (String)inMap.get("patNm");
		String juminNo  = (String)inMap.get("juminNo");
		int presNo      = prescriptionService.getMaxPresNoDetail(presDt, juminNo);
		
		/* QR code 명 세팅 */
		String strPresNo   = StrUtils.getLapdZeroNo(presNo);
		String fileName    = presDt + "_" + strPresNo + "_" + patNm;
		
		/* 데이터 세팅 */
		List<String> itemSeqList    = new ArrayList<String>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("presDt", presDt);
		dataMap.put("presNo", strPresNo);
		dataMap.put("patNm" , patNm);
		
		for(Map<String, Object> pMap : uiyakpList) {
			String itemSeq = (String)pMap.get("itemSeq");
			itemSeqList.add(itemSeq);
		}
		
		dataMap.put("itemSeq", itemSeqList);
		String content = dataMap.toString();
		log.info("QR 데이터 확인: " + content);
		
		/* QR code 생성 */
		try {
			QRUtils qrUtils = new QRUtils();
			
			File qrFile = qrUtils.createQRimageFile(filePath, fileName, content, imgType, width, height);
			byte fileBytes[] = FileCopyUtils.copyToByteArray(qrFile);
			
			/* response 세팅 */
			response.setContentType("application/octet-stream");
			response.setContentLength(fileBytes.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
			response.setHeader("fileName", URLEncoder.encode(fileName, "UTF-8"));
			response.getOutputStream().write(fileBytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
