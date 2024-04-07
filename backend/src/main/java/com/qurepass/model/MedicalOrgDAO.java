package com.qurepass.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MedicalOrgDAO {
	
	// 의료기관 일련번호
	private int medOrgSer;
	// 의료기관명
	private String medOrgNm;
	// 의료기관코드
	private String medOrgCode;
	// 의료기관전화번호
	private String medOrgTel;
	// 의료기관팩스번호
	private String medOrgFaxNo;
	// 의료기관 이메일
	private String medOrgEmail;
	// 의사성명
	private String docNm;
	// 면허종별
	private String licenseType;
	// 면허번호
	private String licenseNo;
	// 요양기관기호
	private String yoyangOrgSign;

}
