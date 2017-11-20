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
// Friendship Model Object
// Represents a friendship between two users.
/////////////////////////////////////////////////////////////

@Entity
@Table(name = "Friendship")
@Data
@NoArgsConstructor
public class Friendship implements Comparable<Friendship>, Serializable {

    public Friendship(Integer userId1, Integer userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.friendshipId = String.valueOf(userId1) + "_" + String.valueOf(userId2);
    }

    @Id
    @Column(name = "friendshipId", unique = true, updatable = false)
    private String friendshipId;

    @Column(name = "userId", unique = false, updatable = false)
    private Integer userId1;

    @Column(name = "userId2", unique = false, updatable = false)
    private Integer userId2;

    @Override
    public int compareTo(Friendship o) {
        return getFriendshipId().compareTo(o.getFriendshipId());
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////