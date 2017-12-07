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
import com.grup.movieshelf.Controller.API.Entity.ResponseStatus;
import com.grup.movieshelf.Controller.API.Entity.SessionCreationRequest;
import com.grup.movieshelf.Controller.API.Entity.UserSessionData;
import com.grup.movieshelf.JPA.Entity.Sessions.Session;
import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Repository.SessionRepository;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import com.grup.movieshelf.JPA.Repository.UserSessionRepository;
import com.grup.movieshelf.JPA.Repository.UserSuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.grup.movieshelf.Service.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserSuggestionRepository userSuggestionRepository;

    @Autowired
    private SessionService sessionService;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    @PostMapping("/api/session")
    public Session createSession(@RequestBody SessionCreationRequest sessionCreationRequest){
        Session newSession = sessionService.createSession();
        newSession = sessionService.getSession(newSession.getSessionCode());
        newSession.setSessionName(sessionCreationRequest.getSessionName());
        sessionRepository.save(newSession);
        return newSession;
    }

    @GetMapping("/api/session/{sessionCode}")
    public Session getSession(@PathVariable("sessionCode") String sessionCode){
        return sessionService.getSession(sessionCode);
    }

    @DeleteMapping("/api/session/{sessionId}")
    public void deleteSession(@PathVariable("sessionId") String sessionId){
        //TODO
    }

    @DeleteMapping("/api/session/{sessionId}/finish")
    public void finishSession(@PathVariable("sessionId") Integer sessionId){
        // Finishes a session and triggers session service to asyncronously handle recommendation algorithm.
        // session service will automatically tell clients to redirect when it has finished algorithm.
        sessionService.finishSession(sessionId);
    }

    @GetMapping("/api/session/{sessionId}/recommend")
    public RecommendationList getSessionRecommendations(@PathVariable("sessionId") String sessionId){
        return sessionService.getSessionRecommendations(sessionId);
    }

    //------------------------------------------------
    // Session Join Functionality
    //------------------------------------------------

    @GetMapping("/api/public/session/{sessionCode}/join_check")
    public ResponseStatus getSessionJoinable(@PathVariable("sessionCode") String sessionCode){
        Session session = sessionService.getSession(sessionCode);
        if (session != null){
            if (session.isExpired()){
                return new ResponseStatus(2,"Session Code has Expired!");
            } else {
                return new ResponseStatus();
            }
        } else {
            return new ResponseStatus(1,"Invalid Session Code!");
        }
    }

    @GetMapping("/api/public/session/{sessionId}/users_other")
    public List<UserSessionData> getOtherSessionUsers(@PathVariable("sessionId") Integer sessionId){
        Session session = sessionRepository.getOne(sessionId);
        if (session != null){

            List<User> users = sessionService.getUsersForSession(session);
            List<UserSessionData> userSessionDataList = new ArrayList<>();

            for (User user : users){
                UserSessionData userSessionData = new UserSessionData();
                userSessionData.setUser(user);
                userSessionData.setUserSession(userSessionRepository.getOne(user.getUserId() + "_" + session.getSessionId()));
                userSessionData.setUserSuggestionList(userSuggestionRepository.getAllByUserIdAndSessionId(user.getUserId(),session.getSessionId()));
                userSessionDataList.add(userSessionData);
            }

            return userSessionDataList;
        } else {
            return new ArrayList<>();
        }
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////
