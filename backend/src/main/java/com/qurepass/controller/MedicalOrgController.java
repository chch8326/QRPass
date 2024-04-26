package com.qurepass.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qurepass.service.MedicalOrgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/medorg")
public class MedicalOrgController {
	
	private final MedicalOrgService medicalOrgService;

	/**
	 * 의료기관 상세 조회
	 * 처방전 등록 화면에서 현재 접속한 의료기관 정보가 자동으로 출력
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 최창현
	 */
	@PostMapping("/detail")
	public ResponseEntity<Map<String, Object>> getMedicalOrg(@RequestBody Map<String, Object> map) throws Exception {
		Map<String, Object> inMap   = (Map<String, Object>)map.get("data");
		Map<String, Object> outMap  = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		/* 의료기관 상세 조회 */
		dataMap = medicalOrgService.getMedicalOrg(inMap);
		outMap.put("medOrg", dataMap);
		
		return ResponseEntity.status(HttpStatus.OK).body(outMap);
	}
	
}
