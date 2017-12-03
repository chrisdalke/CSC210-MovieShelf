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
import com.jaunt.*;
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

    public String getDescription (String titleId){
        if (!contains(titleId)) {
            try{
                UserAgent userAgent = new UserAgent();
                String url = "http://imdb.com/title/" + titleId;
                userAgent.visit(url);
                String img_url = userAgent.doc.findFirst("<div class=\"poster\">").findFirst("<img>").getAt("src");
                String description = userAgent.doc.findFirst("<div class=\"summary_text\">").getText();
                addMetadata(titleId, img_url, description);
            }
            catch(JauntException e){
                System.err.println(e);
            }
        }
        return metadataRepository.getByTitleId(titleId).getDescription();
    }

    public String getImage (String titleId){
        if (!contains(titleId)) {
            try{
                UserAgent userAgent = new UserAgent();
                String url = "http://imdb.com/title/" + titleId;
                userAgent.visit(url);
                String img_url = userAgent.doc.findFirst("<div class=\"poster\">").findFirst("<img>").getAt("src");
                String description = userAgent.doc.findFirst("<div class=\"summary_text\">").getText();
                addMetadata(titleId, img_url, description);
            }
            catch(JauntException e){
                System.err.println(e);
            }
        }
        return metadataRepository.getByTitleId(titleId).getImage();
    }

    public boolean contains(String titleId){
        return metadataRepository.existsByTitleId(titleId);
    }

    public void addMetadata(String titleId, String img_url, String description) {
        if (!metadataRepository.existsByTitleId(titleId)) {
            Metadata metadata = new Metadata (titleId, img_url, description);
            metadataRepository.save(metadata);
        }
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////