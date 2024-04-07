package com.qurepass.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qurepass.mapper.MedicalOrgMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class MedicalOrgServiceImpl implements MedicalOrgService {
	
	@Setter(onMethod_ = @Autowired)
	private MedicalOrgMapper medicalOrgMapper;
	
	@Override
	public Map<String,Object> getMedicalOrg(Map<String, Object> inMap) {
		return medicalOrgMapper.getMedicalOrg(inMap);
	}

}
