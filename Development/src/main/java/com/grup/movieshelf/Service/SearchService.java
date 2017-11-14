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
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/////////////////////////////////////////////////////////////
// Search Service
// Performs searches.
/////////////////////////////////////////////////////////////

@Service
public class SearchService {

    @Autowired
    private TitleRepository titleRepository;

    public List<Title> doSimpleSearch(String searchString){

        System.out.println("User searched \"" + searchString + "\".");

        // TODO: make search bar support not just movie titles (actors, directors, etc.)
        List<Title> titles = titleRepository.getAllByTitleNameContaining(searchString);

        System.out.println("Returned results:");
        for(Title title : titles) {
            System.out.println(title.getTitleName() + " (" + title.getYear() + ")");
        }

        return titles;
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////