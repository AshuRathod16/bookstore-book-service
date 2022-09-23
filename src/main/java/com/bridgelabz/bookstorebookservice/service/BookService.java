package com.bridgelabz.bookstorebookservice.service;

import com.bridgelabz.bookstorebookservice.dto.BookDTO;
import com.bridgelabz.bookstorebookservice.exception.BookNotFoundException;
import com.bridgelabz.bookstorebookservice.model.BookModel;
import com.bridgelabz.bookstorebookservice.repository.BookRepository;
import com.bridgelabz.bookstorebookservice.util.Response;
import com.bridgelabz.bookstorebookservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    MailService mailService;

    @Autowired
    RestTemplate restTemplate;

    /**
     * @param token, noteDTO
     * Purpose: Creating method to create notes
     * @author Ashwini Rathod
     */

    @Override
    public BookModel addBook(BookDTO bookDTO, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verify/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            BookModel bookModel = new BookModel(bookDTO);
//            bookModel.setRegisterDate(LocalDateTime.now());
            bookRepository.save(bookModel);
            String body = "Book added successfully with bookId" + bookModel.getBookId();
            String subject = "Book added successfully";
            return bookModel;
        }
        throw new BookNotFoundException(400, "Book not added ");
    }

    /**
     * @param token,noteDTO,noteId
     * Purpose: Creating method to update notes
     * @author Ashwini Rathod
     */

    @Override
    public BookModel updateBook(BookDTO bookDTO, Long bookId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verify/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<BookModel> isUserIdPresent = bookRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<BookModel> isBookPresent = bookRepository.findById(bookId);
                if (isBookPresent.isPresent()) {
                    isBookPresent.get().setBookName(bookDTO.getBookName());
                    isBookPresent.get().setBookAuthor(bookDTO.getBookAuthor());
                    isBookPresent.get().setBookDescription(bookDTO.getBookDescription());
                    isBookPresent.get().setBookPrice(bookDTO.getBookPrice());
                    isBookPresent.get().setBookLogo(bookDTO.getBookLogo());
                    isBookPresent.get().setBookQuantity(bookDTO.getBookQuantity());
                    bookRepository.save(isBookPresent.get());
                    String body = "Book updated successfully with bookId" + isBookPresent.get().getBookId();
                    String subject = "Book updated successfully";
                    mailService.send(String.valueOf(isBookPresent.get().getBookId()), subject, body);
                    return isBookPresent.get();
                }
            }
            throw new BookNotFoundException(400, "Book not found");
        }
        throw new BookNotFoundException(400, "Token is invalid");

    }

    /**
     * @param token
     * purpose: Creating Method to get all books
     * @author Ashwini Rathod
     */

    @Override
    public List<BookModel> getAllBooks(String token) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verify/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<BookModel> isUserIdPresent = bookRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                List<BookModel> readAllNotes = bookRepository.findAll();
                if (readAllNotes.size() > 0) {
                    return readAllNotes;
                }
            }
            throw new BookNotFoundException(400, "Book with this id not found");
        }
        throw new BookNotFoundException(400, "Token is invalid");
    }

    /**
     * @param token,noteId
     * purpose: Creating method to get book by id
     * @author Ashwini Rathod
     */

    @Override
    public Optional<BookModel> getBookById(Long bookId, String token) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verify/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<BookModel> isUserIdPresent = bookRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<BookModel> isNotePresent = bookRepository.findById(bookId);
                if (isNotePresent.isPresent()) {
                    return isNotePresent;
                }
            }
            throw new BookNotFoundException(400, "Book not found");
        }
        throw new BookNotFoundException(400, "Token is invalid");
    }

/**
 * @param token,noteId
 * purpose: Creating method to delete book
 * @author Ashwini Rathod
 */


    @Override
    public Response deleteBook(String token, Long bookId) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verify/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<BookModel> isUserIdPresent = bookRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<BookModel> isIdPresent = bookRepository.findById(bookId);
                if (isIdPresent.isPresent()) {
                    return new Response(200, "Successfully", isIdPresent.get());
                }
            }
            throw new BookNotFoundException(400, "User not found");
        }
        throw new BookNotFoundException(400, "Invalid token");
    }

    /**
     * @param token,bookId,price
     * purpose: Creating method to update book price
     * @author Ashwini Rathod
     */

    @Override
    public Response updateBookPrice(String token, Long bookId, double price) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verify/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<BookModel> isUserIdPresent = bookRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<BookModel> isBookPresent = bookRepository.findById(bookId);
                if (isBookPresent.isPresent()) {
                    isBookPresent.get().setBookPrice(price);
                    bookRepository.save(isBookPresent.get());
                    return new Response(200, "Successfully updated book price", isBookPresent.get());
                }
            }
            throw new BookNotFoundException(400, "Book not Present");
        }
        throw new BookNotFoundException(400, "User not Login, Please Check!");
    }

    /**
     * @param token,bookId,quantity
     * purpose: Creating method to update book quatity
     * @author Ashwini Rathod
     */

    @Override
    public Response updateBookQuantity(String token, Long bookId, Long quantity) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verify/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<BookModel> isUserIdPresent = bookRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<BookModel> isBookPresent = bookRepository.findById(bookId);
                if (isBookPresent.isPresent()) {
                    isBookPresent.get().setBookQuantity(quantity);
                    bookRepository.save(isBookPresent.get());
                    return new Response(200, "Successfully updated book quantity", isBookPresent.get());
                }
            }
            throw new BookNotFoundException(400, "Book not Present");
        }
        throw new BookNotFoundException(400, "User not Login, Please Check!");
    }

    /**
     * @param token,bookId
     * purpose: Creating method to verify book
     * @author Ashwini Rathod
     */

    @Override
    public Boolean verifyBook(String token, Long bookId) {
        boolean isUserPresent = restTemplate.getForObject("http://BOOKSTORE-USER-SERVICE:8083/user/verify/" + token, Boolean.class);
        if (isUserPresent) {
            Long userId = tokenUtil.decodeToken(token);
            Optional<BookModel> isUserIdPresent = bookRepository.findByUserId(userId);
            if (isUserIdPresent.isPresent()) {
                Optional<BookModel> isBookPresent = bookRepository.findById(bookId);
                if (isBookPresent != null) {
                    return true;
                } else {
                    return false;
                }
            }

        }
        return isUserPresent;
    }
}
