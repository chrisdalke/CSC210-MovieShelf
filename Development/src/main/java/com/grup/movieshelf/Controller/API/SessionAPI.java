///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.API;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.Controller.API.Entity.RecommendationList;
import com.grup.movieshelf.JPA.Entity.Sessions.Session;
import com.grup.movieshelf.JPA.Repository.SessionRepository;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.grup.movieshelf.Service.*;

/////////////////////////////////////////////////////////////
// Sessions API
// Handles movie-watching sessions
/////////////////////////////////////////////////////////////

@RestController
public class SessionAPI {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    @PostMapping("/api/session")
    public Session createSession(){
        return sessionService.createSession();
    }

    @GetMapping("/api/session/{sessionId}")
    public void getSession(@PathVariable("sessionId") String sessionId){

    }

    @DeleteMapping("/api/session/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") String sessionId){
        //TODO
    }

    @GetMapping("/api/session/{sessionId}/recommend")
    public RecommendationList getSessionRecommendations(@PathVariable("sessionId") String sessionId){
        return sessionService.getSessionRecommendations(sessionId);
    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////
