package com.grup.movieshelf.Controller.API;

import com.grup.movieshelf.JPA.Entity.*;
import com.grup.movieshelf.JPA.Repository.RoleRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;

public class UserRegistrationController {
    //should handle user login
    //link to account creation page
    //when a user tries to make an account, they input a username, password, and some other info
    //info is used to make a User object, which is stored on the SQL database
        //need a username, a password, and an id
            //can also have miscellaneous information for flavor?
            //include a password encryptor?
            //randomly generate a user id? hash function? id = hash of username and/or password?
        //role - needed for certain authorities; how to implement? do we even need to for account creation
            //should make officer accounts manually? accounts made user by defaul and then given permissions by admin?
    // how to push User objects to UserRepository?

    @Autowired
    private UserRepository users;
    @Autowired
    private RoleRepository roles;

    public void register (User user) {
        //repository = user table?
        //how to push to it?
        //take user as input, or build in method?
    }
}
