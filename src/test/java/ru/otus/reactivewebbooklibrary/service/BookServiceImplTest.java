package ru.otus.reactivewebbooklibrary.service;

import org.junit.jupiter.api.Test;
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
import ru.otus.reactivewebbooklibrary.repository.CommentRepository;
import ru.otus.reactivewebbooklibrary.repository.GenreRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private BookServiceImpl service;

    private final Book expectedUlysses = new Book("Ulysses", new Author("James Joyce"),
            new Genre("Modernist novel"));

    @Test
    void testSaveBookMethodWithNewParameters() {
        final Author author = new Author("Michel Foucault");
        final Genre genre = new Genre("Philosophy");
        final Book book = new Book("Discipline and Punish", author, genre);

        when(authorRepository.findByName(author.getName())).thenReturn(Flux.just(author));
        when(genreRepository.findByName(genre.getName())).thenReturn(Mono.empty());
        when(genreRepository.save(genre)).thenReturn(Mono.just(genre));
        when(bookRepository.save(book)).thenReturn(Mono.just(book));
        when(bookRepository.findByTitle(book.getTitle())).thenReturn(Flux.just(book));

        final Book actualBook = service.saveBook("Discipline and Punish",
                "Michel Foucault", "Philosophy").block();

        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().equals(""))
                .matches(s -> s.getTitle().equals("Discipline and Punish"))
                .matches(s -> s.getAuthor().getName().equals("Michel Foucault"))
                .matches(s -> s.getGenre().getName().equals("Philosophy"));
    }

    @Test
    void testSaveBookMethodWithOldAuthorAndGenre() {
        final Author author = new Author("James Joyce");
        final Genre genre = new Genre("Modernist novel");
        final Book book = new Book("A Portrait of the Artist as a Young Man", author, genre);

        when(authorRepository.findByName(author.getName())).thenReturn(Flux.just(author));
        when(genreRepository.findByName(genre.getName())).thenReturn(Mono.just(genre));
        when(bookRepository.save(book)).thenReturn(Mono.just(book));
        when(bookRepository.findByTitle(book.getTitle())).thenReturn(Flux.just(book));

        service.saveBook("A Portrait of the Artist as a Young Man",
                "James Joyce", "Modernist novel");

        final Book actualBook = service.getBookByTitle("A Portrait of the Artist as a Young Man").blockFirst();
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().equals(""))
                .matches(s -> s.getTitle().equals("A Portrait of the Artist as a Young Man"))
                .matches(s -> s.getAuthor().getName().equals("James Joyce"))
                .matches(s -> s.getGenre().getName().equals("Modernist novel"));
    }

    @Test
    void shouldReturnCorrectBookByTitle() {
        final Author author = new Author("James Joyce");
        final Genre genre = new Genre("Modernist novel");
        final Book book = new Book("Ulysses", author, genre);

        when(bookRepository.findByTitle(book.getTitle())).thenReturn(Flux.just(book));

        final Book actual = service.getBookByTitle(expectedUlysses.getTitle()).blockFirst();

        assertEquals(expectedUlysses, actual);

        verify(bookRepository, times(1)).findByTitle(book.getTitle());
    }

    @Test
    void shouldReturnCorrectBookByGenre() {
        final Author author = new Author("James Joyce");
        final Genre genre = new Genre("Modernist novel");
        final Book book = new Book("Ulysses", author, genre);

        when(bookRepository.findByGenre_Name(book.getGenre().getName())).thenReturn(Flux.just(book));

        final Book actual = service.getBookByGenre(expectedUlysses.getGenre().getName()).blockFirst();

        assertEquals(expectedUlysses, actual);

        verify(bookRepository, times(1)).findByGenre_Name(book.getGenre().getName());
    }

    @Test
    void shouldReturnCorrectListOfBooks() {
        final Author author = new Author("Michel Foucault");
        final Genre genre = new Genre("Philosophy");
        final Book book = new Book("Discipline And Punish", author, genre);
        final List<Book> expected = Flux.just(expectedUlysses, book).collectList().block();

        when(bookRepository.findAll()).thenReturn(Flux.just(expectedUlysses, book));

        final List<Book> actual = service.getAll().collectList().block();

        assertThat(actual).isNotNull().matches(a -> a.size() == expected.size());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testUpdateBookMethodWithParameters() {
        final Author author = new Author("Michel Foucault");
        final Genre genre = new Genre("Philosophy");
        final Book book = new Book("Ulysses", author, genre);

        when(authorRepository.findByName(author.getName())).thenReturn(Flux.just(author));
        when(genreRepository.findByName(genre.getName())).thenReturn(Mono.just(genre));
        when(genreRepository.save(genre)).thenReturn(Mono.just(genre));
        when(bookRepository.save(new Book("Discipline and Punish", author, genre)))
                .thenReturn(Mono.just(book));
        when(bookRepository.findById(book.getTitle())).thenReturn(Mono.just(book));
        when(commentRepository.findByBook_Title(book.getTitle())).thenReturn(Flux.just());
        when(bookRepository.findByTitle("Discipline and Punish")).thenReturn
                (Flux.just(new Book("Discipline and Punish", author, genre)));

        service.updateBook("Ulysses", "Discipline and Punish", "Michel Foucault",
                "Philosophy");

        final Book actualBook = service.getBookByTitle("Discipline and Punish").blockFirst();
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().equals(""))
                .matches(s -> s.getTitle().equals("Discipline and Punish"))
                .matches(s -> s.getAuthor().getName().equals("Michel Foucault"))
                .matches(s -> s.getGenre().getName().equals("Philosophy"));
    }

    @Test
    void testUpdateBookMethodWithOldAuthorAndGenre() {
        final Author author = new Author("James Joyce");
        final Genre genre = new Genre("Modernist novel");
        final Book book = new Book("Ulysses", author, genre);

        when(authorRepository.findByName(author.getName())).thenReturn(Flux.just(author));
        when(genreRepository.findByName(genre.getName())).thenReturn(Mono.just(genre));
        when(genreRepository.save(genre)).thenReturn(Mono.just(genre));
        when(bookRepository.save(new Book("Discipline and Punish", author, genre)))
                .thenReturn(Mono.just(book));
        when(bookRepository.findById(book.getTitle())).thenReturn(Mono.just(book));
        when(commentRepository.findByBook_Title(book.getTitle())).thenReturn(Flux.just());
        when(bookRepository.findByTitle("A Portrait of the Artist as a Young Man")).thenReturn
                (Flux.just(new Book("A Portrait of the Artist as a Young Man", author, genre)));

        service.updateBook("Ulysses", "A Portrait of the Artist as a Young Man",
                "James Joyce", "Modernist novel");

        final Book actualBook = service.getBookByTitle("A Portrait of the Artist as a Young Man").blockFirst();
        assertThat(actualBook).isNotNull().matches(s -> !s.getTitle().equals(""))
                .matches(s -> s.getTitle().equals("A Portrait of the Artist as a Young Man"))
                .matches(s -> s.getAuthor().getName().equals("James Joyce"))
                .matches(s -> s.getGenre().getName().equals("Modernist novel"));
    }
}
