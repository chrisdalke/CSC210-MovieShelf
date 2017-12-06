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
import org.springframework.ui.Model;
import com.grup.movieshelf.Controller.API.Entity.ResponseStatus;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/api/meta/{titleId}")
    public String getMovieImage(@PathVariable("titleId") String titleId) {
        return metadataService.getImage(titleId);
    }

    @PostMapping("/api/meta/{titleId}/{image}/{descrip}")
    public void addMetadata(@PathVariable("titleId") String titleId, @PathVariable("image") String img_url, @PathVariable("descrip") String description){
        metadataService.addMetadata(titleId, img_url, description);
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////