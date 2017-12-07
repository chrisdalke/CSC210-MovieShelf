///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.Web;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Sessions.Session;
import com.grup.movieshelf.JPA.Entity.Sessions.UserSession;
import com.grup.movieshelf.Controller.API.Entity.RecommendationList;
import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Sessions.UserSuggestion;
import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Repository.UserSessionRepository;
import com.grup.movieshelf.JPA.Repository.UserSuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.grup.movieshelf.Service.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/////////////////////////////////////////////////////////////
// Sessions Controller
// Displays pages related to the session functionality.
/////////////////////////////////////////////////////////////

@Controller
public class SessionController {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    SessionService sessionService;

    @Autowired
    UserService userService;

    @Autowired
    MetadataService metadataService;

    @Autowired
    UserSessionRepository userSessionRepository;

    @Autowired
    UserSuggestionRepository userSuggestionRepository;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    // Page showing history of all the sessions a user has been a part of.
    @RequestMapping("/sessions/history")
    public String sessionHistory(Model model) {
        User userObject = userService.getLoggedInUser();
        List<Session> currentSessions = new ArrayList<>();
        List<Session> oldSessions = new ArrayList<>();
        for (Session session : sessionService.getSessionsForUser(userObject)){
            if (session.isExpired()){
                oldSessions.add(session);
            } else {
                currentSessions.add(session);
            }
        }
        model.addAttribute("currentSessions",currentSessions);
        model.addAttribute("oldSessions",oldSessions);
        return "sessionHistory";
    }

    @RequestMapping("/public/sessions/join/{sessionCode}")
    public String sessionJoin(@PathVariable("sessionCode") String sessionCode){
        // Handles session join functionality. What this function does depends on whether
        // we are a guest user or a "real" user
        User userObject = userService.getLoggedInUser();
        if (userObject == null){
            // Not logged in; create a guest user and log them in
            // Guest users have a session code appended to their login
            // and will get auto-redirected to the session
            User guestUser = userService.createNewGuestUser(sessionCode);
            userService.autologin(guestUser.getUsername(),guestUser.getUsername());
            // We should be logged in now, and can redirect to the session page.
            userObject = userService.getLoggedInUser();
            // If we are not logged in print an error
            if (userObject == null){
                System.out.println("ERROR: USER SHOULD NOT BE NULL");
            }
        }

        // We are already logged in. redirect to the session page. This will handle session joining from there.
        return "redirect:/sessions/"+sessionCode;
    }

    // Page to display a session
    // If the session is live, displays template for a current session
    // Otherwise, displays template for an archived session
    @RequestMapping("/sessions/{sessionCode}")
    public String sessionPage(@PathVariable("sessionCode") String sessionCode, Model model) {
        // Check that session exists
        Session session = sessionService.getSession(sessionCode);
        if (session == null){
            return "sessionInvalidCode";
        }

        User userObject = userService.getLoggedInUser();

        // If the session has already expired, just show the session results page
        if (session.isExpired()){
            model.addAttribute("msSession",session);
            return "sessionArchived";
        }

        // If the user is not already a part of the session, have them join this session
        // Users can autojoin sessions simply by navigating to the URL of the session
        if (!sessionService.userInSession(userObject.getUserId(),session.getSessionId())){
            sessionService.addUserToSession(userObject.getUserId(),session.getSessionId());
        }

        // Get the user session object
        UserSession userSession = userSessionRepository.getOne(userObject.getUserId()+"_"+session.getSessionId());

        // Display the session live page (which has some JS code on clientside to join sockets system.)
        model.addAttribute("msSession",session);
        List<User> sessionUsers = sessionService.getUsersForSession(session);
        HashMap<Integer,List<UserSuggestion>> userSuggestionMatrix = new HashMap<>();
        HashMap<Integer,String> guestUserAliases = new HashMap<>();
        int numGuests = 0;
        for (User user : sessionUsers){
            List<UserSuggestion> suggestions = userSuggestionRepository.getAllByUserIdAndSessionId(user.getUserId(),session.getSessionId());
            if (suggestions == null){
                suggestions = new ArrayList<UserSuggestion>();
            }
            userSuggestionMatrix.put(user.getUserId(),suggestions);
            if (user.getIsGuest()){
                guestUserAliases.put(user.getUserId(),"Guest #"+(++numGuests));
            }
        }
        List<UserSuggestion> suggestions = userSuggestionMatrix.get(userObject.getUserId());
        sessionUsers.remove(userObject);

        HashMap<String,String> imageUrls = new HashMap<>();

        for (Integer key : userSuggestionMatrix.keySet()){
            for (UserSuggestion suggestion : userSuggestionMatrix.get(key)){
                imageUrls.put(suggestion.getTitleId(),metadataService.getImage(suggestion.getTitleId()));
            }
        }

        model.addAttribute("userSuggestionMatrix",userSuggestionMatrix);
        model.addAttribute("sessionUsers",sessionUsers);
        model.addAttribute("userObject",userObject);
        model.addAttribute("userIsReady",userSession.getIsReady());
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("imageUrls", imageUrls);
        model.addAttribute("numSuggestions", suggestions.size());
        model.addAttribute("guestUserAliases", guestUserAliases);

        return "sessionLive";

    }

    @RequestMapping("/sessions/results")
    public String sesh(Model model){
        RecommendationList recommendationList = sessionService.getSessionRecommendations("shitSession");
        model.addAttribute("first", recommendationList.getRecommendations().remove(0));
        model.addAttribute("second", recommendationList.getRecommendations().remove(0));
        model.addAttribute("third", recommendationList.getRecommendations().remove(0));
        model.addAttribute("sessionResults",recommendationList);
        model.addAttribute("metaDaniel", metadataService);
        return "sessionResults";
    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////