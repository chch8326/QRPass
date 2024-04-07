package com.qurepass.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatientDAO {

	// 환자번호
	private int patNo;
	// 환자명
	private String patNm;
	// 주민등록번호
	private String juminNo;
	// 성별
	private char gender;
	// 전화번호
	private String telNo;
	
}
