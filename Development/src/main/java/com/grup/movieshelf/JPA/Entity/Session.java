///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.JPA.Entity;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/////////////////////////////////////////////////////////////
// Session
// data object for storing a session.
/////////////////////////////////////////////////////////////

@Entity
@Table(name = "Sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue
    @Column(name = "sessionId", unique = true, updatable = false)
    private String sessionId;

    @NotNull
    @Column(name = "isExpired", unique = false, updatable = true)
    private boolean isExpired;

    @NotNull
    @Column(name = "sessionCode", unique = true, updatable = true)
    private String sessionCode;
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////