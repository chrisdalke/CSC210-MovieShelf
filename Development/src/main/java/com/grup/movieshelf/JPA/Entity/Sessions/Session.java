///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.JPA.Entity.Sessions;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/////////////////////////////////////////////////////////////
// Sessions
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
    private Integer sessionId;

    @NotNull
    @Column(name = "isExpired", unique = false, updatable = true)
    private boolean isExpired;

    @NotNull
    @Column(name = "state")
    private Integer state;

    @NotNull
    @Column(name = "sessionCode", unique = true, updatable = true)
    private String sessionCode;

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////