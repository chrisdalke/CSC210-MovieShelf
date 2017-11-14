///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.Web;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Users.Role;
import com.grup.movieshelf.JPA.Entity.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.grup.movieshelf.Service.*;

/////////////////////////////////////////////////////////////
// Session Controller
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

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    // Page showing history of all the sessions a user has been a part of.
    @RequestMapping("/sessions/history")
    public String sessionHistory() {
        return "sessionHistory";
    }

    // Page saying that a session has expired
    // If this is a full user, just tell them the session has expired
    // If this is a guest user, show an account upgrade page.
    @RequestMapping("/sessions/expired")
    public String sessionExpired() {

        User userObject = userService.getLoggedInUser();

        if (userObject.getRoles().contains(new Role("GUEST"))){
            // User is a guest, display a page asking them if they would like to upgrade.
            return "accountUpgrade";
        } else {
            // User is a full user, tell them this session is expired
            return "sessionExpired";
        }

    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////