package com.qurepass.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qurepass.mapper.PatientMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
	
	@Setter(onMethod_ = @Autowired)
	private PatientMapper patientMapper;
	
	@Override
	public List<Map<String, Object>> getPatientList() {
		return patientMapper.getPatientList();
	}
	
	@Override
	public int getPatientCnt() {
		return patientMapper.getPatientCnt();
	}
	
	@Override
	public Map<String, Object> getPatient(Map<String, Object> paramMap) {
		return patientMapper.getPatient(paramMap);
	}

}
