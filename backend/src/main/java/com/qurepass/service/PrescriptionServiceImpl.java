package com.qurepass.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qurepass.mapper.PrescriptionMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

	@Setter(onMethod_ = @Autowired)
	private PrescriptionMapper prescriptionMapper;
	
	@Override
	public List<Map<String, Object>> getPrescriptionList(Map<String, Object> paramMap) {
		return prescriptionMapper.getPrescriptionList(paramMap);
	}
	
	@Override
	public int getPrescriptionCnt(Map<String, Object> paramMap) {
		return prescriptionMapper.getPrescriptionCnt(paramMap);
	}
	
	@Override
	public int getMaxPresNo(String presDt) {
		return prescriptionMapper.getMaxPresNo(presDt);
	}
	
	@Override
	public int getMaxPresNoDetail(String param1, String param2) {
		return prescriptionMapper.getMaxPresNoDetail(param1, param2);
	}
	
	@Override
	public int prescriptionRegister(Map<String, Object> paramMap) {
		return prescriptionMapper.prescriptionRegister(paramMap);
	}
	
	@Override
	public int prescriptionHisRegister(Map<String, Object> paramMap) {
		return prescriptionMapper.prescriptionHisRegister(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> getPrescriptionHisList(Map<String, Object> paramMap) {
		return prescriptionMapper.getPrescriptionHisList(paramMap);
	}
}
