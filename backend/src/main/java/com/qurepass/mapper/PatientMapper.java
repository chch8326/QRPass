package com.qurepass.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientMapper {

	// 환자 목록 조회
	public List<Map<String, Object>> getPatientList();
	
	// 환자 목록 갯수
	public int getPatientCnt();
	
	// 환자 상세 조회
	public Map<String, Object> getPatient(Map<String, Object> paramMap);
	
}
