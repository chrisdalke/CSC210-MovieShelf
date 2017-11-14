///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.API;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.grup.movieshelf.Service.*;
import java.util.List;

/////////////////////////////////////////////////////////////
// Search API
// REST Controller handling search functionality
/////////////////////////////////////////////////////////////

@RestController
public class SearchAPI {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private SearchService searchService;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    @PostMapping("/search")
    public List<Title> search (@RequestParam("searchString") String searchString) {
        return searchService.search(searchString);
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////