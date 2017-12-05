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
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/////////////////////////////////////////////////////////////
// Person Model Object
// Stores data about a movie actor, director, producer, etc.
/////////////////////////////////////////////////////////////

@Entity
@Table(name = "people")
@Data
@NoArgsConstructor
public class Person implements Comparable<Person>, Serializable {

    @Id
    @NotNull
    @Column(name = "nconst", unique = true, updatable = false)
    private String personId;

    @NotNull
    @Column(name = "primary_name", unique = false, updatable = true)
    @Field
    private String name;

    @Override
    public int compareTo(Person o) {
        return getPersonId().compareTo(o.getPersonId());
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////
