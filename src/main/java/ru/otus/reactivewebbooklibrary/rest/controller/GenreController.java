package ru.otus.reactivewebbooklibrary.rest.controller;

import org.springframework.http.MediaType;
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
    public Mono<Genre> create(@Validated @RequestBody GenreRequest genreRequest) {
        return genreService.saveGenre(genreRequest.getGenre());
    }

    @GetMapping("/api/genres/id")
    public Mono<Genre> getGenreById(@RequestParam String id){
        return genreService.getGenreById(id);
    }

    @GetMapping("/api/genres/{genre}")
    public Mono<Genre> getGenreByName(@PathVariable String genre) {
        return genreService.getGenreByName(genre);
    }

    @GetMapping("/api/genres")
    public Flux<Genre> getAll() {
        return genreService.getAll();
    }

    @PutMapping(value = "/api/genres",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public  Mono<Tuple2<Flux<Book>, Genre>> edit(@Validated @RequestBody GenreRequest genreRequest) {
        return genreService.updateGenre(genreRequest.getId(), genreRequest.getGenre());
    }

    @DeleteMapping(value = "/api/genres",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Tuple2<Void, Void>> deleteByName(@Validated @RequestBody GenreRequest genreRequest) {
        return genreService.deleteGenre(genreRequest.getId());
    }
}
