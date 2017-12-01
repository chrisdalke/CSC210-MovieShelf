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
import com.grup.movieshelf.JPA.Entity.Movies.Metadata;
import com.grup.movieshelf.JPA.Repository.MetadataRepository;

/////////////////////////////////////////////////////////////
// Metadata Service
// Functionality for Movie Meta Data
/////////////////////////////////////////////////////////////

@Service
public class MetadataService {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private MetadataRepository metadataRepository;

    //------------------------------------------------
    // Add Metadata and Get Movie Image
    //------------------------------------------------

    public String getImage (String titleId){
        return metadataRepository.getByTitleId(titleId).getImage();
    }

    public boolean contains(String titleId){
        return metadataRepository.existsByTitleId(titleId);
    }

    public void addMetadata(String titleId, String img_url, String description) {
        if (metadataRepository.existsByTitleId(titleId)) {
            Metadata metadata = new Metadata (titleId, img_url, description);
            metadataRepository.save(metadata);
        }
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////