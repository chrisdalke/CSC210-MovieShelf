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
import lombok.Data;
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
    @Data
    private class FriendIdObject {
        public String titleId;
    }

    @PostMapping("/api/shelf/{titleId}")
    public ResponseStatus addTitle (Model model, @PathVariable("titleId") String titleId) {
        shelfService.addTitleToShelf(titleId);
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

    //need the following functions: one to get movie image and description from imdb and place in database
        // use Jaunt to look up movie page url based on input title id
        // get html of page
        // parse class="poster" to get image url; save this in database
            // contained in a div with class "poster"
            // contains a <a> tag with the url as an href - GET THIS
            // actual poster url contained further in!
                // within the <a> tag is an <img> tag
                // parse the <img> tag with class="pswp__img"
                    //acutal image file contained in the tag under "src=" property
        // parse class="summary_text" to get description; save in databasae
            //contained in a div of class "summary_text"
    // one to check if in database, otherwise call former; return image and descrip?
        // first, check database using title id to see if the image exists; if not, call the method above
            // THEN return the image url

    // how to get element text from html - put this in a .js file later
    /*
    try{
      UserAgent userAgent = new UserAgent();
      String url = "http://imdb.com/title/" + titleId;
      userAgent.visit(url);

      Element img_div = userAgent.doc.findFirst("<div class=\"poster\">")
      Element img_tag = img_div.findFirst("<img>");
      String img_url = img_tag.getAt("src");
      // how to add to database?

      Element meta_div = userAgent.doc.findFirst("<div class=\"summary_text\">");
      String description = meta_div.getText();
    }
    catch(JauntException e){                         
      System.err.println(e);         
    }
    */
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////