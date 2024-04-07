package com.qurepass.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrescriptionHisDAO {
	
	// 의약품번호
	private String uiyakpNo;
	// 처방일자
	private String presDt;
	// 처방번호
	private int presNo;
	// 환자주민등록번호
	private String patJuminNo;
	// 의약품명
	private String uiyakpNm;
	// 1회 투약량
	private float tuyak1Amt;
	// 1회 투약횟수
	private int tuyak1Cnt;
	// 총투약일수
	private int totToyakIlsu;
	// 급여구분
	private int geubyeoGbn;
	
}
