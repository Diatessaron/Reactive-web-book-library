package ru.otus.reactivewebbooklibrary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.reactivewebbooklibrary.domain.builder.BookBuilder;

import java.util.Objects;

@Document(collection = "books")
public class Book {
    @Id
    private String id;
    @Field("title")
    private String title;
    @Field("author")
    private Author author;
    @Field("genre")
    private Genre genre;

    public Book() {
    }

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String id, String title, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public BookBuilder builder() {
        return new BookBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.title) && Objects.equals(author, book.author) &&
                Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, genre);
    }

    @Override
    public String toString() {
        return "Title: " + title + '\n' +
                "Author: " + author.getName() + '\n' +
                "Genre: " + genre;
    }
}
