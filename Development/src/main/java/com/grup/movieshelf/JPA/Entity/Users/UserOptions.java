///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.JPA.Entity.Users;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/////////////////////////////////////////////////////////////
// UserOptions Model Object
// Stores the set of arbitrary options for a user.
/////////////////////////////////////////////////////////////

@Entity
@Table(name = "UserOptions")
@Data
@NoArgsConstructor
public class UserOptions implements Comparable<UserOptions>, Serializable {

    @Id
    @Column(name = "userId", unique = true, updatable = false)
    private Integer userId;

    @NotNull
    @Column(name = "displayName", unique = true, updatable = true)
    private String displayName;

    @NotNull
    @Column(name = "primaryLanguage", unique = false, updatable = true)
    private String primaryLanguage;

    @NotNull
    @Column(name = "birthdate", unique = false, updatable = true)
    private String birthdate;

    public UserOptions(User user) {
        userId = user.getUserId();
        displayName = user.getUsername();
        primaryLanguage = "English";
        birthdate = "01/01/2000";
    }

    @Override
    public int compareTo(UserOptions other) {
        return getUserId().compareTo(other.getUserId());
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////