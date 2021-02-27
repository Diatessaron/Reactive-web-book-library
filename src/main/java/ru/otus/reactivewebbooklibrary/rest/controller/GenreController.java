package ru.otus.reactivewebbooklibrary.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Book;
import ru.otus.reactivewebbooklibrary.domain.Genre;
import ru.otus.reactivewebbooklibrary.rest.dto.GenreRequest;
import ru.otus.reactivewebbooklibrary.service.GenreService;

@RestController
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping(value = "/api/genres",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Genre>> create(@Validated @RequestBody GenreRequest genreRequest) {
        if (genreRequest.getGenre() == null || genreRequest.getGenre().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(genreService.saveGenre(genreRequest.getGenre()));
    }

    @GetMapping("/api/genres/id")
    public ResponseEntity<Mono<Genre>> getGenreById(@RequestParam String id) {
        final Mono<Genre> genre = genreService.getGenreById(id);

        if (genre.hasElement().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(genre);
    }

    @GetMapping("/api/genres/{genre}")
    public ResponseEntity<Mono<Genre>> getGenreByName(@PathVariable String genre) {
        final Mono<Genre> result = genreService.getGenreByName(genre);

        if (result.hasElement().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/api/genres")
    public Flux<Genre> getAll() {
        return genreService.getAll();
    }

    @PutMapping(value = "/api/genres",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<Tuple2<Book, Genre>>> edit(@Validated @RequestBody GenreRequest genreRequest) {
        if (genreRequest.getId() == null || genreRequest.getGenre() == null ||
                genreRequest.getId().isBlank() || genreRequest.getGenre().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(genreService.updateGenre(genreRequest.getId(),
                    genreRequest.getGenre()));
    }

    @DeleteMapping(value = "/api/genres",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Tuple2<Void, Void>>> deleteByName(@Validated @RequestBody GenreRequest genreRequest) {
        if (genreRequest.getId() == null || genreRequest.getId().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(genreService.deleteGenre(genreRequest.getId()));
    }
}
