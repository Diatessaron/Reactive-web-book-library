package ru.otus.reactivewebbooklibrary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document(collection = "genres")
public class Genre {
    @Id
    private String id;
    @Field("name")
    private String name;

    //TODO: Check for necessity
    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //TODO: Check for necessity
    public void setId(String id) {
        this.id = id;
    }

    public Genre setName(String name) {
        return new Genre(this.id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return name.equals(genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
