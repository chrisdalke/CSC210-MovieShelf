///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.API;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.Controller.API.Entity.ResponseStatus;
import com.grup.movieshelf.JPA.Entity.Users.Friendship;
import com.grup.movieshelf.JPA.Repository.FriendshipRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.grup.movieshelf.Service.*;

/////////////////////////////////////////////////////////////
// Friend API
// Handles REST endpoints for the Friendship system.
/////////////////////////////////////////////////////////////

@RestController
public class FriendAPI {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserService userService;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    @PostMapping("/api/friends/add")
    public ResponseStatus addFriend (@RequestParam("userName") String friendUserName) {

        userService.addFriend(friendUserName);

        return new ResponseStatus();
    }

    @RequestMapping("/api/friends/remove")
    public ResponseStatus removeFriend (@RequestParam("friendshipId") String friendshipId) {

        userService.removeFriend(friendshipId);

        return new ResponseStatus();
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////