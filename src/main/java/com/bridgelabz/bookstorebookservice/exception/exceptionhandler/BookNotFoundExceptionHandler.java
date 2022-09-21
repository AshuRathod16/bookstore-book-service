package com.bridgelabz.bookstorebookservice.exception.exceptionhandler;

import com.bridgelabz.bookstorebookservice.exception.BookNotFoundException;
import com.bridgelabz.bookstorebookservice.exception.CustomValidationException;
import com.bridgelabz.bookstorebookservice.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookNotFoundExceptionHandler{
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ResponseUtil> handlerHiringException(BookNotFoundException exception) {
        ResponseUtil response = new ResponseUtil();
        response.setErrorCode(400);
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //  Using custom exception for handling the error of validation part
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomValidationException> handlerHiringException(MethodArgumentNotValidException exception) {
        CustomValidationException validException = new CustomValidationException();
        validException.setErrorCode(400);
        validException.setMessage(exception.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(validException, HttpStatus.BAD_REQUEST);
    }
}
