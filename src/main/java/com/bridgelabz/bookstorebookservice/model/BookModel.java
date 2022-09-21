package com.bridgelabz.bookstorebookservice.model;

import com.bridgelabz.bookstorebookservice.dto.BookDTO;
import lombok.Data;

import javax.persistence.*;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 22-09-2022
 * Purpose : Model for the User data Registration
 */

@Data
@Entity
@Table(name = "BookDetails")
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;
    private Long userId;
    private String bookName;
    private String bookAuthor;
    private String bookDescription;
    private String bookLogo;
    private double bookPrice;
    private long bookQuantity;

    public BookModel(BookDTO bookDTO) {

        this.bookName = bookDTO.getBookName();
        this.bookAuthor = bookDTO.getBookAuthor();
        this.bookDescription = bookDTO.getBookDescription();
        this.bookLogo = bookDTO.getBookLogo();
        this.bookPrice = bookDTO.getBookPrice();
        this.bookQuantity = bookDTO.getBookQuantity();

    }

    public BookModel() {

    }
}
