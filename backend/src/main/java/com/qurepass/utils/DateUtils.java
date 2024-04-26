package com.qurepass.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	/**
	 * 
	 * @return
	 * @author 최창현
	 */
	public static String getCurrentDate() {
		String currentDate = null;
		
		LocalDate localDate = LocalDate.now();
		currentDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		return currentDate;
	}

}
