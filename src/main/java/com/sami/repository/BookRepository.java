package com.sami.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sami.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
