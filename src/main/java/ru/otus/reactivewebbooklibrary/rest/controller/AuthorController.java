package ru.otus.reactivewebbooklibrary.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Author;
import ru.otus.reactivewebbooklibrary.domain.Book;
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
    public ResponseEntity<Mono<Author>> create(@Validated @RequestBody AuthorRequest authorRequest) {
        if (authorRequest.getAuthor() == null || authorRequest.getAuthor().isBlank())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(authorService.save(authorRequest.getAuthor()));
    }

    @GetMapping("/api/authors/id")
    public ResponseEntity<Mono<Author>> getAuthorById(@RequestParam String id) {
        final Mono<Author> author = authorService.getAuthorById(id);

        if (author.hasElement().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @GetMapping("/api/authors/{author}")
    public ResponseEntity<Flux<Author>> getAuthorByName(@PathVariable String author) {
        final Flux<Author> authors = authorService.getAuthorsByName(author);

        if (authors.hasElements().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(authors);
    }

    @GetMapping("/api/authors")
    public Flux<Author> getAll() {
        return authorService.getAll();
    }

    @PutMapping(value = "/api/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Void>> edit(@Validated @RequestBody AuthorRequest authorRequest) {
        if (authorRequest.getId() == null || authorRequest.getAuthor() == null
                || authorRequest.getId().isBlank() || authorRequest.getAuthor().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else {
            final Mono<Void> result = authorService
                    .updateAuthor(authorRequest.getId(), authorRequest.getAuthor());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @DeleteMapping(value = "/api/authors",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Void>> deleteById(@Validated @RequestBody AuthorRequest authorRequest) {
        if (authorRequest.getId() == null || authorRequest.getId().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(authorService.deleteAuthor(authorRequest.getId()));
    }
}
