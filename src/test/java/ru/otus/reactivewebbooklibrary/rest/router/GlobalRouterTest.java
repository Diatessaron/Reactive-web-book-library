package ru.otus.reactivewebbooklibrary.rest.router;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootTest
class GlobalRouterTest {
    @Qualifier("globalPagesRouter")
    @Autowired
    private RouterFunction route;

    @Test
    void testGlobalIndexRouter() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get().uri("/").exchange()
                .expectStatus().isOk();
    }
}
