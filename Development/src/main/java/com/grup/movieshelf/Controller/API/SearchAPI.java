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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.grup.movieshelf.Service.*;
import java.util.List;

/////////////////////////////////////////////////////////////
// Search API
// REST Controller handling searchTitles functionality
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

    @GetMapping("/api/search")
    public List<Title> search (@RequestParam("searchString") String searchString) {
        return searchService.searchTitles(searchString);
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////