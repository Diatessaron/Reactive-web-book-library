package ru.otus.reactivewebbooklibrary.rest.dto;

public class AuthorRequest {
    private String id;
    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
