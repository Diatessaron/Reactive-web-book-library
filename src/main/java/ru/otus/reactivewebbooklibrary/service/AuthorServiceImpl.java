package ru.otus.reactivewebbooklibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Author;
import ru.otus.reactivewebbooklibrary.domain.Book;
import ru.otus.reactivewebbooklibrary.repository.AuthorRepository;
import ru.otus.reactivewebbooklibrary.repository.BookRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public Mono<Author> save(String authorName) {
        return authorRepository.save(new Author(authorName));
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Author> getAuthorsByName(String name) {
        return authorRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Author> getAll() {
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public Mono<Void> updateAuthor(String id, String name) {
        final Flux<Book> bookSave = bookRepository.findByAuthor_Id(id).map
                (b -> b.setAuthor(b.getAuthor().setName(name)))
                .flatMap(bookRepository::save);

        final Mono<Author> authorSave = authorRepository.findById(id).map(a -> a.setName(name))
                .flatMap(authorRepository::save);

        return bookSave.zipWith(authorSave).then();
    }

    @Transactional
    @Override
    public Mono<Void> deleteAuthor(String id) {
        return bookRepository.deleteByAuthor_Id(id)
                .zipWith(authorRepository.deleteById(id)).then();
    }
}
