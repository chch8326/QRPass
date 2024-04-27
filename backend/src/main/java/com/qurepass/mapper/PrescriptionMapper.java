package com.qurepass.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PrescriptionMapper {
	
	// 처방전 조회
	public List<Map<String, Object>> getPrescriptionList(Map<String, Object> paramMap);
	
	// 처방전 목록 갯수
	public int getPrescriptionCnt(Map<String, Object> paramMap);
	
	// 해당 교부일자의 최대 교부번호 산출
	public int getMaxPresNo(String presDt);
	
	// 교부일자와 주민번호로 최대 교부번호 산출
	public int getMaxPresNoDetail(@Param("presDt") String param1, @Param("juminNo") String param2);
	
	// 처방전 등록
	public int prescriptionRegister(Map<String, Object> paramMap);
	
	// 처방전내역 등록
	public int prescriptionHisRegister(Map<String, Object> paramMap);
	
	// 의약품 조회
	public List<Map<String, Object>> getPrescriptionHisList(Map<String, Object> paramMap);
	
}
