package ru.otus.reactivewebbooklibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Comment;
import ru.otus.reactivewebbooklibrary.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public Mono<Comment> saveComment(String bookTitle, String commentContent) {
        return commentRepository.save(new Comment(commentContent, bookTitle));
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Comment> getCommentByContent(String content) {
        return commentRepository.findByContent(content);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Comment> getCommentsByBook(String bookTitle) {
        return commentRepository.findByBook_Title(bookTitle);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Transactional
    @Override
    public Mono<Comment> updateComment(String id, String commentContent) {
        return commentRepository.findById(id)
                .flatMap(c -> Mono.just(c.setContent(commentContent)))
                .flatMap(commentRepository::save);
    }

    @Transactional
    @Override
    public Mono<Void> deleteComment(String id) {
        return commentRepository.deleteById(id);
    }
}
