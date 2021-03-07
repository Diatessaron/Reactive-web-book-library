package ru.otus.reactivewebbooklibrary.rest.router;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootTest
class GenreRouterTest {
    @Qualifier("genrePagesRouter")
    @Autowired
    private RouterFunction route;

    @Test
    void testGenreSaveRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/genres/add").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGenreRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/genres/id").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGenreListRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/genres").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGenreEditRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/genres/edit").exchange()
                .expectStatus().isOk();
    }
}
