package ru.otus.reactivewebbooklibrary.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Book;
import ru.otus.reactivewebbooklibrary.rest.dto.BookRequest;
import ru.otus.reactivewebbooklibrary.service.BookService;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/api/books",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Book>> save(@Validated @RequestBody BookRequest bookRequest) {
        if (bookRequest.getTitle() == null || bookRequest.getAuthorName() == null ||
                bookRequest.getGenreName() == null || bookRequest.getTitle().isBlank() ||
                bookRequest.getAuthorName().isBlank() || bookRequest.getGenreName().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(bookService.saveBook
                    (bookRequest.getTitle(), bookRequest.getAuthorName(), bookRequest.getGenreName()));
    }

    @GetMapping("/api/books/id")
    public ResponseEntity<Mono<Book>> getBookById(@RequestParam String id) {
        final Mono<Book> book = bookService.getBookById(id);

        if (book.hasElement().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/api/books/title/{title}")
    public ResponseEntity<Flux<Book>> getBookByTitle(@PathVariable String title) {
        final Flux<Book> book = bookService.getBookByTitle(title);

        if (book.hasElements().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/api/books/author/{author}")
    public ResponseEntity<Flux<Book>> getBookByAuthor(@PathVariable String author) {
        final Flux<Book> book = bookService.getBookByAuthor(author);

        if (book.hasElements().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @GetMapping("/api/books/genre/{genre}")
    public ResponseEntity<Flux<Book>> getBookByGenre(@PathVariable String genre) {
        final Flux<Book> books = bookService.getBookByGenre(genre);

        if (books.hasElements().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/api/books")
    public Flux<Book> getAll() {
        return bookService.getAll();
    }

    @PutMapping(value = "/api/books",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Void>> edit(@Validated @RequestBody BookRequest bookRequest) {
        if (bookRequest.getId() == null || bookRequest.getTitle() == null ||
                bookRequest.getAuthorName() == null || bookRequest.getGenreName() == null ||
                bookRequest.getId().isBlank() || bookRequest.getTitle().isBlank() ||
                bookRequest.getAuthorName().isBlank() || bookRequest.getGenreName().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook
                    (bookRequest.getId(), bookRequest.getTitle(),
                            bookRequest.getAuthorName(), bookRequest.getGenreName()));
    }

    @DeleteMapping(value = "/api/books",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Void>> deleteByTitle(@Validated @RequestBody BookRequest bookRequest) {
        if (bookRequest.getId() == null || bookRequest.getId().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBook(bookRequest.getId()));
    }
}
