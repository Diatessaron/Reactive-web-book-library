package ru.otus.reactivewebbooklibrary.rest.router;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootTest
class AuthorRouterTest {
    @Qualifier("authorPagesRouter")
    @Autowired
    private RouterFunction route;

    @Test
    void testAuthorSaveRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/authors/add").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testAuthorListRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/authors").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testAuthorRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/authors/id").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testAuthorEditRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/authors/edit").exchange()
                .expectStatus().isOk();
    }
}
