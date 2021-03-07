package ru.otus.reactivewebbooklibrary.domain.builder;

import ru.otus.reactivewebbooklibrary.domain.Comment;

public class CommentBuilder {
    private String id;
    private String content;
    private String book;

    public CommentBuilder() {
    }

    public CommentBuilder(String id, String content, String book) {
        this.id = id;
        this.content = content;
        this.book = book;
    }

    public CommentBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CommentBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public CommentBuilder setBook(String book) {
        this.book = book;
        return this;
    }

    public Comment build() {
        return new Comment(id, content, book);
    }
}
