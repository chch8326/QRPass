package com.qurepass.service;

import java.util.Map;

public interface MedicalOrgService {

	// 의료기관 조회
	public Map<String,Object> getMedicalOrg(Map<String, Object> inMap);
	
}
