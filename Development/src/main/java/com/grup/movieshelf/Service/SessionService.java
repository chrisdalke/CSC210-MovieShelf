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
import com.grup.movieshelf.JPA.Entity.Sessions.Session;
import com.grup.movieshelf.JPA.Entity.Sessions.SessionResult;
import com.grup.movieshelf.JPA.Entity.Sessions.UserSession;
import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Repository.*;
import com.grup.movieshelf.Utility.RandomStringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/////////////////////////////////////////////////////////////
// Sessions Service
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
    private UserSessionRepository userSessionRepository;

    @Autowired
    private SessionResultRepository sessionResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private UserService userService;


    //------------------------------------------------
    // Sessions Creation / Deletion
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
        session = sessionRepository.save(session);

        return session;
    }

    public void deleteSession(Session session){
        // Dont actually delete, just archive
        session.setExpired(true);
        sessionRepository.save(session);
    }

    public Session getSession(String sessionCode){
        return sessionRepository.getBySessionCode(sessionCode);
    }

    public void finishSession(Integer sessionId){
        if (sessionRepository.existsById(sessionId)){
            Session session = sessionRepository.getOne(sessionId);
            if (!session.isExpired()){
                // Step 1: Dispatch messages telling all users to display a loading screen

                // Step 2: Start async task to get recommendations

                // Step 3: After async task ends, dispatch message telling all users to redirect to results page

                session.setExpired(true);
                sessionRepository.save(session);
            }
        }
    }

    //------------------------------------------------
    // Sessions User Movie Lists
    //------------------------------------------------

    public void addSuggestion(){

    }

    public void removeSuggestion(){

    }

    //------------------------------------------------
    // Session / User Relation
    //------------------------------------------------

    public void addUserToSession(Integer userId, Integer sessionId){
        UserSession userSession = new UserSession(userId,sessionId);
        if (!userSessionRepository.existsById(userSession.getUserSessionId())){
            userSessionRepository.save(userSession);
        }
    }

    public void removeUserFromSession(Integer userId, Integer sessionId){
        UserSession userSession = new UserSession(userId,sessionId);
        if (userSessionRepository.existsById(userSession.getUserSessionId())){
            // Delete user/session entry
            userSessionRepository.delete(userSession);
            // Delete all the user's session movie suggestions so they don't get used
        }
    }

    public boolean userInSession(Integer userId, Integer sessionId){
        UserSession userSession = new UserSession(userId,sessionId);
        return userSessionRepository.existsById(userSession.getUserSessionId());
    }

    public List<Session> getSessionsForUser(Integer userId){
        ArrayList<Session> sessions = new ArrayList<>();
        if (userRepository.existsById(userId)){
            for (UserSession userSession : userSessionRepository.getAllByUserId(userId)){
                sessions.add(sessionRepository.getOne(userSession.getSessionId()));
            }
        }
        return sessions;
    }

    public List<User> getUsersForSession(Integer sessionId){
        ArrayList<User> sessions = new ArrayList<>();
        if (sessionRepository.existsById(sessionId)){
            for (UserSession userSession : userSessionRepository.getAllBySessionId(sessionId)){
                sessions.add(userRepository.findByUserId(userSession.getUserId()));
            }
        }
        return sessions;
    }

    public List<Session> getSessionsForUser(User user) {
        return getSessionsForUser(user.getUserId());
    }


    public List<User> getUsersForSession(Session session){
        return getUsersForSession(session.getSessionId());
    }


    //------------------------------------------------
    // Sessions Recommendations
    //------------------------------------------------

    public RecommendationList getSessionRecommendations(Integer sessionId){


        RecommendationList recommendationResults = new RecommendationList();

        for (SessionResult results : sessionResultRepository.getAllBySessionId(sessionId)){
            recommendationResults.addRecommendation(titleRepository.getByTitleId(results.getTitleId()));
        }

        return recommendationResults;
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////