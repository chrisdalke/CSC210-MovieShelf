package com.grup.movieshelf;

import com.grup.movieshelf.JPA.Entity.Sessions.RecommendationInput;
import com.grup.movieshelf.JPA.Entity.Sessions.RecommendationResult;
import com.grup.movieshelf.Service.RecommendationService;
import com.grup.movieshelf.Service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestRecommendationService implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ShelfService shelfService;

    @Autowired
    RecommendationService recommendationService;

    @Override
    @Transactional
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        // Test recommendation algorithm with Nate's shelf as the input
        RecommendationInput recommendationInput = new RecommendationInput(shelfService.getShelfForUser(9));
        RecommendationResult result = recommendationService.getRecommendations(recommendationInput);
    }
}