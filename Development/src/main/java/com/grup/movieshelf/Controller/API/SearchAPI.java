///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.API;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.Controller.API.Entity.SearchResult;
import com.grup.movieshelf.Controller.API.Entity.TitleWithShelfStatus;
import com.grup.movieshelf.Controller.API.Entity.UserWithAddedStatus;
import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.grup.movieshelf.Service.*;

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

    @Autowired
    private ShelfService shelfService;

    @Autowired
    private UserService userService;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    @GetMapping("/api/search")
    public SearchResult search (@RequestParam("searchString") String searchString) {
        SearchResult result = new SearchResult();

        for(Title title : searchService.searchTitles(searchString)) {
            result.getTitles().add(new TitleWithShelfStatus(title, shelfService.shelfContains(title.getTitleId())));
        }

        for(User user : searchService.searchUsers(searchString)) {
            result.getUsers().add(new UserWithAddedStatus(user.getUserId(), user.getUsername(), userService.hasAdded(user.getUserId())));
        }

        return result;
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////