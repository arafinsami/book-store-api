package com.sami.utils;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CommonHelper {

	public void getCommonData(Integer page, Integer size, Map<String, ?> searchResult, Map<String, Object> response,
			List<?> lists) {

		response.put("lists", lists);
		response.put("currentPage", searchResult.get("currentPage"));
		response.put("nextPage", searchResult.get("nextPage"));
		response.put("previousPage", searchResult.get("previousPage"));
		response.put("size", searchResult.get("size"));
		response.put("total", searchResult.get("total"));
	}

	public void getCommonData(Map<String, Object> response, List<?> lists) {
		response.put("lists", lists);
	}

	public void getCommonData(Map<String, Object> response, Object data) {
		response.put("data", data);
	}

	public void setPageSize(Integer page, Integer size) {
		if (page < 0 || size < 0) {
			page = 0;
			size = 10;
		} else if (size >= 50) {
			size = 50;
		}
	}
}
