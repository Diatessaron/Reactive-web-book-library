package ru.otus.reactivewebbooklibrary.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Comment;

public interface CommentService {
    Mono<Comment> saveComment(String bookTitle, String commentContent);

    Mono<Comment> getCommentById(String id);

    Flux<Comment> getCommentByContent(String content);

    Flux<Comment> getCommentsByBook(String bookTitle);

    Flux<Comment> getAll();

    Mono<Comment> updateComment(String id, String commentContent);

    Mono<Void> deleteComment(String id);
}
