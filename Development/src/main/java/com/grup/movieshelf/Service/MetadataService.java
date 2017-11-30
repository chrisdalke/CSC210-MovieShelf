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
    // Add Metadata and Get Movie Image
    //------------------------------------------------

    public String getImage (String titleId){
        // returns image url from database
    }

    public boolean contains(String titleId){
        // check if database contains titleId
    }

    public void addMetadata(String img_url, String description) {
        // add img_url to database
        // add description to database
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////