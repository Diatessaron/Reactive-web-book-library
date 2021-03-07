package ru.otus.reactivewebbooklibrary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.reactivewebbooklibrary.domain.builder.CommentBuilder;

import java.util.Objects;

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    @Field("content")
    private String content;
    @Field("book")
    private Book book;

    public Comment() {
    }

    public Comment(String content, String bookTitle) {
        this.content = content;
        this.book = new Book();
        this.book = this.book.builder().setTitle(bookTitle).build();
    }

    public Comment(String id, String content, String bookTitle) {
        this.id = id;
        this.content = content;
        this.book = new Book();
        this.book = this.book.builder().setTitle(bookTitle).build();
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Book getBook() {
        return book;
    }

    public CommentBuilder builder() {
        return new CommentBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return content.equals(comment.content) && book.equals(comment.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, book);
    }

    @Override
    public String toString() {
        return "Comment '" + content +
                "' to book " + book.getTitle();
    }
}
