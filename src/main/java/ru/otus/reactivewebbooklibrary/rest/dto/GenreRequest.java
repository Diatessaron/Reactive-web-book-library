package ru.otus.reactivewebbooklibrary.rest.dto;

public class GenreRequest {
    private String id;
    private String genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
