package com.sami.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hibernate-second-level")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(columnDefinition = "text")
	private String description;

}
