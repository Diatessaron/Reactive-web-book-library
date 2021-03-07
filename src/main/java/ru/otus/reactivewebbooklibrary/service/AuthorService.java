package ru.otus.reactivewebbooklibrary.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Author;

public interface AuthorService {
    Mono<Author> save(String authorName);

    Mono<Author> getAuthorById(String id);

    Flux<Author> getAuthorsByName(String name);

    Flux<Author> getAll();

    Mono<Void> updateAuthor(String id, String name);

    Mono<Void> deleteAuthor(String id);
}
