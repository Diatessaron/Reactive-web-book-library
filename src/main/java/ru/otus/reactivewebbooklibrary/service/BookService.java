package ru.otus.reactivewebbooklibrary.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Book;

public interface BookService {
    Mono<Book> saveBook(String title, String authorNameParameter, String genreNameParameter);

    Mono<Book> getBookById(String id);

    Flux<Book> getBookByTitle(String title);

    Flux<Book> getBookByAuthor(String author);

    Flux<Book> getBookByGenre(String genre);

    Mono<Book> getBookByComment(String commentId);

    Flux<Book> getAll();

    Mono<Book> updateBook(String oldBookTitle, String title, String authorNameParameter,
                          String genreNameParameter);

    Mono<Tuple2<Void, Void>> deleteBook(String id);
}