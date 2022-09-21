package com.bridgelabz.bookstorebookservice.dto;

import lombok.Data;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 21-09-2022
 * Purpose : dto for the User data
 */


@Data
public class BookDTO {

    public String bookName;
    public String bookAuthor;
    public String bookDescription;
    public String bookLogo;
    public double bookPrice;
    public long bookQuantity;
}
