package ru.otus.reactivewebbooklibrary.service;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Author;
import ru.otus.reactivewebbooklibrary.domain.Book;
import ru.otus.reactivewebbooklibrary.domain.Genre;
import ru.otus.reactivewebbooklibrary.repository.AuthorRepository;
import ru.otus.reactivewebbooklibrary.repository.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthorServiceImplTest {
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private AuthorServiceImpl service;

    private final Author jamesJoyce = new Author("James Joyce");

    @Test
    void testSaveByComparing() {
        final Author foucault = new Author("Michel Foucault");

        when(authorRepository.save(foucault)).thenReturn(Mono.just(foucault));
        when(authorRepository.findByName(foucault.getName())).thenReturn(Flux.just(foucault));

        service.save(foucault.getName());

        final Author actual = service.getAuthorsByName("Michel Foucault").blockFirst();

        assertNotNull(actual);
        assertEquals(foucault.getName(), actual.getName());

        verify(authorRepository, times(1)).save(foucault);
    }

    @Test
    void shouldReturnCorrectAuthorByName() {
        when(authorRepository.findByName(jamesJoyce.getName())).thenReturn(Flux.just(jamesJoyce));

        final Author actual = service.getAuthorsByName(jamesJoyce.getName()).blockFirst();

        assertEquals(jamesJoyce, actual);

        verify(authorRepository, times(1)).findByName(jamesJoyce.getName());
    }

    @Test
    void testUpdateAuthorMethodByComparing() {
        final Author author = new Author("Author");
        final Book book = new Book("Book", jamesJoyce, new Genre("Modernist novel"));

        when(authorRepository.findById(jamesJoyce.getId())).thenReturn(Mono.just(jamesJoyce));
        when(authorRepository.save(author)).thenReturn(Mono.just(author));
        when(bookRepository.findByAuthor_Id(jamesJoyce.getId())).thenReturn(Flux.just(book));
        when(bookRepository.save(book.setAuthor(author))).thenReturn(Mono.just(book.setAuthor(author)));

        final Author actualAuthor = service.updateAuthor(jamesJoyce.getId(), author.getName()).blockFirst().getT2();

        assertThat(actualAuthor).isNotNull().matches(s -> !s.getName().isBlank())
                .matches(s -> s.getName().equals(author.getName()));

        final InOrder inOrder = inOrder(authorRepository, bookRepository);
        inOrder.verify(bookRepository).findByAuthor_Id(jamesJoyce.getId());
        inOrder.verify(authorRepository, times(2)).findById(jamesJoyce.getId());
        inOrder.verify(authorRepository).save(author);
    }
}
