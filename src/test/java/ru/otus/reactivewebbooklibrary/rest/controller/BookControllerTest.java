package ru.otus.reactivewebbooklibrary.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Author;
import ru.otus.reactivewebbooklibrary.domain.Book;
import ru.otus.reactivewebbooklibrary.domain.Genre;
import ru.otus.reactivewebbooklibrary.rest.dto.BookRequest;
import ru.otus.reactivewebbooklibrary.service.BookService;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = BookController.class)
class BookControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private BookService bookService;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    void testCreateByStatus() {
        final Book book = new Book("Modernist novel",
                new Author("James Joyce"), new Genre("Modernist novel"));

        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle(book.getTitle());
        bookRequest.setAuthorName(book.getAuthor().getName());
        bookRequest.setGenreName(book.getGenre().getName());

        client.post().uri("/api/books").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bookRequest)).exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectCreateRequest() {
        final BookRequest bookRequest = new BookRequest();

        client.post().uri("/api/books").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bookRequest)).exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testGetBookByIdByStatus() {
        when(bookService.getBookById("Book")).thenReturn(Mono.just(new Book("Book", new Author("Author"),
                new Genre("Genre"))));

        client.get().uri(uriBuilder -> uriBuilder.path("/api/books/id").queryParam("id", "Book").build())
                .exchange().expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectGetByIdRequest() {
        client.get().uri(uriBuilder -> uriBuilder.path("/api/books/id").queryParam("id", "id").build())
                .exchange().expectStatus().is4xxClientError();
    }

    @Test
    void testGetBookByTitleByStatus() {
        when(bookService.getBookByTitle("Book")).thenReturn(Flux.just(new Book("Book", new Author("Author"),
                new Genre("Genre"))));

        client.get().uri("/api/books/title/Book").exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectGetByTitleRequest() {
        client.get().uri("/api/books/title/Book").exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testGetBookByAuthorByStatus() {
        when(bookService.getBookByAuthor("Author")).thenReturn(Flux.just(new Book("Book", new Author("Author"),
                new Genre("Genre"))));

        client.get().uri("/api/books/author/Author").exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectGetByAuthorRequest() {
        client.get().uri("/api/books/author/Author").exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testGetBookByGenreByStatus() {
        when(bookService.getBookByGenre("Genre")).thenReturn(Flux.just(new Book("Book", new Author("Author"),
                new Genre("Genre"))));

        client.get().uri("/api/books/genre/Genre").exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectGetByGenreRequest() {
        client.get().uri("/api/books/genre/Genre").exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testGetAllByStatus() {
        final Book book = new Book("Book", new Author("Author"),
                new Genre("Genre"));

        when(bookService.getAll()).thenReturn(Flux.just(book));

        client.get().uri("/api/books").exchange()
                .expectStatus().isOk();
    }

    @Test
    void testEditByStatus() {
        final Book book = new Book("Book",
                new Author("Author"), new Genre("Genre"));

        final BookRequest bookRequest = new BookRequest();
        bookRequest.setId("id");

        bookRequest.setTitle("Book");
        bookRequest.setAuthorName("Author");
        bookRequest.setGenreName("Genre");

        client.put().uri("/api/books").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bookRequest))
                .exchange().expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectEditRequest() {
        final BookRequest bookRequest = new BookRequest();

        client.put().uri("/api/books").contentType(APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(bookRequest))
                .exchange().expectStatus().is4xxClientError();
    }

    @Test
    void testDeleteByTitleByStatus() {
        final BookRequest bookRequest = new BookRequest();
        bookRequest.setId("id");

        client.method(HttpMethod.DELETE).uri("/api/books")
                .body(BodyInserters.fromObject(bookRequest)).exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldReturnClientErrorStatusCodeOnIncorrectDeleteRequest() {
        final BookRequest bookRequest = new BookRequest();

        client.method(HttpMethod.DELETE).uri("/api/books")
                .body(BodyInserters.fromObject(bookRequest)).exchange()
                .expectStatus().is4xxClientError();
    }
}
