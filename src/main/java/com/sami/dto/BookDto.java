package com.sami.dto;

import com.sami.entity.Book;

import lombok.Data;

@Data
public class BookDto {

	private Long id;

	private String title;

	private String author;

	private String publisher;

	private String publicationDate;

	private String language;

	private String category;

	private Integer pages;

	private String format;

	private String isbn;

	private Double shippingWeight;

	private Double listPrice;

	private Double ourPrice;

	private Boolean active = true;

	private Integer stockNumber;

	private String photo;

	private String description;

	public Book to() {

		Book book = new Book();
		book.setTitle(title);
		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setPublicationDate(publicationDate);
		book.setLanguage(language);
		book.setCategory(category);
		book.setPages(pages);
		book.setFormat(format);
		book.setIsbn(isbn);
		book.setShippingWeight(shippingWeight);
		book.setListPrice(listPrice);
		book.setOurPrice(ourPrice);
		book.setActive(active);
		book.setStockNumber(stockNumber);
		book.setDescription(description);
		return book;
	}

	public Book update(Book book) {

		book.setTitle(title);
		book.setAuthor(author);
		book.setPublisher(publisher);
		book.setPublicationDate(publicationDate);
		book.setLanguage(language);
		book.setCategory(category);
		book.setPages(pages);
		book.setFormat(format);
		book.setIsbn(isbn);
		book.setShippingWeight(shippingWeight);
		book.setListPrice(listPrice);
		book.setOurPrice(ourPrice);
		book.setActive(active);
		book.setStockNumber(stockNumber);
		book.setDescription(description);
		return book;
	}

	public static BookDto from(Book book) {

		BookDto dto = new BookDto();
		dto.setId(book.getId());
		dto.setTitle(book.getTitle());
		dto.setAuthor(book.getAuthor());
		dto.setPublisher(book.getPublisher());
		dto.setPublicationDate(book.getPublicationDate());
		dto.setLanguage(book.getLanguage());
		dto.setCategory(book.getCategory());
		dto.setPages(book.getPages());
		dto.setFormat(book.getFormat());
		dto.setIsbn(book.getIsbn());
		dto.setShippingWeight(book.getShippingWeight());
		dto.setListPrice(book.getListPrice());
		dto.setOurPrice(book.getOurPrice());
		dto.setActive(book.getActive());
		dto.setStockNumber(book.getStockNumber());
		dto.setPhoto(book.getPhoto());
		dto.setDescription(book.getDescription());
		return dto;
	}
}
