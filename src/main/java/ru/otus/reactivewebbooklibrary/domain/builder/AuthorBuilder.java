package ru.otus.reactivewebbooklibrary.domain.builder;

import ru.otus.reactivewebbooklibrary.domain.Author;

public class AuthorBuilder {
    private String id;
    private String name;

    public AuthorBuilder() {
    }

    public AuthorBuilder(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public AuthorBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Author build() {
        return new Author(id, name);
    }
}
