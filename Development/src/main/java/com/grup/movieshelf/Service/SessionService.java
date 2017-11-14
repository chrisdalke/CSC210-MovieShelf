///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Service;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.Controller.API.Entity.RecommendationList;
import com.grup.movieshelf.JPA.Entity.Session;
import com.grup.movieshelf.JPA.Repository.SessionRepository;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import com.grup.movieshelf.Utility.RandomStringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////////////////////
// Session Service
// Handles data tasks associated with a session.
/////////////////////////////////////////////////////////////

@Service
public class SessionService {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TitleRepository titleRepository;

    //------------------------------------------------
    // Session Creation / Deletion
    //------------------------------------------------

    public Session createSession(){
        Session session = new Session();
        session.setSessionCode(RandomStringUtility.generateSessionCodeString(6));
        session.setExpired(false);

        // make sure the session code is unique
        while (sessionRepository.existsBySessionCode(session.getSessionCode())){
            session.setSessionCode(RandomStringUtility.generateSessionCodeString(6));
        }

        // Save and return the new session
        sessionRepository.save(session);

        return session;
    }

    public void deleteSession(Session session){
        // Dont actually delete, just archive
        session.setExpired(true);
        sessionRepository.save(session);
    }

    //------------------------------------------------
    // Session Recommendations
    //------------------------------------------------

    public RecommendationList getSessionRecommendations(String sessionId){

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

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////