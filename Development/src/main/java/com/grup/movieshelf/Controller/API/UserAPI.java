///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.API;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Repository.UserOptionsRepository;
import com.grup.movieshelf.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/////////////////////////////////////////////////////////////
// User API
// REST API for interacting with users.
/////////////////////////////////////////////////////////////

@RestController
public class UserAPI {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private static UserService userService;

    @Autowired
    private static UserOptionsRepository userOptionsRepository;

    //------------------------------------------------
    // REST methods for User
    //------------------------------------------------

    @PostMapping("/api/user")
    public void createUser(){

    }

    @GetMapping("/api/user")
    public void getUser(){

    }

    @PatchMapping("/api/user")
    public void modifyUser(){

    }

    @DeleteMapping("/api/user")
    public void deleteUser(){

    }

    @GetMapping("/api/user_active")
    public void getActiveUser(){

    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////