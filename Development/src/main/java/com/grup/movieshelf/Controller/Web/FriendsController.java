///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.Web;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Repository.FriendshipRepository;
import com.grup.movieshelf.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/friends")
    public String friendsPage(Model model){
        model.addAttribute("incoming_requests", userService.getIncomingFriendRequestUsers());
        model.addAttribute("outgoing_requests", userService.getOutgoingFriendRequestUsers());
        model.addAttribute("friends", userService.getFriends());
        return "friends";
    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////