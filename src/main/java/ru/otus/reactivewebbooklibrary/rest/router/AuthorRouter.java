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
public class AuthorRouter {
    @Bean
    public RouterFunction<ServerResponse> authorPagesRouter(
            @Value("classpath:/static/author.html") Resource authorHtml,
            @Value("classpath:/static/authorEdit.html") Resource authorEditHtml,
            @Value("classpath:/static/authorList.html") Resource authorListHtml,
            @Value("classpath:/static/authorSave.html") Resource authorSaveHtml
    ) {
        return route()
                .GET("/authors/add", request -> ok().contentType(MediaType.TEXT_HTML)
                        .bodyValue(authorSaveHtml))
                .GET("/authors", request -> ok().contentType(MediaType.TEXT_HTML)
                        .bodyValue(authorListHtml))
                .GET("/authors/id", request -> ok().contentType(MediaType.TEXT_HTML)
                        .bodyValue(authorHtml))
                .GET("/authors/edit", request -> ok().contentType(MediaType.TEXT_HTML)
                        .bodyValue(authorEditHtml))
                .build();
    }
}
