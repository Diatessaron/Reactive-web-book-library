package ru.otus.reactivewebbooklibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Author;
import ru.otus.reactivewebbooklibrary.domain.Book;
import ru.otus.reactivewebbooklibrary.domain.Comment;
import ru.otus.reactivewebbooklibrary.domain.Genre;
import ru.otus.reactivewebbooklibrary.repository.AuthorRepository;
import ru.otus.reactivewebbooklibrary.repository.BookRepository;
import ru.otus.reactivewebbooklibrary.repository.CommentRepository;
import ru.otus.reactivewebbooklibrary.repository.GenreRepository;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public Mono<Book> saveBook(String title, String authorNameParameter, String genreNameParameter) {
        return getAuthor(authorNameParameter)
                .flatMap(a -> {
                    Book book = new Book();
                    book = book.setTitle(title);
                    return Mono.just(book.setAuthor(a));
                })
                .flatMap(b -> getGenre(genreNameParameter)
                        .flatMap(g -> Mono.just(b.setGenre(g))))
                .flatMap(bookRepository::save);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Book> getBookByAuthor(String author) {
        return bookRepository.findByAuthor_Name(author);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Book> getBookByGenre(String genre) {
        return bookRepository.findByGenre_Name(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Book> getBookByComment(String commentId) {
        return commentRepository.findById(commentId)
                .flatMap(c -> bookRepository.findByTitle(c.getBook().getTitle()).next());
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public Mono<Tuple2<Flux<Comment>, Book>> updateBook(String id, String title, String authorNameParameter,
                                                        String genreNameParameter) {
        return bookRepository.findById(id)
                .map(b -> commentRepository.saveAll(commentRepository.findByBook_Title(b.getTitle())
                        .flatMap(c -> Mono.just(c.setBook(title)))))
                .zipWith(bookRepository.findById(id).flatMap(b -> getAuthor(authorNameParameter)
                            .flatMap(a -> Mono.just(b.setAuthor(a)))
                            .flatMap(book -> Mono.just(book.setTitle(title)))
                            .flatMap(book -> getGenre(genreNameParameter)
                                    .flatMap(g -> Mono.just(book.setGenre(g))))));
    }

    @Transactional
    @Override
    public Mono<Tuple2<Void, Void>> deleteBook(String id) {
        return bookRepository.findById(id).flatMap(b -> commentRepository.deleteByBook_Title(b.getTitle()))
                .zipWith(bookRepository.deleteById(id));
    }

    private Mono<Author> getAuthor(String authorName) {
        return authorRepository.findByName(authorName).collectList()
                .flatMap(authors -> {
                    if (authors.isEmpty()) {
                        return authorRepository.save(new Author(authorName));
                    } else
                        return Mono.just(authors.get(0));
                });
    }

    private Mono<Genre> getGenre(String genreName) {
        return genreRepository.findByName(genreName)
                .switchIfEmpty(genreRepository.save(new Genre(genreName)));
    }
}
