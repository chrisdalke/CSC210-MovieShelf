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
import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Repository.UserTitlesRepository;
import com.grup.movieshelf.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/////////////////////////////////////////////////////////////
// Shelf API
// REST API functionality for interaction with user's shelf.
/////////////////////////////////////////////////////////////

@RestController
public class ShelfAPI {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private ShelfService shelfService;

    @Autowired
    private UserTitlesRepository userTitlesRepository;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    private class FriendIdObject {
        public String titleId;
    }

    @PostMapping("/api/shelf")
    public ResponseStatus addTitle (Model model, @RequestBody FriendIdObject friendIdObject) {
        shelfService.addTitleToShelf(friendIdObject.titleId);
        return new ResponseStatus();
    }

    @DeleteMapping("/api/shelf/{titleId}")
    public ResponseStatus removeTitle (Model model, @PathVariable("titleId") String titleId) {
        shelfService.removeTitleFromShelf(titleId);
        return new ResponseStatus();
    }

    @GetMapping("/api/shelf")
    public List<Title> getShelf(){
        return shelfService.getShelfForUser();
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////