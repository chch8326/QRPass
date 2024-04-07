package com.qurepass.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MedicalOrgMapper {
	
	// 의료기관 조회
	public Map<String,Object> getMedicalOrg(Map<String, Object> inMap);
	
}
