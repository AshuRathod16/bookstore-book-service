package com.bridgelabz.bookstorebookservice.repository;

import com.bridgelabz.bookstorebookservice.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookModel, Long> {

    Optional<BookModel> findByUserId(Long userId);
}
