package ru.otus.reactivewebbooklibrary.service;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Author;
import ru.otus.reactivewebbooklibrary.domain.Book;
import ru.otus.reactivewebbooklibrary.domain.Genre;
import ru.otus.reactivewebbooklibrary.repository.BookRepository;
import ru.otus.reactivewebbooklibrary.repository.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class GenreServiceImplTest {
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private GenreServiceImpl service;

    private final Genre expectedNovel = new Genre("Modernist novel");

    @Test
    void testSaveByComparing() {
        final Genre philosophy = new Genre("Philosophy");

        when(genreRepository.save(philosophy)).thenReturn(Mono.just(philosophy));
        when(genreRepository.findByName(philosophy.getName())).thenReturn(Mono.just(philosophy));

        service.saveGenre(philosophy.getName());

        final Genre actual = service.getGenreByName("Philosophy").block();

        assertEquals(philosophy.getName(), actual.getName());

        verify(genreRepository, times(1)).save(philosophy);
    }

    @Test
    void shouldReturnCorrectGenreByName() {
        when(genreRepository.findByName(expectedNovel.getName())).thenReturn(Mono.just(expectedNovel));

        final Genre actual = service.getGenreByName(expectedNovel.getName()).block();

        assertEquals(expectedNovel, actual);

        verify(genreRepository, times(1)).findByName(expectedNovel.getName());
    }

    @Test
    void shouldReturnCorrectListOfGenre() {
        final Genre philosophy = new Genre("Philosophy");
        final List<Genre> expected = Flux.just(expectedNovel, philosophy).collectList().block();

        when(genreRepository.save(philosophy)).thenReturn(Mono.just(philosophy));
        when(genreRepository.findAll()).thenReturn(Flux.just(expectedNovel, philosophy));

        service.saveGenre(philosophy.getName());

        final List<Genre> actual = service.getAll().collectList().block();

        assertThat(actual).isNotNull().matches(a -> a.size() == expected.size())
                .matches(a -> a.get(0).getName().equals(expected.get(0).getName()))
                .matches(a -> a.get(1).getName().equals(expected.get(1).getName()));

        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void testUpdateGenreMethodByComparing() {
        final Genre genre = new Genre("Genre");
        final Book book = new Book("Book", new Author("James Joyce"), expectedNovel);

        when(genreRepository.findById(expectedNovel.getName())).thenReturn(Mono.just(expectedNovel));
        when(genreRepository.save(genre)).thenReturn(Mono.just(genre));
        when(bookRepository.findByGenre_Id(expectedNovel.getId())).thenReturn(Flux.just(book));

        final Mono<Tuple2<Flux<Book>, Genre>> source = service.updateGenre("Modernist novel", "Genre");
        final Genre actualGenre = source.block().getT2();

        assertThat(actualGenre).isNotNull().matches(s -> !s.getName().isBlank())
                .matches(s -> s.getName().equals("Genre"));

        final InOrder inOrder = inOrder(genreRepository, bookRepository);
        inOrder.verify(genreRepository, times(2)).findById("Modernist novel");
        inOrder.verify(bookRepository).findByGenre_Id(genre.getId());
        inOrder.verify(genreRepository).save(genre);
    }
}
