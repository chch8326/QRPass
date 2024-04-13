package com.qurepass.utils;

import java.util.HashMap;
import java.util.Map;

public class PagingUtils {
	
	/**
	 * 페이징 처리
	 * @param page
	 * @param amount
	 * @param totalPresCnt
	 * @return
	 * @author 최창현
	 */
	public Map<String, Object> pagingProcess(int page, int amount, int totalPresCnt) {
		int startPage               = 0;
		int endPage                 = 0;
		int realEndPage             = 0;
		boolean prev                = false;
		boolean next                = false;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		endPage     = (int)(Math.ceil(page / (float)amount)) * amount;    // 마지막 페이지
		startPage   = endPage - (amount - 1);						      // 시작 페이지
		realEndPage = (int)(Math.ceil((totalPresCnt * 1.0) / amount));    // 실제 마지막 페이지
		
		if(endPage > realEndPage) endPage = realEndPage;                  // 실제 마지막 페이지로 변경
		prev = startPage > 1;
		next = endPage < realEndPage;
		
		dataMap.put("startPage", startPage);
		dataMap.put("endPage", endPage);
		dataMap.put("prev", prev);
		dataMap.put("next", next);
		
		return dataMap;
	}

}
