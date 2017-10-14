package com.grup.movieshelf.Controller.API;

import com.grup.movieshelf.JPA.Entity.*;
import com.grup.movieshelf.JPA.Repository.RoleRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.grup.movieshelf.JPA.Utility;
@Controller
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;
    
    @GetMapping("/userRegister")
    public String getUser (Model model) {
        model.addAttribute("user", new User()); //builds user from form input provided by userRegister.html
        return "userRegister";
    }
    
    @PostMapping("/userRegister")
    public String registerUser (Model model, @ModelAttribute User user) { //get the user object from before
        if (userRepository.findByUsername(user.username) != NULL)
        {
            hibernateUserDetailsService.saveNewUser(user);
            model.addAttribute("Account made successfully.");
            //print out success message - not SOPL, but to .html page?
        }
        else
        {
            model.addAttribute("Account creation failed. Invalid username or password.");
        }
        
        return "userRegister"; //return to a different page? maybe the home page?
    }
}
