package com.betrybe.alexandria.service;

import com.betrybe.alexandria.entity.Author;
import com.betrybe.alexandria.entity.Book;
import com.betrybe.alexandria.entity.BookDetails;
import com.betrybe.alexandria.entity.Publisher;
import com.betrybe.alexandria.repository.BookDetailsRepository;
import com.betrybe.alexandria.repository.BookRepository;
import com.betrybe.alexandria.service.exception.AuthorNotFoundException;
import com.betrybe.alexandria.service.exception.BookDetailsNotFoundException;
import com.betrybe.alexandria.service.exception.BookNotFoundException;
import com.betrybe.alexandria.service.exception.PublisherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final BookDetailsRepository bookDetailsRepository;
  private final PublisherService publisherService;
  private final AuthorService authorService;

  @Autowired
  public BookService(BookRepository bookRepository,
                     BookDetailsRepository bookDetailsRepository,
                     PublisherService publisherService, AuthorService authorService) {
    this.bookRepository = bookRepository;
    this.bookDetailsRepository = bookDetailsRepository;
    this.publisherService = publisherService;
    this.authorService = authorService;
  }

  public Book findById(Long id) throws BookNotFoundException {
      return  bookRepository.findById(id)
              .orElseThrow(BookNotFoundException::new);
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Book create(Book book) {
    return bookRepository.save(book);
  }

  public Book update(Long id, Book book) throws BookNotFoundException {
    Book bookFromDb = findById(id);

    bookFromDb.setTitle(book.getTitle());
    bookFromDb.setGenre(book.getGenre());

    return bookRepository.save(bookFromDb);

  }

  public Book deleteById(Long id) throws BookNotFoundException {

    Book book = findById(id);

    bookRepository.deleteById(id);

    return book;
  }

  public BookDetails createBookDetails(Long bookId, BookDetails bookDetails)
          throws BookNotFoundException {
    Book book = findById(bookId);
    bookDetails.setBook(book);
    return bookDetailsRepository.save(bookDetails);
  }

  public BookDetails getBookDetails(Long bookId) throws BookNotFoundException, BookDetailsNotFoundException {
    Book book = findById(bookId);
    BookDetails bookDetailsfromBD = book.getBookDetails();

    if(bookDetailsfromBD == null) {
      throw new BookDetailsNotFoundException();
    }

    return bookDetailsfromBD;
  }

  public BookDetails updateBookDetail(Long bookId, BookDetails bookDetails) throws
          BookNotFoundException, BookNotFoundException{
    BookDetails bookDetailsDB = getBookDetails(bookId);
    bookDetailsDB.setSummary(bookDetails.getSummary());
    bookDetailsDB.setPageCount(bookDetails.getPageCount());
    bookDetailsDB.setYear(bookDetails.getYear());
    bookDetailsDB.setIsbn(bookDetails.getIsbn());
    return bookDetailsRepository.save(bookDetailsDB);
  }

  public BookDetails removeBookDetails(Long bookId) throws
          BookNotFoundException, BookDetailsNotFoundException {
    Book book = findById(bookId);
    BookDetails bookDetails = book.getBookDetails();
    if (bookDetails == null) {
      throw new BookDetailsNotFoundException();
    }
    book.setBookDetails(null);
    bookDetails.setBook(null);
    bookDetailsRepository.delete(bookDetails);
    return bookDetails;
  }

  public Book setBookPulisher(Long bookId, Long publisherId) throws BookNotFoundException,
          BookDetailsNotFoundException, PublisherNotFoundException {
    Book book = findById(bookId);
    Publisher publisher = publisherService.findById(publisherId);
    book.setPublisher(publisher);
    return bookRepository.save(book);
  }

  public Book removeBookPublisher(Long bookId) throws BookNotFoundException {
    Book book = findById(bookId);
    book.setPublisher(null);
    return bookRepository.save(book);
  }

  public Book addBookAuthor(Long bookId, Long authorId) throws BookNotFoundException,
          AuthorNotFoundException {
    Book book = findById(bookId);
    Author author = authorService.findById(authorId);
    book.getAuthors().add(author);
    return bookRepository.save(book);
  }

  public Book removeBookAuthor(Long bookId, Long authorId) throws
          BookNotFoundException, AuthorNotFoundException {
    Book book = findById(bookId);
    Author author = authorService.findById(authorId);
    book.getAuthors().remove(author);
    return bookRepository.save(book);
  }
}
