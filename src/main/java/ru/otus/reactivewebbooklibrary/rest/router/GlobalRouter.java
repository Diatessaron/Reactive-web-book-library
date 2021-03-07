package ru.otus.reactivewebbooklibrary.rest.router;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class GlobalRouter {
    @Bean
    public RouterFunction<ServerResponse> globalPagesRouter(@Value("classpath:/static/index.html") Resource indexHtml) {
        return route()
                .GET("/", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml))
                .build();
    }
}
