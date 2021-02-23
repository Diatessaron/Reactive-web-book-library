package ru.otus.reactivewebbooklibrary.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Author;
import ru.otus.reactivewebbooklibrary.rest.dto.AuthorRequest;
import ru.otus.reactivewebbooklibrary.service.AuthorService;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping(value = "/api/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Author> create(@Validated @RequestBody AuthorRequest authorRequest) {
        return authorService.save(authorRequest.getAuthor());
    }

    @GetMapping("/api/authors/id")
    public Mono<Author> getAuthorById(@RequestParam String id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/api/authors/{author}")
    public Flux<Author> getAuthorByName(@PathVariable String author) {
        return authorService.getAuthorsByName(author);
    }

    @GetMapping("/api/authors")
    public Flux<Author> getAll() {
        return authorService.getAll();
    }

    @PutMapping(value = "/api/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Author> edit(@Validated @RequestBody AuthorRequest authorRequest) {
        return authorService.updateAuthor(authorRequest.getId(), authorRequest.getAuthor());
    }

    @DeleteMapping(value = "/api/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Tuple2<Void, Void>> deleteById(@Validated @RequestBody AuthorRequest authorRequest) {
        return authorService.deleteAuthor(authorRequest.getId());
    }
}
