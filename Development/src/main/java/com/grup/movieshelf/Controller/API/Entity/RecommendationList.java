///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.API.Entity;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

/////////////////////////////////////////////////////////////
// RecommendationList
// Lightweight object used to return recommendations in
// a request.
/////////////////////////////////////////////////////////////

@Data
@NoArgsConstructor
public class RecommendationList {

    //------------------------------------------------
    // Instance Variables
    //------------------------------------------------

    private ArrayList<Title> recommendations = new ArrayList<>();

    //------------------------------------------------
    // Methods
    //------------------------------------------------

    // Add a title to this recommendation list
    public void addRecommendation(Title title){
        recommendations.add(title);
    }

    // Remove a title from this recommendation list
    public void removeRecommendation(Title title){
        recommendations.remove(title);
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////