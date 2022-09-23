package com.bridgelabz.bookstorebookservice.controller;

import com.bridgelabz.bookstorebookservice.dto.BookDTO;
import com.bridgelabz.bookstorebookservice.model.BookModel;
import com.bridgelabz.bookstorebookservice.service.IBookService;
import com.bridgelabz.bookstorebookservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 20-09-2022
 * Purpose : controller for the Book Service
 */

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    IBookService bookService;


    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to bookstore project";
    }

    @PostMapping("/addBook")
    public ResponseEntity<Response> createNote(@Valid @RequestBody BookDTO bookDTO, @RequestHeader String token) {
        BookModel noteModel = bookService.addBook(bookDTO, token);
        Response response = new Response(200, "Book added successfully", token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("updateBook/{id}")
    public ResponseEntity<Response> updateBook(@Valid @RequestBody BookDTO bookDTO, @PathVariable Long bookId, @RequestHeader String token) {
        BookModel bookModel = bookService.updateBook(bookDTO, bookId, token);
        Response response = new Response(200, "Book updated successfully", bookModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBook/{id}")
    public ResponseEntity<Response> getBookById(@PathVariable Long bookId, @RequestHeader String token) {
        Optional<BookModel> bookModel = bookService.getBookById(bookId, token);
        Response response = new Response(200, "book by id fetch successfully", bookModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<Response> getAllBooks(@RequestHeader String token) {
        List<BookModel> bookModel = bookService.getAllBooks(token);
        Response response = new Response(200, "all notes fetch successfully", bookModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteNote/{id}")
    public ResponseEntity<Response> deleteBook(@RequestHeader String token, @PathVariable Long bookId) {
        Response bookModel = bookService.deleteBook(token, bookId);
        Response response = new Response(200, "delete notes successfully", bookModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updatebookprice/{token}/{bookid}")
    public ResponseEntity<Response> updateBookPrice(@RequestHeader String token, @PathVariable Long bookId, @RequestParam double price) {
        Response response = bookService.updateBookPrice(token, bookId, price);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PutMapping("/updatebookquantity/{token}/{bookid}")
    public ResponseEntity<Response> updateBookQuantity(@RequestHeader String token, @PathVariable Long bookId, @RequestParam long quantity) {
        Response response = bookService.updateBookQuantity(token, bookId, quantity);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/verifybook/{token}/{bookId}")
    public Boolean verifyBook(@PathVariable String token,@PathVariable Long bookId) {
        return bookService.verifyBook(token,bookId);
    }
}
