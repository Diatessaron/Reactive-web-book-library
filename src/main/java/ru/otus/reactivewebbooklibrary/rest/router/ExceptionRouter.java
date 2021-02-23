package ru.otus.reactivewebbooklibrary.rest.router;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
public class ExceptionRouter extends AbstractErrorWebExceptionHandler {

    public ExceptionRouter(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                           ApplicationContext applicationContext) {
        super(errorAttributes, resources, applicationContext);

        ServerCodecConfigurer serverCodecConfigurer = new DefaultServerCodecConfigurer();
        setMessageWriters(serverCodecConfigurer.getWriters());
        setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
        return ServerResponse.status(HttpStatus.BAD_REQUEST).build();
    }
}
