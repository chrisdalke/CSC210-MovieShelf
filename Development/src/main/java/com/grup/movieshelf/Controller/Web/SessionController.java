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
import com.grup.movieshelf.Controller.API.Entity.RecommendationList;
import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Users.Role;
import com.grup.movieshelf.JPA.Entity.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.grup.movieshelf.Service.*;

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

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    // Page showing history of all the sessions a user has been a part of.
    @RequestMapping("/sessions/history")
    public String sessionHistory() {
        return "sessionHistory";
    }

    // Page to display a session
    // If the session is live, displays template for a current session
    // Otherwise, displays template for an archived session
    @RequestMapping("/sessions/{sessionCode}")
    public String sessionPage(@PathVariable("sessionCode") String sessionCode, Model model) {
        // Check that session exists
        Session session = sessionService.getSession(sessionCode);
        if (session != null){
            return "sessionInvalidCode";
        }

        User userObject = userService.getLoggedInUser();

        // If the session has already expired, just show the session results page
        if (session.isExpired()){
            model.addAttribute("session",session);
            return "sessionArchived";
        }

        // If the user is not already a part of the session, have them join this session
        // Users can autojoin sessions simply by navigating to the URL of the session
        if (!sessionService.userInSession(userObject.getUserId(),session.getSessionId())){
            sessionService.addUserToSession(userObject.getUserId(),session.getSessionId());
        }

        // Display the session live page (which has some JS code on clientside to join sockets system.)
        model.addAttribute("session",session);
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