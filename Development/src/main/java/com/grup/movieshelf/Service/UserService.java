///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Service;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Repository.SessionRepository;
import com.grup.movieshelf.JPA.Repository.User.UserOptionsRepository;
import com.grup.movieshelf.JPA.Repository.User.UserRepository;
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
import java.util.concurrent.ThreadLocalRandom;

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
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

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

    public void addFriend(User friendUser){

    }

    public void removeFriend(User friendUser){

    }

    public ArrayList<User> getFriends(){
        return null;
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
    // Login & Session Management
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
