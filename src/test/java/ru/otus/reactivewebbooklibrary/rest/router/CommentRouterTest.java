package ru.otus.reactivewebbooklibrary.rest.router;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootTest
class CommentRouterTest {
    @Qualifier("commentPagesRouter")
    @Autowired
    private RouterFunction route;

    @Test
    void testCommentSaveRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/comments/add").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCommentRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/comments/id").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCommentListByBookRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/comments/book/Book").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCommentListRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/comments").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCommentEditRoute() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/comments/edit").exchange()
                .expectStatus().isOk();
    }
}
