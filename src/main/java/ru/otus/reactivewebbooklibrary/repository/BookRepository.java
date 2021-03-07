package ru.otus.reactivewebbooklibrary.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByTitle(String title);

    Flux<Book> findByAuthor_Id(String id);

    Flux<Book> findByAuthor_Name(String authorName);

    Flux<Book> findByGenre_Id(String id);

    Flux<Book> findByGenre_Name(String genreName);

    Mono<Void> deleteByAuthor_Id(String id);

    Mono<Void> deleteByGenre_Id(String id);
}
