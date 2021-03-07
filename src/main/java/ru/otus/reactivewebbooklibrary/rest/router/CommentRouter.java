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
public class CommentRouter {
    @Bean
    public RouterFunction<ServerResponse> commentPagesRouter(@Value("classpath:/static/comment.html") Resource commentHtml,
                                                             @Value("classpath:/static/commentEdit.html") Resource commentEditHtml,
                                                             @Value("classpath:/static/commentList.html") Resource commentListHtml,
                                                             @Value("classpath:/static/commentListByBook.html")
                                                                     Resource commentListByBookHtml,
                                                             @Value("classpath:/static/commentSave.html") Resource commentSaveHtml) {
        return route()
                .GET("/comments/add", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(commentSaveHtml))
                .GET("/comments/id", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(commentHtml))
                .GET("/comments/book/{bookTitle}", request -> ok().contentType(MediaType.TEXT_HTML)
                        .bodyValue(commentListByBookHtml))
                .GET("/comments", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(commentListHtml))
                .GET("/comments/edit", request -> ok().contentType(MediaType.TEXT_HTML).bodyValue(commentEditHtml))
                .build();
    }
}
