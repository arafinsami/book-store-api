package com.sami.controller;

import static com.sami.enums.Action.SAVE;
import static com.sami.enums.Action.UPDATE;
import static com.sami.utils.ApiError.error;
import static com.sami.utils.ApiResponseBuilder.errors;
import static com.sami.utils.ApiResponseBuilder.success;
import static com.sami.utils.Constants.BOOK_SAVE;
import static com.sami.utils.Constants.BOOK_UPDATE;
import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sami.dto.BookDto;
import com.sami.entity.Book;
import com.sami.exceptions.AppException;
import com.sami.service.BookService;
import com.sami.utils.FileUpload;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "book")
@RequiredArgsConstructor
public class BookController extends FileUpload {

	private MultipartFile file = null;

	private final BookService bookService;
	
	@Value("${deletionPath}")
	String deletionPath;

	@PostMapping("/save")
	@PreAuthorize("hasAuthority('BOOK_SAVE')")
	public ResponseEntity<JSONObject> save(@Valid @RequestBody BookDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ok(errors(error(bindingResult)).getJson());
		}

		Book book = dto.to();

		bookService.save(book, SAVE, BOOK_SAVE);
		return ok(success(BookDto.from(book)).getJson());
	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('BOOK_UPDATE')")
	public ResponseEntity<JSONObject> update(@Valid @RequestBody BookDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ok(errors(error(bindingResult)).getJson());
		}

		Book book = bookService.findById(dto.getId()).orElseThrow(AppException::new);
		dto.update(book);
		book = bookService.save(book, UPDATE, BOOK_UPDATE);
		return ok(success(BookDto.from(book)).getJson());
	}

	@GetMapping("/details/{bookId}")
	@PreAuthorize("hasAuthority('BOOK_DETAILS')")
	public ResponseEntity<JSONObject> findById(@PathVariable Long bookId) {

		Book book = bookService.findById(bookId).orElseThrow(AppException::new);
		return ok(success(BookDto.from(book)).getJson());
	}

	@GetMapping("/lists")
	@PreAuthorize("hasAuthority('BOOK_LIST')")
	public ResponseEntity<JSONObject> findAll() {
		List<BookDto> dtos = bookService.findAll().stream().map(BookDto::from).collect(Collectors.toList());
		return ok(success(dtos).getJson());
	}

	@PostMapping("/save/image")
	public ResponseEntity<JSONObject> upload(@RequestParam("id") Long id, HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		Book book = bookService.findById(id).orElseThrow(AppException::new);

		String fileName = book.getId() + ".png";

		uploadImage(this.file, request, fileName);

		book.setPhoto(fileName);

		book = bookService.save(book, UPDATE, BOOK_UPDATE);

		return ok(success(BookDto.from(book)).getJson());
	}

	@PostMapping("/update/image")
	public ResponseEntity<JSONObject> updateImagePost(@RequestParam("id") Long id, HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		Book book = bookService.findById(id).orElseThrow(AppException::new);

		String fileName = book.getId() + ".png";

		updateImage(this.file, request, fileName);

		book.setPhoto(fileName);

		book = bookService.save(book, UPDATE, BOOK_UPDATE);

		return ok(success(BookDto.from(book)).getJson());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<JSONObject> delete(@PathVariable("id") Long id) {

		bookService.delete(id);
		return ok(success("Book Has Been Deleted").getJson());
	}
}
