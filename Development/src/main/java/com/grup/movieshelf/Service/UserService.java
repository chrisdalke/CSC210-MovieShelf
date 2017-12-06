///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Service;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.Controller.API.Entity.ResponseStatus;
import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Users.Friendship;
import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Repository.FriendshipRepository;
import com.grup.movieshelf.JPA.Repository.SessionRepository;
import com.grup.movieshelf.JPA.Repository.UserOptionsRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import com.grup.movieshelf.Utility.RandomStringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/////////////////////////////////////////////////////////////
// UserService
// Holds all functionality related to user accounts.
/////////////////////////////////////////////////////////////

@Service
public class UserService implements UserDetailsService {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private UserOptionsRepository userOptionsRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ShelfService shelfService;

    @Lazy
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //------------------------------------------------
    // Account Creation / Deletion
    //------------------------------------------------

    public void saveNewUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteUser() {
        User user = getLoggedInUser();

        // remove all friendships before deleting user
        for(User friend : getFriends()) {
            removeFriend(friend.getUsername());
        }

        // remove all movies from shelf
        for(Title title : shelfService.getShelfForUser()) {
            shelfService.removeTitleFromShelf(title.getTitleId());
        }

        // TODO: delete options, roles, sessions

        userRepository.delete(user);
    }

    //------------------------------------------------
    // Guest Account Creation / Deletion
    //------------------------------------------------
    // Note: A guest account is just a normal user account
    // with a specific flag. When the user enters a
    // session code, this system generates a new user
    // and sends it back so the user can autologin.

    public User createNewGuestUser(String sessionCode){
        // If the session code is invalid, return a null user object.
        if (!sessionRepository.existsBySessionCode(sessionCode)){
            return null;
        }

        String username = RandomStringUtility.generateCredentialString(16);
        while (userRepository.existsByUsername(username)){
            username = RandomStringUtility.generateCredentialString(16);
        }
        String password = RandomStringUtility.generateCredentialString(16);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        saveNewUser(newUser);

        return newUser;
    }


    //------------------------------------------------
    // Friends List
    //------------------------------------------------

    // helper method that returns a boolean corresponding to whether the users are friends or not
    public boolean isFriend(Integer userId1, Integer userId2) {

        String friendshipId = String.format("%d_%d", userId1, userId2);
        String friendshipId2 = String.format("%d_%d", userId2, userId1);

        // check if user 1 has added user 2, and vice versa
        return !(friendshipRepository.getByFriendshipId(friendshipId) == null
                || friendshipRepository.getByFriendshipId(friendshipId2) == null);
    }

    public boolean addFriend(String username){
        Integer userId = getLoggedInUser().getUserId();
        User requestedFriend = userRepository.findByUsername(username);

        if (requestedFriend != null) {
            String friendshipId = String.format("%d_%d", userId, requestedFriend.getUserId());
            Friendship friendship = friendshipRepository.getByFriendshipId(friendshipId);

            //if the requester -> requestee friendship object does not already exist
            if(friendship == null) {
                friendshipRepository.save(new Friendship(userId, requestedFriend.getUserId()));
                return true;
            }
        }

        return false;
    }

    public void removeFriend(String username) {
        Integer userId = getLoggedInUser().getUserId();
        User friend = userRepository.findByUsername(username);

        if(friend != null) {
            String friendshipId1 = String.format("%d_%d", userId, friend.getUserId());
            String friendshipId2 = String.format("%d_%d", friend.getUserId(), userId);

            Friendship friendship1 = friendshipRepository.getByFriendshipId(friendshipId1);
            Friendship friendship2 = friendshipRepository.getByFriendshipId(friendshipId2);

            if(friendship1 != null) {
                friendshipRepository.delete(friendship1);
            }

            if(friendship2 != null) {
                friendshipRepository.delete(friendship2);
            }
        }
    }

    public List<User> getFriends(){
        Integer userId = getLoggedInUser().getUserId();
        return getFriends(userId);
    }

    public List<User> getFriends(Integer userId) {

        List<User> friends = new ArrayList<>();

        // get reciprocated friend requests
        List<Friendship> requestedFriends = friendshipRepository.getAllByUserId1(userId);
        for(Friendship friendship : requestedFriends) {
            if(isFriend(userId, friendship.getUserId2())) {
                friends.add(userRepository.findByUserId(friendship.getUserId2()));
            }
        }

        return friends;
    }

    public List<User> getIncomingFriendRequestUsers() {
        Integer userId = getLoggedInUser().getUserId();

        List<User> requestingUsers = new ArrayList<>();

        // get users who have requested you, but you have not yet responded
        List<Friendship> requestedFriends = friendshipRepository.getAllByUserId2(userId);
        for(Friendship friendship : requestedFriends) {
            if(!isFriend(userId, friendship.getUserId1())) {
                requestingUsers.add(userRepository.findByUserId(friendship.getUserId1()));
            }
        }

        return requestingUsers;
    }

    public List<User> getOutgoingFriendRequestUsers() {
        Integer userId = getLoggedInUser().getUserId();

        List<User> requestedUsers = new ArrayList<>();

        // get users who you have requested, but they have not yet responded
        List<Friendship> requestedFriends = friendshipRepository.getAllByUserId1(userId);
        for(Friendship friendship : requestedFriends) {
            if(!isFriend(userId, friendship.getUserId2())) {
                requestedUsers.add(userRepository.findByUserId(friendship.getUserId2()));
            }
        }

        return requestedUsers;
    }

    //------------------------------------------------
    // User Options
    // Functionality to get/set a user's preferences.
    //------------------------------------------------

    /*
    Gets the options for the current user.
     */
    public UserOptions getUserOptions(){
        User user = getLoggedInUser();
        return getUserOptions(user);
    }

    public UserOptions getUserOptions(User user){
        if (user == null){
            return null;
        }

        UserOptions userOptions = userOptionsRepository.findByUserId(user.getUserId());

        if(userOptions == null) {
            userOptions = new UserOptions(user);
        }

        return userOptions;
    }

    public void saveUserOptions(UserOptions userOptions) {
        userOptionsRepository.save(userOptions);
    }

    //------------------------------------------------
    // Login & Sessions Management
    //------------------------------------------------

    // Loads a user by username. Used for user login.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByUsername(username);

        if(foundUser == null) {
            throw new UsernameNotFoundException(username + " was not found.");
        }
        return foundUser;
    }

    // Returns the object for the logged-in user.
    public User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username);
    }

    // Takes a username/password and generates a session token from it.
    // Used to autologin after creating a new account.
    public void autologin(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////
