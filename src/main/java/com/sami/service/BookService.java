package com.sami.service;

import static com.sami.constants.Comments.BOOK_SAVE_COMMENT;
import static com.sami.constants.Comments.BOOK_UPDATE;
import static com.sami.enums.Action.SAVE;
import static com.sami.enums.Action.UPDATE;
import static com.sami.enums.ModuleName.BOOK;
import static java.lang.String.valueOf;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sami.entity.Book;
import com.sami.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	private final ActionLogService actionLogService;

	@Transactional
	public Book save(Book book) {

		Book b = bookRepository.save(book);
		
		actionLogService.publishActivity(
				BOOK,
				SAVE,
				valueOf(b.getId()),
				BOOK_SAVE_COMMENT
		);
		return b;
	}
	
	@Transactional
	public Book update(Book book) {

		Book b = bookRepository.save(book);
		
		actionLogService.publishActivity(
				BOOK,
				UPDATE,
				valueOf(b.getId()),
				BOOK_UPDATE
		);
		return b;
	}

	public Optional<Book> findById(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		return book;
	}

	@Cacheable(cacheNames = "book-list")
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public void delete(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(null);
		bookRepository.delete(book);
	}
}
