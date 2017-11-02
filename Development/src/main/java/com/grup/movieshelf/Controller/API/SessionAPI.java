package com.grup.movieshelf.Controller.API;

import com.grup.movieshelf.Controller.API.Entity.RecommendationList;
import com.grup.movieshelf.JPA.Entity.Session;
import com.grup.movieshelf.JPA.Repository.SessionRepository;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SessionAPI {

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/api/session")
    public Session createSession(){
        Session newSession = new Session();

        String sessionCode;
        do {
            sessionCode = Session.generateSessionCode();
        } while (sessionRepository.existsBySessionCode(sessionCode));

        newSession.setSessionCode(sessionCode);
        sessionRepository.save(newSession);

        return newSession;
    }

    @GetMapping("/api/session/{sessionId}")
    public void getSession(@PathVariable("sessionId") String sessionId){

    }

    @DeleteMapping("/api/session/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") String sessionId){

    }

    @GetMapping("/api/session/{sessionId}/recommend")
    public RecommendationList getSessionRecommendations(@PathVariable("sessionId") String sessionId){

        // Based on the users in this session and their movie choices, generate a movie recommendation.
        // Return it as a RecommendationList object containing movie titles encoded as JSON.
        // This should probably only be called a single time, because it takes a long time and returns random results.
        // TODO

        RecommendationList recommendationResults = new RecommendationList();

        // For now, let's just add some movies we know everyone likes...
        recommendationResults.addRecommendation(titleRepository.getByTitleId("tt1375666")); // Inception
        recommendationResults.addRecommendation(titleRepository.getByTitleId("tt0816692")); // Interstellar
        recommendationResults.addRecommendation(titleRepository.getByTitleId("tt3659388")); // The Martian

        return recommendationResults;
    }

}
