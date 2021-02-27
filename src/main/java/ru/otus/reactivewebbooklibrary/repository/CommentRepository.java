package ru.otus.reactivewebbooklibrary.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Flux<Comment> findByContent(String content);

    Flux<Comment> findByBook_Title(String bookTitle);

    Mono<Void> deleteByContent(String content);

    Mono<Void> deleteByBook_Title(String title);
}
