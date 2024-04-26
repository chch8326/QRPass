package com.qurepass.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.qurepass.service.PrescriptionService;
import com.qurepass.utils.PagingUtils;
import com.qurepass.utils.StrUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/pres")
public class PrescriptionController {
	
	@Value("${pdf.file.path}")
	private String pdfPath;
	
	@Value("${css.file.path}")
	private String cssPath;
	
	@Value("${font.file.path}")
	private String fontPath;
	
	private final PrescriptionService prescriptionService;
	
	/**
	 * 처방전 목록 조회
	 * @return
	 * @throws Exception
	 * @author 최창현
	 */
	@PostMapping("/list")
	public ResponseEntity<Map<String, Object>> getPrescriptionList(@RequestBody Map<String, Object> map) throws Exception {
		Map<String, Object> inMap   = (Map<String, Object>)map.get("data");
		Map<String, Object> outMap  = new HashMap<String, Object>();		
		
		/* 처방전 목록 갯수 조회 */
		int prescriptionCnt = prescriptionService.getPrescriptionCnt(inMap);
		
		/* 페이징 처리 */
		PagingUtils paging = new PagingUtils();
		int pageNo         = Integer.parseInt(String.valueOf(inMap.get("pageNo")));
		int amount         = Integer.parseInt(String.valueOf(inMap.get("amount")));
		Object pagingInfo  = paging.pagingProcess(pageNo, amount, prescriptionCnt);
		
		/* 처방전 목록 조회 */
		List<Map<String, Object>> prescriptionList = prescriptionService.getPrescriptionList(inMap);
		
		outMap.put("presCnt"   , prescriptionCnt);
		outMap.put("pagingInfo", pagingInfo);
		outMap.put("presList"  , prescriptionList);
		
		return ResponseEntity.status(HttpStatus.OK).body(outMap);
	}
	
	/**
	 * 처방전 등록
	 * @param map
	 * @throws Exception
	 * @author 최창현
	 */
	@PostMapping("/register")
	@Transactional(rollbackFor = Exception.class)
	public boolean getPrescriptionRegister(@RequestBody Map<String, Object> map) throws Exception {
		int presHisRegCnt            = 0;
		int prescriptionRegisterCnt  = 0;
		Map<String, Object> inMap    = (Map<String, Object>)map.get("data");
		
		/* 처방전 데이터 세팅 */
		Map<String, Object> dataMap1 = new HashMap<String, Object>();
		
		String presDt                = (String)inMap.get("presDt");
		int presNo                   = prescriptionService.getMaxPresNo(presDt) + 1;
		String juminNo               = (String)inMap.get("juminNo");
		String diseaseSign           = (String)inMap.get("diseaseSign");
		int presPrep                 = Integer.parseInt(String.valueOf(inMap.get("presPrep")));
		int wonaeOeYn                = Integer.parseInt(String.valueOf(inMap.get("wonaeOeYn")));
		int useGigan                 = Integer.parseInt(String.valueOf(inMap.get("useGigan")));
		String useMth                = (String)inMap.get("useMth");
		String presIlsi              = (String)inMap.get("presIlsi");
		
		dataMap1.put("presDt"        , presDt);
		dataMap1.put("presNo"        , presNo);
		dataMap1.put("juminNo"       , juminNo);
		dataMap1.put("diseaseSign"   , diseaseSign);
		dataMap1.put("presPrep"      , presPrep);
		dataMap1.put("wonaeOeYn"     , wonaeOeYn);
		dataMap1.put("useGigan"      , useGigan);
		dataMap1.put("useMth"        , useMth);
		dataMap1.put("presIlsi"      , presIlsi);
		
		/* 처방전 등록 */
		prescriptionRegisterCnt = prescriptionService.prescriptionRegister(dataMap1);
		if(prescriptionRegisterCnt < 1) {
			return false;
		}
		
		/* 처방전 내역 데이터 세팅 */
		List<Map<String, Object>> uiyakpList = (List<Map<String, Object>>)inMap.get("uiyakpList");
		
		for(Map<String, Object> pMap : uiyakpList) {
			int prescriptionHisRegisterCnt = 0;
			Map<String, Object> dataMap2   = new HashMap<String, Object>();
			
			String itemSeq                 = (String)pMap.get("itemSeq");
			String itemName                = (String)pMap.get("itemName");
			float tuyak1Amt                = Float.parseFloat(String.valueOf(pMap.get("tuyak1Amt")));
			int tuyak1Cnt                  = Integer.parseInt(String.valueOf(pMap.get("tuyak1Cnt")));
			int totToyakIlsu               = Integer.parseInt(String.valueOf(pMap.get("totToyakIlsu")));
			int gubun                      = Integer.parseInt(String.valueOf(pMap.get("gubun")));
			
			dataMap2.put("itemSeq"         , itemSeq);
			dataMap2.put("presDt"          , presDt);
			dataMap2.put("presNo"          , presNo);
			dataMap2.put("juminNo"         , juminNo);
			dataMap2.put("itemName"        , itemName);
			dataMap2.put("tuyak1Amt"       , tuyak1Amt);
			dataMap2.put("tuyak1Cnt"       , tuyak1Cnt);
			dataMap2.put("totToyakIlsu"    , totToyakIlsu);
			dataMap2.put("gubun"           , gubun);
			
			/* 처방전 내역 등록 */
			prescriptionHisRegisterCnt = prescriptionService.prescriptionHisRegister(dataMap2);
			if(prescriptionHisRegisterCnt < 1) {
				return false;
			}
			
			if(prescriptionHisRegisterCnt > 0) {
				presHisRegCnt++;
			}
		}
		
		/* 처방전 등록 및 내역 검증 */
		if((prescriptionRegisterCnt > 0) && (uiyakpList.size() == presHisRegCnt)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 식품의약품안전처 의약품 낱알식별 정보 Open API JSON 파싱
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 최창현
	 */
	@PostMapping("/uiyakpoom")
	public ResponseEntity<Map<String, Object>> getUiyakpoomDataList(@RequestBody Map<String, Object> map) throws Exception {
		int pageNo        = 0;
		int numOfRows     = 0;
		String itemName   = null;
		String requestUrl = null;
		
		Map<String, Object> inMap  = (Map<String, Object>)map.get("data");
		Map<String, Object> outMap = new HashMap<String, Object>();

		/* 식품의약품안전처 의약품 낱알식별 정보 Open API 입력 파라미터 */
		pageNo            = (int)inMap.get("pageNo");
		numOfRows         = (int)inMap.get("numOfRows");
		itemName          = (String)inMap.get("keyword");
		String type       = "json";
		String serviceUrl = "https://apis.data.go.kr/1471000/MdcinGrnIdntfcInfoService01/getMdcinGrnIdntfcInfoList01";
		String serviceKey = "6sfs8QFlA1x9EQMGrvDlt%2BHX1cdvhTzyw5rq2gEUksl7S5XWAC5pq5At%2B9qV%2FKFmvbzTIhocB6%2F7rXohX7%2FFCg%3D%3D";
		
		/* 품목명 미검색 */
		if(itemName == null) {
			requestUrl = serviceUrl + "?serviceKey=" + serviceKey
									+ "&pageNo=" + pageNo 
									+ "&numOfRows=" + numOfRows 
									+ "&type=" + type;
		}
		/* 품목명 검색 */
		else {
			requestUrl = serviceUrl + "?serviceKey=" + serviceKey
									+ "&pageNo=" + pageNo 
									+ "&numOfRows=" + numOfRows 
									+ "&item_name=" + URLEncoder.encode(itemName, "UTF-8")    // 한글 인코딩
									+ "&type=" + type;
		}
		
		try {
			int totalCount        = 0;
			String result         = null;
			
			URL connUrl           = new URL(requestUrl);
			BufferedReader reader = new BufferedReader(new InputStreamReader(connUrl.openStream(), "UTF-8"));
			result                = reader.readLine();
			
			/* JSON으로 변환 */
			JSONParser jParser    = new JSONParser();
			JSONObject jObj       = (JSONObject)jParser.parse(result);
			JSONObject bodyObj    = (JSONObject)jObj.get("body");
			JSONArray jArr        = (JSONArray)bodyObj.get("items");
			totalCount            = Integer.parseInt(String.valueOf(bodyObj.get("totalCount")));
			
			outMap.put("totcalCnt", totalCount);
			outMap.put("uiyackpoomList", jArr);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(outMap);
	}
	
	/**
	 * 처방전 PDF 파일 생성
	 * @param map
	 * @throws Exception
	 * @author 최창현
	 */
	@PostMapping("/pdf")
	public boolean createPrescriptionPdf(@RequestBody Map<String, Object> map) throws Exception {
		Map<String, Object> inMap   = (Map<String, Object>)map.get("data");
		
		String presDt               = (String)inMap.get("presDt");
		String patNm                = (String)inMap.get("patNm");
		int presNo                  = prescriptionService.getMaxPresNo(presDt);
		
		/* PDF 생성되는 폴더 없을 경우 생성 */
		File file = new File(pdfPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		/* PDF 생성 */
		Document document           = new Document(PageSize.A4, 35, 35, 35, 35);    // 용지 및 여백 설정
		/* HTML 코드 생성 */
		String prescriptionHtmlCode = prescriptionHtmlTable(inMap);                 // 처방전 HTML 코드
		
		try {
			/* 해당 경로에 PDF 파일 생성 */
			FileOutputStream fos               = new FileOutputStream(pdfPath + presDt + "_" + StrUtils.getLapdZeroNo(presNo) + "_" + patNm +".pdf");
			PdfWriter writer                   = PdfWriter.getInstance(document, fos);
			writer.setInitialLeading(12.5f);
			document.open();
			
			/* CSS 세팅 */
			XMLWorkerHelper helper             = XMLWorkerHelper.getInstance();
			CSSResolver cssResolver            = new StyleAttrCSSResolver();
			CssFile cssFile                    = helper.getCSS(new FileInputStream(cssPath + "pdf.css"));
			cssResolver.addCss(cssFile);
			
			/* pipeline */
			XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontProvider.register(fontPath + "malgun.ttf", "MalgunGothic");
			CssAppliers cssAppliers            = new CssAppliersImpl(fontProvider); 
			HtmlPipelineContext htmlContext    = new HtmlPipelineContext(cssAppliers);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
			PdfWriterPipeline pdfPipeline      = new PdfWriterPipeline(document, writer);
			HtmlPipeline htmlPipeline          = new HtmlPipeline(htmlContext, pdfPipeline);
			CssResolverPipeline cssPipeline    = new CssResolverPipeline(cssResolver, htmlPipeline);
			
			/* XML 파싱 */
			XMLWorker worker                   = new XMLWorker(cssPipeline, true);
			XMLParser parser                   = new XMLParser(worker, Charset.forName("UTF-8")); 
			StringReader reader                = new StringReader(prescriptionHtmlCode);
			parser.parse(reader);
			
			/* 파일 닫기 */
			document.close();
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/* 처방전 HTML 테이블 코드 */
	private String prescriptionHtmlTable(Map<String, Object> map) {
		/* 처방전 정보 세팅 */
		String presDt              = StrUtils.getPresDtFormat((String)map.get("presDt"));
		String presNo              = StrUtils.getLapdZeroNo(prescriptionService.getMaxPresNo(presDt));
		String juminNo             = StrUtils.getJuminNoFormat((String)map.get("juminNo"));
		String diseaseSign         = (String)map.get("diseaseSign");
		if(diseaseSign == null) {
			diseaseSign = "";
		}    
		int presPrep               = Integer.parseInt(String.valueOf(map.get("presPrep")));
		int wonaeOeYn              = Integer.parseInt(String.valueOf(map.get("wonaeOeYn")));
		int useGigan               = Integer.parseInt(String.valueOf(map.get("useGigan")));
		String useMth              = (String)map.get("useMth");
		String patNm               = (String)map.get("patNm");
		
		/* 의료기관 정보 세팅 */
		Map<String, Object> medOrg = (Map<String, Object>)map.get("medOrg");
		String medOrgNm            = (String)medOrg.get("MED_ORG_NM");
		String medOrgTel           = (String)medOrg.get("MED_ORG_TEL");
		String medOrgFaxNo         = (String)medOrg.get("MED_ORG_FAX_NO");
		String medOrgEmail         = (String)medOrg.get("MED_ORG_EMAIL");
		String docNm               = (String)medOrg.get("DOC_NM");
		String licenseType         = (String)medOrg.get("LICENSE_TYPE");
		String licenseNo           = (String)medOrg.get("LICENSE_NO");
		String yoyangOrgSign       = (String)medOrg.get("YOYANG_ORG_SIGN");
		
		/* 처방전 HTML 코드 세팅 */
		String presHtmlTable = "<html><head></head><body style='font-family: MalgunGothic;'><div class='top'>( 약국 보관용 )</div><table border='1' class='table'>"
								+ "<thead><tr><td colspan='19'><div class='header'><b>처&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;방&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;전</b></div>"
								+ "<div style='padding-bottom: 5px;'>&nbsp;&nbsp;&nbsp;① 의료보험&nbsp;&nbsp;&nbsp; ② 의료급여&nbsp;&nbsp;&nbsp; ③ 산재보험&nbsp;&nbsp;&nbsp;"
								+ "④ 자동차보험&nbsp;&nbsp;&nbsp; ⑤ 기타(&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)&nbsp;&nbsp;&nbsp; 요양기관번호: " + yoyangOrgSign + "</div></td></tr></thead>"
								+ "<tbody><tr><td colspan='3' class='center'>교부 연원일 및 번호</td><td colspan='6'>" + presDt + " 제 " + presNo + "호</td><td rowspan='5' class='center'>"
								+ "<div>의</div><div>료</div><div>기</div><div>관</div></td><td colspan='3' class='center'>명칭</td><td colspan='6'>" + medOrgNm + "</td></tr>"
								+ "<tr><td rowspan='4' class='center'><div>환</div><div>자</div></td><td rowspan='2' colspan='2' class='center'>성명</td><td rowspan='2' colspan='6'>" + patNm + "</td>"
								+ "<td colspan='3' class='center'>전화번호</td><td colspan='6'>" + medOrgTel + "</td></tr><tr><td colspan='3' class='center'>팩스번호</td>"
								+ "<td colspan='6'>" + medOrgFaxNo + "</td><td rowspan='2' colspan='2' class='center'>주민등록번호</td><td rowspan='2' colspan='6'>" + juminNo + "</td></tr>"
								+ "<tr><td colspan='3' class='center'>e-mail</td><td colspan='6'>" + medOrgEmail + "</td></tr><tr><td rowspan='2' class='center'><div>질병</div><div>분류</div>"
								+ "<div>기호</div></td><td colspan='4' style='height: 19px;'>" + diseaseSign + "</td><td rowspan='2' colspan='3' class='center'><div>처&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;방</div>"
								+ "<div>의료인의</div><div>성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</div></td><td rowspan='2' colspan='5' class='center'><div>" + docNm + "</div><div>(서명 또는 날인)</div></td>"
								+ "<td colspan='2' class='center'>면허종별</td><td colspan='4' class='center'>" + licenseType + "</td></tr><tr><td colspan='4'></td><td colspan='2' class='center'>면허번호</td>"
								+ "<td colspan='4' class='center'>" + licenseNo + "</td></tr><tr><td colspan='19' style='height: 26px;'><b>&nbsp;&nbsp;&nbsp;※ 환자의 요구가 있을 때에는 질병분류기호를 기재하지 아니합니다.</b></td></tr>"
								+ "<tr><td colspan='9' class='center'>처방 의약품의 명칭</td><td colspan='2' class='center'><div>1회</div><div>투약량</div></td>"
								+ "<td class='center'><div>1일</div><div>투여</div><div>횟수</div></td><td class='center'><div>총</div><div>투약</div><div>일수</div></td>"
								+ "<td colspan='2' class='center'>급여구분</td><td colspan='4' class='center'>용&nbsp;&nbsp;법</td></tr>";			
								
		/* 의약품 목록 세팅*/
		List<Map<String, Object>> uiyakpList = (List<Map<String, Object>>)map.get("uiyakpList");
		
		for(int idx = 0; idx < 11; idx++) {
			String tmpHtmlCode = null;
			
			/* uiyakpList size 벗어날 시 에러나므로 분기처리 */
			if(idx < uiyakpList.size()) {
				Map<String, Object> tmpMap = uiyakpList.get(idx);    
				String itemName            = (String)tmpMap.get("itemName");
				float tuyak1Amt            = Float.parseFloat(String.valueOf(tmpMap.get("tuyak1Amt")));
				int tuyak1Cnt              = Integer.parseInt(String.valueOf(tmpMap.get("tuyak1Cnt")));
				int totToyakIlsu           = Integer.parseInt(String.valueOf(tmpMap.get("totToyakIlsu")));
				int gubun                  = Integer.parseInt(String.valueOf(tmpMap.get("gubun")));
				String strGubun            = null;
				if(gubun == 1) {
					strGubun = "급여";
				} else {
					strGubun = "";
				}
				
				if(idx == 0) {
					tmpHtmlCode = "<tr><td colspan='9' class='nowrap'>" + itemName + "</td><td colspan='2' class='center'>" + tuyak1Amt + "</td>"
									+ "<td class='center'>" + tuyak1Cnt + "</td><td class='center'>" + totToyakIlsu + "</td>"
									+ "<td colspan='2' class='center'>" + strGubun + "</td><td rowspan='10' colspan='4' style='vertical-align: top;'>" + useMth + "</td></tr>";
				} else if(idx == 10) {
					tmpHtmlCode = "<tr><td colspan='9' class='nowrap'>" + itemName + "</td><td colspan='2' class='center'>" + tuyak1Amt + "</td>"
									+ "<td class='center'>" + tuyak1Cnt + "</td><td class='center'>" + totToyakIlsu + "</td>"
									+ "<td colspan='2' class='center'>" + strGubun + "</td><td colspan='4' class='center'>조제 시 참고사항</td>";
				} else {
					tmpHtmlCode = "<tr><td colspan='9' class='nowrap'>" + itemName + "</td><td colspan='2' class='center'>" + tuyak1Amt + "</td>"
									+ "<td class='center'>" + tuyak1Cnt + "</td><td class='center'>" + totToyakIlsu + "</td>"
									+ "<td colspan='2' class='center'>" + strGubun + "</td></tr>";
				}
			} else {
				if(idx == 10) {
					tmpHtmlCode = "<tr><td colspan='9' class='void'></td><td colspan='2'></td><td></td><td></td><td colspan='2'></td>"
									+ "<td colspan='4' class='center'>조제 시 참고사항</td></tr>";
				} else {
					tmpHtmlCode = "<tr><td colspan='9' class='void'></td><td colspan='2'></td><td></td><td></td><td colspan='2'></td></tr>";
				}
			}
			
			presHtmlTable = presHtmlTable + tmpHtmlCode; 
		}
		
		presHtmlTable = presHtmlTable + "<tr><td colspan='15' class='center'>주사제 처방내역( 원내조제 ㅁ, 원외처방 ㅁ )</td><td rowspan='4' colspan='4'></td></tr>"
									  + "<tr><td colspan='9' style='height: 15px;'></td><td colspan='2' class='center'></td><td class='center'></td>"
									  + "<td class='center'></td><td colspan='2' class='center'></td></tr><tr><td colspan='9' style='height: 15px;'></td>"
									  + "<td colspan='2' class='center'></td><td class='center'></td><td class='center'></td><td colspan='2' class='center'></td></tr>"
									  + "<tr><td colspan='9' style='height: 15px;'></td><td colspan='2' class='center'></td><td class='center'></td>"
									  + "<td class='center'></td><td colspan='2' class='center'></td></tr><tr><td colspan='2' class='center'>사용기간</td>"
									  + "<td colspan='17'>&nbsp;&nbsp;교부일부터 (&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + useGigan + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;)일간 사용기간내에 약국에 제출하여야 합니다.</td>"
									  + "</tr><tr><td colspan='19' class='center'>의약품 조제내역</td></tr><tr><td rowspan='4' class='center'><div>조제</div><div>내역</div></td>"
									  + "<td class='center'><div>조제기관의</div><div>명칭</div></td><td colspan='11'></td><td colspan='6'><div>처방의 변경 · 수정 · 확인 ·</div><div>대체시 그 내용 등</div></td></tr>"
									  + "<tr><td class='center'>조제약사</td><td class='center'>성명</td><td colspan='10'></td><td rowspan='3' colspan='6'></td></tr>"
									  + "<tr><td class='center'><div>조제량</div><div>(조제일수)</div></td><td colspan='11'></td></tr>"
									  + "<tr><td class='center'>조제연원일</td><td colspan='11'></td></tr></tbody></table></body></html>";
		
		return presHtmlTable;
	}
		
}
