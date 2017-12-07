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
import com.grup.movieshelf.JPA.Entity.Movies.Person;
import com.jaunt.*;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
        getMetadataForTitle(titleId);
        Metadata metadata = metadataRepository.getByTitleId(titleId);
        if (metadata != null){
            return metadata.getDescription();
        } else {
            return "No Description Found!";
        }
    }

    public String getImage (String titleId){
        getMetadataForTitle(titleId);
        Metadata metadata = metadataRepository.getByTitleId(titleId);
        if (metadata != null){
            return metadata.getImage();
        } else {
            return "No Image Found!";
        }
    }

    public void getMetadataForTitle(String titleId){
        if (!contains(titleId)) {
            try {
                //Find and add metadata for this title
                UserAgent userAgent = new UserAgent();
                String url = "http://imdb.com/title/" + titleId;
                userAgent.visit(url);
                String img_url = userAgent.doc.findFirst("<div class=\"poster\">").findFirst("<img>").getAt("src");
                String description = userAgent.doc.findFirst("<div class=\"summary_text\">").getText();
                description = description.replace("&quot;", "\"");
                addMetadata(titleId, img_url, description);
            } catch (JauntException e) {
                // Don't put any metadata.
            }
        }

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

    public String getPersonnel(Set<Person> associatedPeople) {
        String personnel = "";
        for (Person p : associatedPeople) {
            personnel += p.getName();
            personnel += ", ";
        }

        return personnel.substring(0, personnel.length()-2);
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////