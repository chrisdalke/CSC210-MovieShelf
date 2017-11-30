///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Service;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/////////////////////////////////////////////////////////////
// Metadata Service
// Functionality for Movie Meta Data
/////////////////////////////////////////////////////////////

@Service
public class MetadataService {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    //------------------------------------------------
    // Add Metadata and Get Move Image
    //------------------------------------------------

    public String getMovieImage(String titleId) {
        // check if titleId in database
        // if so, return image url
        // if not, call api.js addMetadata
            //  then, return the url
    }

    public void addMetadata(String img_url, String description) {
        // add img_url to database
        // add description to database
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////