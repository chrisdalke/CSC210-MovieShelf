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
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

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
public class Title implements Comparable<Title>, Serializable {

    @Id
    @NotNull
    @Column(name = "tconst", unique = true, updatable = false)
    private String titleId;

    @NotNull
    @Column(name = "primaryTitle", unique = false, updatable = false)
    @Field
    private String titleName;

    @NotNull
    @Column(name = "startYear", unique = false, updatable = false)
    private Integer year;

    @NotNull
    @Column(name = "runtimeMinutes", unique = false, updatable = false)
    private Integer runtimeMinutes;

    @IndexedEmbedded
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "known_for",
            joinColumns = @JoinColumn(name = "tconst", referencedColumnName = "tconst"),
            inverseJoinColumns = @JoinColumn(name = "nconst", referencedColumnName = "nconst"))
    private Set<Person> associatedPeople = new HashSet<>();

    @Override
    public int compareTo(Title other) {
        return getTitleId().compareTo(other.getTitleId());
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////
