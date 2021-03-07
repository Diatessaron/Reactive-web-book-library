package ru.otus.reactivewebbooklibrary.rest.router;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRouterTest {
    @Qualifier("bookPagesRouter")
    @Autowired
    private RouterFunction route;

    @Test
    void testBookSaveRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/books/add").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testBookIdRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/books/id").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testBooksByAuthorRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/books/author/Author").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testBooksByGenreRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/books/genre/Genre").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testBooksByCommentRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/books/comment").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testBookListRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/books").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testBookEditRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/books/edit").exchange()
                .expectStatus().isOk();
    }
}
