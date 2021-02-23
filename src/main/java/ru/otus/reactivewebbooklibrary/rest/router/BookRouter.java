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
public class BookRouter {
    @Bean
    public RouterFunction<ServerResponse> bookPagesRouter(@Value("classpath:/static/bookByComment.html") Resource bookByCommentHtml,
                                                      @Value("classpath:/static/bookById.html") Resource bookByIdHtml,
                                                      @Value("classpath:/static/bookEdit.html") Resource bookEditHtml,
                                                      @Value("classpath:/static/bookList.html") Resource bookListHtml,
                                                      @Value("classpath:/static/bookSave.html") Resource bookSaveHtml,
                                                      @Value("classpath:/static/booksByAuthor.html") Resource booksByAuthorHtml,
                                                      @Value("classpath:/static/booksByGenre.html") Resource booksByGenreHtml) {
        return route()
                .GET("/books/add", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(bookSaveHtml))
                .GET("/books/id", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(bookByIdHtml))
                .GET("/books/author/{author}", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(booksByAuthorHtml))
                .GET("/books/genre/{genre}", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(booksByGenreHtml))
                .GET("/books/comment", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(bookByCommentHtml))
                .GET("/books", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(bookListHtml))
                .GET("/books/edit", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(bookEditHtml))
                .build();
    }
}
