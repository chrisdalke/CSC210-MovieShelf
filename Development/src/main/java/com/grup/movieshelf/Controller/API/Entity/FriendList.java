///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.API.Entity;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Users.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/////////////////////////////////////////////////////////////
// FriendList
// Lightweight object used to return a user's friend list.
/////////////////////////////////////////////////////////////

@Data
@NoArgsConstructor
public class FriendList {

    //------------------------------------------------
    // Instance Variables
    //------------------------------------------------

    private ArrayList<User> friends = new ArrayList<>();

    //------------------------------------------------
    // Methods
    //------------------------------------------------

    // Add a title to this friend list
    public void addFriend(User user){
        friends.add(user);
    }

    // Remove a title from this friend list
    public void removeRecommendation(User user){
        friends.remove(user);
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////