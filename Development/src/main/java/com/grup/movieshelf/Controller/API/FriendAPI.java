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
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.grup.movieshelf.Service.*;
import org.springframework.ui.Model;

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

    @Data
    private class FriendIdObject {
        public String friendUserName;
    }

    // Add a friendship with a user
    @PostMapping("/api/friends")
    public ResponseStatus addFriend (Model model, @RequestBody FriendIdObject friend) {
        userService.addFriend(friend.friendUserName);
        return new ResponseStatus();
    }

    // Delete a friendship with a user
    @DeleteMapping("/api/friends/{userName}")
    public ResponseStatus removeFriend (Model model, @PathVariable("userName") String friendUserName) {
        userService.removeFriend(friendUserName);
        return new ResponseStatus();
    }

    // Get the list of users you are friends with
    @GetMapping("/api/friends/")
    public FriendList getFriends(){
        return new FriendList();
    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////