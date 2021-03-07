package ru.otus.reactivewebbooklibrary.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Genre;

public interface GenreService {
    Mono<Genre> saveGenre(String name);

    Mono<Genre> getGenreById(String id);

    Mono<Genre> getGenreByName(String name);

    Flux<Genre> getAll();

    Mono<Void> updateGenre(String id, String name);

    Mono<Void> deleteGenre(String id);
}
