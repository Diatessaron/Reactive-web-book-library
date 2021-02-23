package ru.otus.reactivewebbooklibrary.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Author;

public interface AuthorService {
    Mono<Author> save(String authorName);

    Mono<Author> getAuthorById(String id);

    Flux<Author> getAuthorsByName(String name);

    Flux<Author> getAll();

    Mono<Author> updateAuthor(String id, String name);

    Mono<Tuple2<Void, Void>> deleteAuthor(String id);
}
