package com.bridgelabz.bookstorebookservice.service;

import com.bridgelabz.bookstorebookservice.dto.BookDTO;
import com.bridgelabz.bookstorebookservice.model.BookModel;
import com.bridgelabz.bookstorebookservice.util.Response;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    public BookModel addBook(BookDTO bookDTO, String token);
    public BookModel updateBook(BookDTO bookDTO, Long bookId, String token);
    public List<BookModel> getAllBooks(String token);
    public Optional<BookModel> getBookById(Long bookId, String token);
    public Response deleteBook(String token, Long bookId);
    public Response updateBookPrice(String token, Long bookId, double price);
    public Response updateBookQuantity(String token, Long bookId, Long quantity);

}
