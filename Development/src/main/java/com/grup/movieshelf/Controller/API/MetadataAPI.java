///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.API;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.grup.movieshelf.Service.*;
import java.util.List;

/////////////////////////////////////////////////////////////
// Search API
// REST Controller handling getImage and addMetadata functionality
/////////////////////////////////////////////////////////////

@RestController
public class MetadataAPI {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private MetadataService metadataService;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    @GetMapping("/api/meta")
    public String getMovieImage(String titleId) {
        if (metadataService.contains(titleId)) { return metadataService.getImage(titleId); }
        else { // if not, call api.js addMetadata
            return metadataService.getImage(titleId);
        }
    }

    @PostMapping("/api/meta/...")
    public void addMetadata(String img_url, String description){
        // calls service - tells it to add the params to databasse
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////