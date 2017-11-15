///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.API;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.Controller.API.Entity.FriendList;
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

    // Add a friendship with a user
    @PostMapping("/api/friends")
    public ResponseStatus addFriend (@RequestParam("userName") String friendUserName) {

        userService.addFriend(friendUserName);

        return new ResponseStatus();
    }

    // Delete a friendship with a user
    @DeleteMapping("/api/friends")
    public ResponseStatus removeFriend (@RequestParam("userName") String friendUserName) {

        userService.removeFriend(friendUserName);

        return new ResponseStatus();
    }

    // Get the list of users you are friends with
    @GetMapping("/api/friends")
    public FriendList getFriends(){
        return new FriendList();
    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////