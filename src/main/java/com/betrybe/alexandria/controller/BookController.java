package com.betrybe.alexandria.controller;

import com.betrybe.alexandria.controller.dto.BookCreationDto;
import com.betrybe.alexandria.controller.dto.BookDetailsCreationDto;
import com.betrybe.alexandria.controller.dto.BookDetailsDto;
import com.betrybe.alexandria.controller.dto.BookDto;
import com.betrybe.alexandria.entity.Book;
import com.betrybe.alexandria.entity.BookDetails;
import com.betrybe.alexandria.service.BookService;
import com.betrybe.alexandria.service.exception.AuthorNotFoundException;
import com.betrybe.alexandria.service.exception.BookDetailsNotFoundException;
import com.betrybe.alexandria.service.exception.BookNotFoundException;
import com.betrybe.alexandria.service.exception.PublisherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/{id}")
  public BookDto getBookById(@PathVariable Long id) throws BookNotFoundException {
    return BookDto.fromEntity(
            bookService.findById(id)
    );
  }

  @GetMapping
  public List<BookDto> getAllBooks() throws BookNotFoundException {
    List<Book> allBooks = bookService.findAll();
    return allBooks.stream()
                    .map(BookDto::fromEntity)
                    .toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookDto createBook(@RequestBody BookCreationDto bookCreationDto) {
      return BookDto.fromEntity(bookCreationDto.toEntity());
  }

  @PutMapping("/{id}")
  public BookDto updateBook(@PathVariable Long id,
          @RequestBody BookCreationDto bookCreationDto) throws BookNotFoundException {
    return BookDto.fromEntity(
            bookService.update(id, bookCreationDto.toEntity())
    );
  }

  @DeleteMapping("/{id}")
  public BookDto deleteBookById(@PathVariable Long id) throws BookNotFoundException {
    return BookDto.fromEntity(
            bookService.deleteById(id)
    );
  }

  @PostMapping("/{bookId}/detail")
  @ResponseStatus(HttpStatus.CREATED)
  public BookDetailsDto createBookDetails(@PathVariable Long bookId,
                                          @RequestBody BookDetailsCreationDto bookDetailsCreationDto)
    throws BookNotFoundException {
      return BookDetailsDto.fromEntity(bookDetailsCreationDto.toEntity());
  }

  @GetMapping("/{bookId}/detail")
  public BookDetailsDto getBookDetailById(@PathVariable Long bookId) throws BookNotFoundException,
          BookNotFoundException {
    return BookDetailsDto.fromEntity(bookService.getBookDetails(bookId));
  }

  @PutMapping("/{bookId}/detail")
  public BookDetailsDto updateBookDetail(@PathVariable Long bookId,
                                         @RequestBody BookDetailsCreationDto bookDetailsCreationDto) throws
          BookNotFoundException, BookDetailsNotFoundException {
    return BookDetailsDto.fromEntity(bookService.updateBookDetail(bookId, bookDetailsCreationDto.toEntity()));
  }

  @DeleteMapping("/{bookId}/detail")
  public BookDetailsDto deleteBookDetailsById(@PathVariable Long bookId) throws BookNotFoundException,
          BookNotFoundException {
    return BookDetailsDto.fromEntity(bookService.removeBookDetails(bookId));
  }

  @PutMapping("/{bookId}/publisher/{publisherId}")
  public BookDto setBookPublisher(@PathVariable Long bookId, Long publisherId) throws BookNotFoundException,
          PublisherNotFoundException {
    return BookDto.fromEntity(bookService.setBookPulisher(bookId, publisherId));
  }

  @DeleteMapping("/{bookId}/publisher")
  public BookDto deleteBookPublisher(@PathVariable Long bookId) throws BookNotFoundException,
  PublisherNotFoundException {
    return BookDto.fromEntity(bookService.removeBookPublisher(bookId));
  }

  @PutMapping("/{bookId}/authors/{authorId}")
  public BookDto addBookAuthor(@PathVariable Long bookId, Long authorId) throws BookNotFoundException,
          AuthorNotFoundException {
    return BookDto.fromEntity(bookService.addBookAuthor(bookId, authorId));
  }

  @DeleteMapping("/{bookId}/authors")
  public BookDto deleteBookAuthor(@PathVariable Long bookId, Long authorId) throws
          BookNotFoundException, AuthorNotFoundException {
    return BookDto.fromEntity(bookService.removeBookAuthor(bookId, authorId ));
  }
}
