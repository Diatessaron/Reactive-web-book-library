package ru.otus.reactivewebbooklibrary.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Comment;
import ru.otus.reactivewebbooklibrary.rest.dto.CommentRequest;
import ru.otus.reactivewebbooklibrary.service.CommentService;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = CommentController.class)
class CommentControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private CommentService commentService;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void testSaveByStatus() {
        final CommentRequest commentRequest = new CommentRequest();
        commentRequest.setContent("Content");
        commentRequest.setBook("Book");

        client.post().uri("/api/comments").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(commentRequest))
                .exchange().expectStatus().isOk();
    }

    @Test
    void testGetCommentByIdByStatus() {
        when(commentService.getCommentById("id"))
                .thenReturn(Mono.just(new Comment("Content", "Book")));

        client.get().uri(uriBuilder -> uriBuilder.path("/api/comments/id").queryParam("id", "id").build())
                .exchange().expectStatus().isOk();
    }

    @Test
    void testGetCommentByContentByStatus() {
        when(commentService.getCommentByContent("Content"))
                .thenReturn(Flux.just(new Comment("Content", "Book")));

        client.get().uri("/api/comments/Content").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetCommentByBookTitleByStatus() {
        when(commentService.getCommentsByBook("Book"))
                .thenReturn(Flux.just(new Comment("Content", "Book")));

        client.get().uri("/api/comments/book/Book").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetAllByStatus() {
        when(commentService.getAll()).thenReturn(Flux.just(new Comment("Content", "Book")));

        client.get().uri("/api/comments").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testEditByStatus() {
        when(commentService.updateComment("id", "Content"))
                .thenReturn(Mono.just(new Comment("id", "Content", "Book")));

        final CommentRequest commentRequest = new CommentRequest();
        commentRequest.setId("id");
        commentRequest.setContent("Content");
        commentRequest.setBook("Book");

        client.put().uri("/api/comments").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(commentRequest))
                .exchange().expectStatus().isOk();
    }

    @Test
    void testDeleteByContentByStatus() {
        final CommentRequest commentRequest = new CommentRequest();
        commentRequest.setId("id");

        client.method(HttpMethod.DELETE).uri("/api/comments")
                .body(BodyInserters.fromObject(commentRequest)).exchange()
                .expectStatus().isOk();
    }
}
