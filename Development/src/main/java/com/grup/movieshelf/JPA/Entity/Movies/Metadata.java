///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.JPA.Entity.Movies;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/////////////////////////////////////////////////////////////
// Title Model Object
// Stores data about a movie title.
/////////////////////////////////////////////////////////////

@Entity
@Indexed
@Table(name = "Titles")
@Data
@NoArgsConstructor
public class Metadata implements Comparable<Metadata>, Serializable {

    public Metadata(String titleId, String image, String description) {
        this.titleId = titleId;
        this.image = image;
        this.description = description;
    }

    @Id
    @NotNull
    @Column(name = "tconst", unique = true, updatable = false)
    private String titleId;

    @NotNull
    @Column(name = "image", unique = false, updatable = false)
    private String image;

    @NotNull
    @Column(name = "description", unique = false, updatable = false)
    private String description;

    @Override
    public int compareTo(Metadata other) {
        return getTitleId().compareTo(other.getTitleId());
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////
