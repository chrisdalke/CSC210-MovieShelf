///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Service;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Session;
import com.grup.movieshelf.JPA.Repository.SessionRepository;
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
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////