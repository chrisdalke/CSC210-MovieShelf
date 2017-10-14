package com.grup.movieshelf.Controller.API;

import com.grup.movieshelf.JPA.Entity.*;
import com.grup.movieshelf.JPA.Repository.RoleRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.grup.movieshelf.JPA.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class UserRegistrationController {
        //role - needed for certain authorities; how to implement? do we even need to for account creation
            //should make officer accounts manually? accounts made user by defaul and then given permissions by admin?

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;
    
    @GetMapping("/user/register")
    public String getUser (Model model) {
        model.addAttribute("user", new User()); //builds user from form input provided by userRegister.html
        return "userRegister";
    }
    
    @PostMapping("/user/register")
    public String registerUser (Model model, @ModelAttribute User user) { //get the user object from before
        if (userRepository.findByUsername(user.getUsername()) == null)
        {
            hibernateUserDetailsService.saveNewUser(user);
            model.addAttribute("message", "Account made successfully.");
        }
        else
        {
            model.addAttribute("message", "Account creation failed. Username already exists.");
        }
        
        return "userRegister"; //return to a different page? maybe the home page?
    }
}
