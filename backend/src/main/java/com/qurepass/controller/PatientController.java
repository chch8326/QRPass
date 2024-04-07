package com.qurepass.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qurepass.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/pat")
public class PatientController {
	
	private final PatientService patientService;
	
	/**
	 * 환자 목록 조회
	 * @return
	 * @throws Exception
	 * @author 최창현
	 */
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> getPatientList() throws Exception {
		int patientCnt = 0;
		Map<String, Object> outMap = new HashMap<String, Object>();
		
		/* 환자 목록 갯수 조회 */
		patientCnt = patientService.getPatientCnt();
		outMap.put("patCnt", patientCnt);
		
		/* 환자 목록 조회 */
		List<Map<String, Object>> patientList = patientService.getPatientList();
		outMap.put("patList", patientList);
		
		return ResponseEntity.status(HttpStatus.OK).body(outMap);
	}
	
	/**
	 * 환자 상세 조회
	 * @param map
	 * @return
	 * @throws Exception
	 * @author 최창현
	 */
	@PostMapping("/detail")
	public ResponseEntity<Map<String, Object>> getPatientDeail(@RequestBody Map<String, Object> map) throws Exception {
		Map<String, Object> inMap = (Map<String, Object>)map.get("data");
		Map<String, Object> outMap = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		/* 환자 상세 조회 */
		dataMap = patientService.getPatient(inMap);
		outMap.put("patInfo", dataMap);
		
		return ResponseEntity.status(HttpStatus.OK).body(outMap);
	}

}
