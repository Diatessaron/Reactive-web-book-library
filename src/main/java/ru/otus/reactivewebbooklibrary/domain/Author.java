package ru.otus.reactivewebbooklibrary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    @Field("name")
    private String name;

    //TODO: Check for necessity
    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(String id, String name) {
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

    public Author setName(String name) {
        return new Author(this.id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return name.equals(author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Author: " + name;
    }
}
