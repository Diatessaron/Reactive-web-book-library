package ru.otus.reactivewebbooklibrary.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByTitle(String title);

    Flux<Book> findByAuthor_Name(String author);

    Flux<Book> findByGenre_Name(String genre);

    Mono<Void> deleteByAuthor_Name(String author);

    Mono<Void> deleteByGenre_Name(String genre);
}
