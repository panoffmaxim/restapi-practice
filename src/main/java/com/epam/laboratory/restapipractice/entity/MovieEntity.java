package com.epam.laboratory.restapipractice.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

public class MovieEntity {
    @NotEmpty (message = "Title can not be empty")
    private String title;

    @Past
    private LocalDate releaseDate;

    @Min(value = 0, message = "Rating should not be less than 0")
    @Max(value = 10, message = "Rating should not be greater than 10")
    private Double rating;

    public MovieEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
