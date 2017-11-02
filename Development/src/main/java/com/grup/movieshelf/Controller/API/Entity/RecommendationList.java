package com.grup.movieshelf.Controller.API.Entity;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class RecommendationList {

    private ArrayList<Title> recommendations = new ArrayList<>();

    public void addRecommendation(Title title){
        recommendations.add(title);
    }

    public void removeRecommendation(Title title){
        recommendations.remove(title);
    }
}
