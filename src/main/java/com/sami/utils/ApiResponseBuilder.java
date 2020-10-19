package com.sami.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class ApiResponseBuilder implements ApiResponse {

	private Object data;
	private Object errors;
	private ResponseType responseType;

	public ApiResponseBuilder(ResponseType responseType) {
		this.responseType = responseType;
	}

	public static ApiResponse success(Object data) {

		ApiResponseBuilder responseBuilder = new ApiResponseBuilder(ResponseType.DATA);
		responseBuilder.data = data;
		return responseBuilder;
	}

	public static ApiResponse errors(Object errors) {

		ApiResponseBuilder responseBuilder = new ApiResponseBuilder(ResponseType.ERROR);
		responseBuilder.errors = errors;
		return responseBuilder;
	}

	@Override
	public JSONObject getJson() {

		Map<String, Object> map = new HashMap<>();

		switch (this.responseType) {
		case DATA:
			map.put("data", data);
			break;

		case ERROR:
			map.put("errors", errors);
			break;
		}
		return new JSONObject(map);
	}

}
