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
import ru.otus.reactivewebbooklibrary.domain.Genre;
import ru.otus.reactivewebbooklibrary.rest.dto.GenreRequest;
import ru.otus.reactivewebbooklibrary.service.GenreService;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = GenreController.class)
class GenreControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private GenreService genreService;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void testCreateByStatus() {
        GenreRequest genreRequest = new GenreRequest();
        genreRequest.setGenre("Modernist novel");

        client.post().uri("/api/genres").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(genreRequest)).exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetGenreByIdByStatus() {
        when(genreService.getGenreById("Id")).thenReturn(Mono.just(new Genre("Genre")));

        client.get().uri(uriBuilder -> uriBuilder.path("/api/genres/id").queryParam("id", "id").build())
                .exchange().expectStatus().isOk();
    }

    @Test
    void testGetGenreByNameByStatus() {
        when(genreService.getGenreByName("Genre")).thenReturn(Mono.just(new Genre("Genre")));

        client.get().uri("/api/genres/Genre").exchange().expectStatus().isOk();
    }

    @Test
    void testGetAllByStatus() {
        when(genreService.getAll()).thenReturn(Flux.just(new Genre("Modernist novel"), new Genre("Genre")));

        client.get().uri("/api/genres").exchange().expectStatus().isOk();
    }

    @Test
    void testEditByStatus() {
        GenreRequest genreRequest = new GenreRequest();
        genreRequest.setId("id");
        genreRequest.setGenre("Genre");

        client.put().uri("/api/genres").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(genreRequest)).exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteByNameByStatus() {
        GenreRequest genreRequest = new GenreRequest();
        genreRequest.setId("id");

        client.method(HttpMethod.DELETE).uri("/api/genres")
                .body(BodyInserters.fromObject(genreRequest)).exchange()
                .expectStatus().isOk();
    }
}