package com.qurepass.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrescriptionDAO {
	
	// 처방일자
	private String presDt;
	// 처방번호
	private int presNo;
	// 환자주민등록번호
	private String patJuminNo;
	// 질병분류코드
	private String diseaseSign;
	// 용법
	private String useMth;
	// 사용기간
	private int useGigan;
	// 조제기관명
	private String jojeOrgNm;
	// 조제약사명
	private String jojeCmstNm;
	// 조제량
	private int jojeAmt;
	// 조제일자
	private String jojeDt;
	// 조제 시 참고사항
	private String jojeNote;
	// 처방목적
	private int presPerp;
	// 원내외처방여부
	private int wonaeOeYn;
	// 처방일시
	private String presIlsi;
	
}
