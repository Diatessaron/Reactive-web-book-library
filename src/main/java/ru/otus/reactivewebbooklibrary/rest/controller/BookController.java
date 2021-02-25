package ru.otus.reactivewebbooklibrary.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Book;
import ru.otus.reactivewebbooklibrary.domain.Comment;
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
    public Mono<Book> save(@Validated @RequestBody BookRequest bookRequest) {
        return bookService.saveBook(bookRequest.getTitle(), bookRequest.getAuthorName(), bookRequest.getGenreName());
    }

    @GetMapping("/api/books/id")
    public Mono<Book> getBookById(@RequestParam String id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/api/books/title/{title}")
    public Flux<Book> getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/api/books/author/{author}")
    public Flux<Book> getBookByAuthor(@PathVariable String author) {
        return bookService.getBookByAuthor(author);
    }

    @GetMapping("/api/books/genre/{genre}")
    public Flux<Book> getBookByGenre(@PathVariable String genre) {
        return bookService.getBookByGenre(genre);
    }

    @GetMapping("/api/books")
    public Flux<Book> getAll() {
        return bookService.getAll();
    }

    @PutMapping(value = "/api/books",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Tuple2<Flux<Comment>, Book>> edit(@Validated @RequestBody BookRequest bookRequest) {
        return bookService.updateBook(bookRequest.getId(), bookRequest.getTitle(),
                bookRequest.getAuthorName(), bookRequest.getGenreName());
    }

    @DeleteMapping(value = "/api/books",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Tuple2<Void, Void>> deleteByTitle(@Validated @RequestBody BookRequest bookRequest) {
        return bookService.deleteBook(bookRequest.getId());
    }
}
