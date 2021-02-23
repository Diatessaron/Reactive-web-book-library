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
public class GenreRouter {
    @Bean
    public RouterFunction<ServerResponse> genrePagesRouter(@Value("classpath:/static/genre.html") Resource genreHtml,
                                                      @Value("classpath:/static/genreEdit.html") Resource genreEditHtml,
                                                      @Value("classpath:/static/genreSave.html") Resource genreSaveHtml,
                                                      @Value("classpath:/static/genreList.html") Resource genreListHtml) {
        return route()
                .GET("/genres/add", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(genreSaveHtml))
                .GET("/genres/id", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(genreHtml))
                .GET("/genres", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(genreListHtml))
                .GET("/genres/edit", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(genreEditHtml))
                .build();
    }
}
