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
import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Repository.FriendshipRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.grup.movieshelf.Service.*;

import java.util.List;

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
    public ResponseStatus addFriend(@RequestBody String username) {
        // remove "username=" from request body
        if(userService.addFriend(username.substring(9))) {
            return new ResponseStatus();
        }
        return new ResponseStatus(1, "Adding friend was unsuccessful.");
    }

    // Delete a friendship with a user
    @DeleteMapping("/api/friends/{username}")
    public ResponseStatus removeFriend (@PathVariable("username") String username) {
        userService.removeFriend(username);
        return new ResponseStatus();
    }

    // Get the list of users you are friends with
    @GetMapping("/api/friends")
    public List<User> getFriends(){
        return userService.getFriends();
    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////