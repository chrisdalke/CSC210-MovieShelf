package com.grup.movieshelf.JPA.Entity.Sessions;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RecommendationInput {
    List<Title> titles;

    public RecommendationInput() {
        titles = new ArrayList<>();
    }
}
