///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Service;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Users.UserTitle;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import com.grup.movieshelf.JPA.Repository.UserTitlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        if (titleRepository.existsByTitleId(titleId)) {
            Integer userId = userService.getLoggedInUser().getUserId();
            UserTitle userTitle = new UserTitle(userId, titleId);
            userTitlesRepository.save(userTitle);
        }
    }

    public void removeTitleFromShelf(String titleId) {
        Integer userId = userService.getLoggedInUser().getUserId();
        UserTitle userTitle = userTitlesRepository.getByUserIdAndTitleId(userId, titleId);
        userTitlesRepository.delete(userTitle);
    }

    //------------------------------------------------
    // Get Shelf Titles
    //------------------------------------------------

    public List<Title> getShelfForUser(){
        Integer userId = userService.getLoggedInUser().getUserId();
        List<UserTitle> titles = userTitlesRepository.getAllByUserId(userId);

        ArrayList<Title> titlesToReturn = new ArrayList<>();
        for (UserTitle titleMapping : titles){
            titlesToReturn.add(titleRepository.getByTitleId(titleMapping.getTitleId()));
        }

        return titlesToReturn;
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////