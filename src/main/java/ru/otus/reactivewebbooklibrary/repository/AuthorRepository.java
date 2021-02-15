package ru.otus.reactivewebbooklibrary.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Flux<Author> findByName(String name);

    Mono<Void> deleteByName(String name);
}
