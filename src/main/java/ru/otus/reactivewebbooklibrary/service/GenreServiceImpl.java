package ru.otus.reactivewebbooklibrary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.otus.reactivewebbooklibrary.domain.Genre;
import ru.otus.reactivewebbooklibrary.repository.BookRepository;
import ru.otus.reactivewebbooklibrary.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public GenreServiceImpl(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Override
    public Mono<Genre> saveGenre(String name) {
        return genreRepository.save(new Genre(name));
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Genre> getGenreById(String id) {
        return genreRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<Genre> getGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Genre> getAll() {
        return genreRepository.findAll();
    }

    //TODO: Check book update
    @Transactional
    @Override
    public Mono<Genre> updateGenre(String id, String name) {
        return genreRepository.findById(id)
                .flatMap(g -> {
                    bookRepository.findByGenre_Id(g.getId())
                            .flatMap(b -> bookRepository.save(b.setGenre(g.setName(name))));
                    return Mono.just(g.setName(name));
                }).flatMap(genreRepository::save);
    }

    //TODO: Check book delete
    @Transactional
    @Override
    public Mono<Tuple2<Void, Void>> deleteGenre(String id) {
        return bookRepository.deleteByGenre_Id(id)
                .zipWith(genreRepository.deleteById(id));
    }
}
