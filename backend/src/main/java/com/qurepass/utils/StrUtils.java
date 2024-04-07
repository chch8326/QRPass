package com.qurepass.utils;

public class StrUtils {
	
	/**
	 * yyyy년 mm월 dd일 format 문자열 출력
	 * @param str
	 * @return
	 * @author 최창현
	 */
	public static String getPresDtFormat(String str) {
		String presDt = null;
		String year   = str.substring(0, 4) + "년 ";
		String month  = str.substring(4, 6) + "월 ";
		String day    = str.substring(6, 8) + "일";
		presDt        = year + month + day;
		
		return presDt;
	}
	
	/**
	 * 주민번호 format 문자열 출력
	 * @param str
	 * @return
	 * @author 최창현
	 */
	public static String getJuminNoFormat(String str) {
		String juminNo = null;
		juminNo = str.substring(0, 6) + "-" + str.substring(6);
		
		return juminNo;
	}
	
	/**
	 * 번호 왼쪽에 0 붙여 문자열 변환
	 * @param num
	 * @return
	 * @author 최창현
	 */
	public static String getLapdZeroNo(int num) {
		String lapdZeroNum = null;
		lapdZeroNum = String.format("%05d", num);
		
		return lapdZeroNum;
	}

}
