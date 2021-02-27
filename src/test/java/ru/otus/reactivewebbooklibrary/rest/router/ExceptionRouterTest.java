package ru.otus.reactivewebbooklibrary.rest.router;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootTest
class ExceptionRouterTest {
    @Qualifier("authorPagesRouter")
    @Autowired
    private RouterFunction route;

    @Test
    void shouldReturnClientErrorCodeOnIncorrectUri() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/authors/addd").exchange()
                .expectStatus().is4xxClientError();
    }
}