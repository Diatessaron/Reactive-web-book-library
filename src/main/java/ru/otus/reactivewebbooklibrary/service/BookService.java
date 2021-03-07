package ru.otus.reactivewebbooklibrary.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Book;

public interface BookService {
    Mono<Book> saveBook(String title, String authorNameParameter, String genreNameParameter);

    Mono<Book> getBookById(String id);

    Flux<Book> getBookByTitle(String title);

    Flux<Book> getBookByAuthor(String author);

    Flux<Book> getBookByGenre(String genre);

    Mono<Book> getBookByComment(String commentId);

    Flux<Book> getAll();

    Mono<Void> updateBook(String id, String title, String authorNameParameter,
                          String genreNameParameter);

    Mono<Void> deleteBook(String id);
}
