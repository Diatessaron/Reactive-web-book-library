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
import ru.otus.reactivewebbooklibrary.domain.Author;
import ru.otus.reactivewebbooklibrary.rest.dto.AuthorRequest;
import ru.otus.reactivewebbooklibrary.service.AuthorService;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private AuthorService authorService;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void testCreateByStatus() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setAuthor("Author");

        client.post().uri("/api/authors").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(authorRequest)).exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectSaveRequest() {
        final AuthorRequest authorRequest = new AuthorRequest();

        client.post().uri("/api/authors").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(authorRequest)).exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testGetAuthorByIdByStatus() {
        when(authorService.getAuthorById("id")).thenReturn(Mono.just(new Author("Author")));

        client.get().uri(uriBuilder -> uriBuilder.path("/api/authors/id").queryParam("id", "id").build())
                .exchange().expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectGetByIdRequest() {
        client.get().uri(uriBuilder -> uriBuilder.path("/api/authors/id").queryParam("id", "id").build())
                .exchange().expectStatus().is4xxClientError();
    }

    @Test
    void testGetAuthorByNameByStatus() {
        when(authorService.getAuthorsByName("Author")).thenReturn(Flux.just(new Author("Author")));

        client.get().uri("/api/authors/Author").exchange().expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectGetByNameRequest() {
        client.get().uri("/api/authors/Author").exchange().expectStatus().is4xxClientError();
    }

    @Test
    void testGetAllByStatus() {
        when(authorService.getAll()).thenReturn(Flux.just(new Author("James Joyce"),
                new Author("Author")));

        client.get().uri("/api/authors").exchange().expectStatus().isOk();
    }

    @Test
    void testEditByStatus() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setId("id");
        authorRequest.setAuthor("Author");

        client.put().uri("/api/authors").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(authorRequest)).exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectEditRequest() {
        final AuthorRequest authorRequest = new AuthorRequest();

        client.put().uri("/api/authors").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(authorRequest)).exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testDeleteByIdByStatus() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setId("id");

        client.method(HttpMethod.DELETE).uri("/api/authors")
                .body(BodyInserters.fromObject(authorRequest)).exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectDeleteRequest() {
        final AuthorRequest authorRequest = new AuthorRequest();

        client.method(HttpMethod.DELETE).uri("/api/authors")
                .body(BodyInserters.fromObject(authorRequest)).exchange()
                .expectStatus().is4xxClientError();
    }
}
