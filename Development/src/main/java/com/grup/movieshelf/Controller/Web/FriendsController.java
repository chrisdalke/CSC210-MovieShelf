///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.Web;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.Service.SessionService;
import com.grup.movieshelf.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////////////////////
// Friends Controller
// Manages pages that display the friends list functionality.
/////////////////////////////////////////////////////////////

@Controller
public class FriendsController {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    UserService userService;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    @RequestMapping("/friends")
    public String friendsList(){
        return "friends";
    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////