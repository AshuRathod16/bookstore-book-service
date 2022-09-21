package com.bridgelabz.bookstorebookservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class BookNotFoundException extends RuntimeException{
    private int statusCode;
    private String statusMessage;

    public BookNotFoundException(int i, String book_not_created_) {
    }
}
