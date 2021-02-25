package ru.otus.reactivewebbooklibrary.rest.controller;

import org.springframework.http.MediaType;
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
    public Mono<Comment> save(@Validated @RequestBody CommentRequest commentRequest) {
        return commentService.saveComment(commentRequest.getBook(), commentRequest.getContent());
    }

    @GetMapping("/api/comments/id")
    public Mono<Comment> getCommentById(@RequestParam String id){
        return commentService.getCommentById(id);
    }

    @GetMapping("/api/comments/{comment}")
    public Flux<Comment> getCommentByContent(@PathVariable String comment) {
        return commentService.getCommentByContent(comment);
    }

    @GetMapping("/api/comments/book/{bookTitle}")
    public Flux<Comment> getCommentByBookTitle(@PathVariable String bookTitle) {
        return commentService.getCommentsByBook(bookTitle);
    }

    @GetMapping("/api/comments")
    public Flux<Comment> getAll() {
        return commentService.getAll();
    }

    @PutMapping(value = "/api/comments",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Comment> edit(@Validated @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(commentRequest.getId(), commentRequest.getContent());
    }

    @DeleteMapping(value = "/api/comments",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> deleteByContent(@Validated @RequestBody CommentRequest commentRequest) {
        return commentService.deleteComment(commentRequest.getId());
    }
}
