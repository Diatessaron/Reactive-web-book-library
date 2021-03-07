package ru.otus.reactivewebbooklibrary.domain.builder;

import ru.otus.reactivewebbooklibrary.domain.Genre;

public class GenreBuilder {
    private String id;
    private String name;

    public GenreBuilder() {
    }

    public GenreBuilder(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public GenreBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Genre build() {
        return new Genre(id, name);
    }
}
