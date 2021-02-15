package ru.otus.reactivewebbooklibrary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    //TODO: Check for necessity
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

    //TODO: Check for necessity
    public void setId(String id) {
        this.id = id;
    }

    public Book setTitle(String title) {
        return new Book(this.id, title, this.author, this.genre);
    }

    public Book setAuthor(Author author) {
        return new Book(this.id, this.title, author, this.genre);
    }

    public Book setGenre(Genre genre) {
        return new Book(this.id, this.title, this.author, genre);
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
