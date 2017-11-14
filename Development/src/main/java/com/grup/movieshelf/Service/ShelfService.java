///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Service;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Users.UserTitle;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import com.grup.movieshelf.JPA.Repository.UserTitlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////////////////////
// Shelf Service
// Functionality for interaction with shelves.
/////////////////////////////////////////////////////////////

@Service
public class ShelfService {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTitlesRepository userTitlesRepository;

    //------------------------------------------------
    // Add & Remove Shelf Titles
    //------------------------------------------------

    public void addTitleToShelf(String titleId) {
        Integer userId = userService.getLoggedInUser().getUserId();
        UserTitle userTitle = new UserTitle(userId, titleId);
        userTitlesRepository.save(userTitle);
    }

    public void removeTitleFromShelf(String titleId) {
        Integer userId = userService.getLoggedInUser().getUserId();
        UserTitle userTitle = userTitlesRepository.getByUserIdAndTitleId(userId, titleId);
        userTitlesRepository.delete(userTitle);
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////