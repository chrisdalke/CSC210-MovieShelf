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
import org.springframework.ui.Model;

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
    public ResponseStatus addFriend (@RequestParam("userName") String friendUserName) {
        userService.addFriend(friendUserName);
        return new ResponseStatus();
    }

    // Confirm a friendship with a user
    @PostMapping("/api/friends/confirm")
    public ResponseStatus confirmFriend (@RequestParam("userName") String friendUserName) {
        // isFriend just checks that both have added each other as friends
        // both considered friends when both have added the other; so just add the other
        userService.addFriend(friendUserName);
        return new ResponseStatus();
    }

    // Delete a friendship with a user
    @DeleteMapping("/api/friends/remove/{username}")
    public ResponseStatus removeFriend (Model model, @PathVariable("username") String username) {
        userService.removeFriend(username);
        return new ResponseStatus();
    }

    // Get the list of users you are friends with
    @GetMapping("/api/friends/")
    public List<User> getFriends(){
        return userService.getFriends();
    }

    // Delete a friendship request from a user
    @DeleteMapping("/api/friends/delete/{username}")
    public ResponseStatus deleteFriendRequest (Model model, @PathVariable("username") String username) {
        userService.deleteFriendRequest(username);
        return new ResponseStatus();
    }

    // Cancel a friendship request to a user
    @DeleteMapping("/api/friends/cancel/{username}")
    public ResponseStatus cancelFriendRequest (Model model, @PathVariable("username") String username) {
        userService.deleteFriendRequest(username); //deleting the same friendships, so same thing as deleteFriend
        return new ResponseStatus();
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////