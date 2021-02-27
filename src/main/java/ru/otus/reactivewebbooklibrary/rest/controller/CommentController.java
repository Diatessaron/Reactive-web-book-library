package ru.otus.reactivewebbooklibrary.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Comment;
import ru.otus.reactivewebbooklibrary.rest.dto.CommentRequest;
import ru.otus.reactivewebbooklibrary.service.CommentService;

@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/api/comments",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Comment>> save(@Validated @RequestBody CommentRequest commentRequest) {
        if (commentRequest.getBook() == null || commentRequest.getContent() == null ||
                commentRequest.getBook().isBlank() || commentRequest.getContent().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(commentService.saveComment
                    (commentRequest.getBook(), commentRequest.getContent()));
    }

    @GetMapping("/api/comments/id")
    public ResponseEntity<Mono<Comment>> getCommentById(@RequestParam String id) {
        final Mono<Comment> comment = commentService.getCommentById(id);

        if(comment.hasElement().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @GetMapping("/api/comments/{comment}")
    public ResponseEntity<Flux<Comment>> getCommentByContent(@PathVariable String comment) {
        final Flux<Comment> comments = commentService.getCommentByContent(comment);

        if(comments.hasElements().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @GetMapping("/api/comments/book/{bookTitle}")
    public ResponseEntity<Flux<Comment>> getCommentByBookTitle(@PathVariable String bookTitle) {
        final Flux<Comment> comments = commentService.getCommentsByBook(bookTitle);

        if(comments.hasElements().equals(Mono.just(false)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @GetMapping("/api/comments")
    public Flux<Comment> getAll() {
        return commentService.getAll();
    }

    @PutMapping(value = "/api/comments",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Comment>> edit(@Validated @RequestBody CommentRequest commentRequest) {
        if(commentRequest.getId() == null || commentRequest.getContent() == null ||
                commentRequest.getId().isBlank() || commentRequest.getContent().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment
                    (commentRequest.getId(), commentRequest.getContent()));
    }

    @DeleteMapping(value = "/api/comments",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Void>> deleteByContent(@Validated @RequestBody CommentRequest commentRequest) {
        if(commentRequest.getId() == null || commentRequest.getId().isBlank())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(commentRequest.getId()));
    }
}
