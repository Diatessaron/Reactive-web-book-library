package ru.otus.reactivewebbooklibrary.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Genre;

public interface GenreService {
    Mono<Genre> saveGenre(String name);

    Mono<Genre> getGenreById(String id);

    Mono<Genre> getGenreByName(String name);

    Flux<Genre> getAll();

    Mono<Genre> updateGenre(String id, String name);

    Mono<Tuple2<Void, Void>> deleteGenre(String id);
}
