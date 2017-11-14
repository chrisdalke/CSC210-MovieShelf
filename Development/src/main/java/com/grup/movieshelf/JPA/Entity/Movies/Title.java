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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/////////////////////////////////////////////////////////////
// Title Model Object
// Stores data about a movie title.
/////////////////////////////////////////////////////////////

@Entity
@Indexed
@Table(name = "Titles")
@Data
@NoArgsConstructor
public class Title implements Comparable<Title>, Serializable {

    @Id
    @Column(name = "tconst", unique = true, updatable = false)
    private String titleId;

    @NotNull
    @Column(name = "primaryTitle", unique = false, updatable = false)
    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
    private String titleName;

    @NotNull
    @Column(name = "startYear", unique = false, updatable = false)
    private Integer year;

    @NotNull
    @Column(name = "runtimeMinutes", unique = false, updatable = false)
    private Integer runtimeMinutes;

    @Override
    public int compareTo(Title other) {
        return getTitleId().compareTo(other.getTitleId());
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////
