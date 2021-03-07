package com.sami.controller;

import static com.sami.utils.ApiResponseBuilder.success;
import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sami.dto.BookDto;
import com.sami.entity.Book;
import com.sami.service.BookFilterservice;
import com.sami.utils.CommonHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "book-search")
@RequiredArgsConstructor
@Api(tags = "Book's Data")
public class IndexController {

	private final CommonHelper helper;
	
	private final BookFilterservice service;

	@PostMapping("/data")
	@ApiOperation(value = "book's data", response = BookDto.class)
	public ResponseEntity<JSONObject> getBookData(
			@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {

		helper.setPageSize(page, size);

		Map<String, Object> response = new HashMap<String, Object>();
		
		Map<String, Object> searchResult = service.getData(title, page, size);

		List<Book> books = (List<Book>) searchResult.get("lists");

		List<BookDto> dtos = books.stream().map(BookDto::from)
				.collect(Collectors.toList());
		
		helper.getCommonData(page, size, searchResult, response, dtos);
		
		return ok(success(response).getJson());
	}
}
