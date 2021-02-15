package ru.otus.reactivewebbooklibrary.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
    Mono<Genre> findByName(String name);

    Mono<Void> deleteByName(String name);
}
