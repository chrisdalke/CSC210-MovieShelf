package com.grup.movieshelf.JPA.Entity.Sessions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RecommendationResult {
    List<Recommendation> recommendations;

    public RecommendationResult() {
        recommendations = new ArrayList<>();
    }
}
