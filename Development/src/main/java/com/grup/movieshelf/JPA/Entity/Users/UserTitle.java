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
import javax.persistence.*;
import java.io.Serializable;

/////////////////////////////////////////////////////////////
// UserTitles model object
// Maps a user account to a title that they've added to their shelf.
/////////////////////////////////////////////////////////////

@Entity
@Table(name = "UserTitles")
@Data
@NoArgsConstructor
public class UserTitle implements Comparable<UserTitle>, Serializable {

    public UserTitle(Integer userId, String titleId) {
        this.userId = userId;
        this.titleId = titleId;
        this.userTitleId = String.valueOf(userId) + titleId;
    }

    @Id
    @Column(name = "userTitleId", unique = true, updatable = false)
    private String userTitleId;

    @Column(name = "userId", unique = false, updatable = true)
    private Integer userId;

    @Column(name = "tconst", unique = false, updatable = true)
    private String titleId;

    @Override
    public int compareTo(UserTitle o) {
        return getUserTitleId().compareTo(o.getUserTitleId());
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////