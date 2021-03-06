///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Realtime;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////
// Sessions Socket Manager
// Handles realtime bidirectional communication for sessions.
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Sessions.*;
import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Repository.SessionRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import com.grup.movieshelf.JPA.Repository.UserSessionRepository;
import com.grup.movieshelf.JPA.Repository.UserSuggestionRepository;
import com.grup.movieshelf.Realtime.Entity.SessionMessage;
import com.grup.movieshelf.Realtime.Entity.SessionServerMessage;
import com.grup.movieshelf.Service.MetadataService;
import com.grup.movieshelf.Service.RecommendationService;
import com.grup.movieshelf.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
public class SessionSocketController {

    @Autowired
    private UserService userService;

    @Autowired
    UserSessionRepository userSessionRepository;

    @Autowired
    UserSuggestionRepository userSuggestionRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    MetadataService metadataService;


    @Autowired
    UserRepository userRepository;

    @Autowired
    RecommendationService recommendationService;

    @Autowired
    private SimpMessagingTemplate template;

    /*

    public static final int MESSAGE_CONFIRM = -2;
    public static final int MESSAGE_ERROR = -1;
    public static final int MESSAGE_JOIN = 0;
    public static final int MESSAGE_LEAVE = 1;
    public static final int MESSAGE_ADD_MOVIE = 2;
    public static final int MESSAGE_REMOVE_MOVIE = 3;
    public static final int MESSAGE_READY = 4;
    public static final int MESSAGE_READY_CANCEL = 5;
    public static final int MESSAGE_FLAIR = 6;

    public static final int MESSAGE_TEST = 999;
     */

    @MessageMapping("/session/{sessionId}")
    @SendTo("/topic/session/{sessionId}")
    public SessionServerMessage sessionMessageManager(@DestinationVariable String sessionId, SessionMessage clientMessage){

        User userObject = userRepository.getOne(clientMessage.getUserId());

        System.out.println("Received message from socket: "+clientMessage+ " and user "+userObject);

        switch (clientMessage.getMessageType()){
            case SessionMessage.MESSAGE_JOIN: {
                // User has joined the server. Send a message to all clients telling them to update their client list.
                return new SessionServerMessage(SessionServerMessage.MESSAGE_REFRESH_USERS,"User Joined.");
            }
            case SessionMessage.MESSAGE_LEAVE: {
                // User has left the server. Send a message to all clients telling them to update their client list.
                return new SessionServerMessage(SessionServerMessage.MESSAGE_REFRESH_USERS,"User Left.");
            }
            case SessionMessage.MESSAGE_ADD_MOVIE: {
                // Update database and send update message to all the other users
                UserSuggestion userSuggestion = new UserSuggestion(userObject.getUserId(),Integer.parseInt(sessionId),clientMessage.getContent());
                userSuggestionRepository.save(userSuggestion);

                // Realtime: After sending message, get metadata for this movie in preparation

                return new SessionServerMessage(SessionServerMessage.MESSAGE_REFRESH_USERS,"User Added Movie.");
            }
            case SessionMessage.MESSAGE_REMOVE_MOVIE: {
                // Update database and send update message to all the other users
                UserSuggestion userSuggestion = new UserSuggestion(userObject.getUserId(),Integer.parseInt(sessionId),clientMessage.getContent());
                userSuggestionRepository.delete(userSuggestion);

                return new SessionServerMessage(SessionServerMessage.MESSAGE_REFRESH_USERS,"User Removed Movie.");
            }
            case SessionMessage.MESSAGE_READY: {
                // Set this user as being ready.
                // Get the user session object
                UserSession userSession = userSessionRepository.getOne(userObject.getUserId()+"_"+sessionId);
                userSession.setIsReady(1);
                userSessionRepository.save(userSession);

                // Check if all users are ready. If so, send a page load event.
                List<UserSession> userSessionList = userSessionRepository.getAllBySessionId(Integer.parseInt(sessionId));
                boolean allReady = true;
                for (UserSession userSession1 : userSessionList){
                    if (userSession1.getIsReady() == 0){
                        allReady = false;
                    }
                }

                Session session = sessionRepository.getOne(Integer.parseInt(sessionId));

                if (allReady){
                    session.setExpired(true);
                    sessionRepository.save(session);
                    sessionRepository.flush();

                    // Send load event
                    template.convertAndSend("/topic/session/"+sessionId,new SessionServerMessage(SessionServerMessage.MESSAGE_TRIGGER_LOAD,"All Users Ready!"));

                    // Process the session repository for recommendations
                    RecommendationResult results = recommendationService.doSessionRecommend(Integer.parseInt(sessionId));

                    // Load metadata for all the boops
                    for (Recommendation result : results.getRecommendations()){
                        System.out.println("Checking metadata for "+result.getTitle());
                        metadataService.getMetadataForTitle(result.getTitle().getTitleId());
                    }

                    template.convertAndSend("/topic/session/"+sessionId,new SessionServerMessage(SessionServerMessage.MESSAGE_TRIGGER_RESULTS,"Results Processed!"));
                } else {
                    return new SessionServerMessage(SessionServerMessage.MESSAGE_TRIGGER_READY,""+userObject.getUserId());
                }

            }
            case SessionMessage.MESSAGE_READY_CANCEL: {
                UserSession userSession = userSessionRepository.getOne(userObject.getUserId()+"_"+sessionId);
                userSession.setIsReady(0);
                userSessionRepository.save(userSession);
                return new SessionServerMessage(SessionServerMessage.MESSAGE_TRIGGER_UNREADY,""+userObject.getUserId());
            }
            default: {
                //Unknown message.
                return new SessionServerMessage(SessionServerMessage.MESSAGE_ERROR,"Unknown message sent by client.");
            }
        }


    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////