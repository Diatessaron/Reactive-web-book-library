package ru.otus.reactivewebbooklibrary.service;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.reactivewebbooklibrary.domain.Author;
import ru.otus.reactivewebbooklibrary.domain.Book;
import ru.otus.reactivewebbooklibrary.domain.Comment;
import ru.otus.reactivewebbooklibrary.domain.Genre;
import ru.otus.reactivewebbooklibrary.repository.BookRepository;
import ru.otus.reactivewebbooklibrary.repository.CommentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataMongoTest
@Import(CommentServiceImpl.class)
class CommentServiceImplTest {
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private CommentServiceImpl commentService;

    private final Book ulysses = new Book("Ulysses", new Author("James Joyce"),
            new Genre("Modernist novel"));
    private final Comment ulyssesComment = new Comment("Published in 1922", ulysses.getTitle());

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void testSaveByComparing() {
        final Author foucault = new Author("Michel Foucault");
        final Genre philosophy = new Genre("Philosophy");
        final Book book = new Book("Discipline and Punish", foucault, philosophy);
        final Comment expected = new Comment("Published in 1975", book.getTitle());

        when(bookRepository.save(book)).thenReturn(Mono.just(book));
        when(bookRepository.findByTitle(book.getTitle())).thenReturn(Flux.just(book));
        when(commentRepository.save(expected)).thenReturn(Mono.just(expected));
        when(commentRepository.save(expected)).thenReturn(Mono.just(expected));
        when(commentRepository.findByContent(expected.getContent())).thenReturn(Flux.just(expected));

        bookRepository.save(book);
        commentService.saveComment(expected.getBook().getTitle(), expected.getContent());
        final Comment actual = commentService.getCommentByContent(expected.getContent()).blockFirst();

        assertEquals(expected.getContent(), actual.getContent());

        final InOrder inOrder = inOrder(bookRepository, commentRepository);
        inOrder.verify(bookRepository).save(book);
        inOrder.verify(commentRepository).save(expected);
        inOrder.verify(commentRepository).findByContent(expected.getContent());
    }

    @Test
    void shouldReturnCorrectCommentByContent() {
        when(commentRepository.findByContent(ulyssesComment.getContent())).thenReturn
                (Flux.just(ulyssesComment));

        final Comment actual = commentService.getCommentByContent(ulyssesComment.getContent()).blockFirst();

        assertEquals(ulyssesComment, actual);

        verify(commentRepository, times(1)).findByContent(ulyssesComment.getContent());
    }

    @Test
    void testGetCommentByBookMethod() {
        when(bookRepository.findByTitle(ulysses.getTitle())).thenReturn(Flux.just(ulysses));
        when(commentRepository.findByBook_Title(ulysses.getTitle())).thenReturn(Flux.just(ulyssesComment));

        final List<Comment> expected = List.of(ulyssesComment);
        final List<Comment> actual = commentService.getCommentsByBook("Ulysses").collectList().block();

        assertEquals(expected, actual);

        final InOrder inOrder = inOrder(bookRepository, commentRepository);
        inOrder.verify(commentRepository).findByBook_Title(ulysses.getTitle());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void shouldReturnCorrectListOfComments() {
        final Author foucault = new Author("Michel Foucault");
        final Genre philosophy = new Genre("Philosophy");
        final Book book = new Book("Discipline and Punish", foucault, philosophy);
        final Comment disciplineAndPunishComment = new Comment("Published in 1975", book.getTitle());

        final List<Comment> expected = List.of(this.ulyssesComment, disciplineAndPunishComment);

        when(commentRepository.findAll()).thenReturn(Flux.just(ulyssesComment, disciplineAndPunishComment));

        final List<Comment> actual = commentService.getAll().collectList().block();

        assertEquals(expected, actual);

        final InOrder inOrder = inOrder(commentRepository);
        inOrder.verify(commentRepository).findAll();
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void shouldUpdateCommentCorrectly() {
        final Comment comment = new Comment("Comment", ulysses.getTitle());

        when(commentRepository.findById(ulyssesComment.getContent())).thenReturn
                (Mono.just(ulyssesComment));
        when(commentRepository.save(comment)).thenReturn(Mono.just(comment));
        when(commentRepository.findByContent(comment.getContent())).thenReturn(Flux.just(comment));

        final Comment actualComment = commentService.updateComment("Published in 1922", "Comment").block();

        assertThat(actualComment).isNotNull().matches(s -> !s.getContent().isBlank())
                .matches(s -> s.getContent().equals("Comment"));

        final InOrder inOrder = inOrder(bookRepository, commentRepository);
        inOrder.verify(commentRepository).findById("Published in 1922");
        inOrder.verify(commentRepository).save(comment);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void testDeleteByIdMethodByResultStringComparing() {
        commentService.deleteComment(ulyssesComment.getContent());

        verify(commentRepository, times(1)).deleteById(ulyssesComment.getContent());
    }
}