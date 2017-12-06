///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.Web;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////


import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Repository.RoleRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import com.grup.movieshelf.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/////////////////////////////////////////////////////////////
// User Controller
// Handles pages related to the user.
/////////////////////////////////////////////////////////////

@Controller
public class UserController {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    // Login Page
    @RequestMapping("/login")
    public String userLoginEndpoint(Model model){
        User userObject = userService.getLoggedInUser();
        if (userObject == null) {
            return "userLogin";
        } else {
            return "redirect:/";
        }
    }

    // Displays the user profile.
    @RequestMapping("/user/profile")
    public String displayUserProfile(Model model){
        UserOptions userOptions = userService.getUserOptions();
        model.addAttribute("userProfile", userOptions);
        return "userProfile";
    }

    // Displays the user options.
    @GetMapping("/user/options")
    public String displayUserOptions(Model model) {
        UserOptions userOptions = userService.getUserOptions();
        model.addAttribute("userOptions", userOptions);
        return "userOptions";
    }

    // Sets the user options based on the form.
    @PostMapping("/user/options")
    public String saveUserOptions(Model model, @ModelAttribute UserOptions userOptionsFormData) {

        UserOptions userOptions = userService.getUserOptions();

        String displayName = userOptionsFormData.getDisplayName();
        String primaryLanguage = userOptionsFormData.getPrimaryLanguage();
        String birthdate = userOptionsFormData.getBirthdate();

        if(!displayName.isEmpty()) {
            userOptions.setDisplayName(displayName);
        }
        if(!primaryLanguage.isEmpty()) {
            userOptions.setPrimaryLanguage(primaryLanguage);
        }
        if(!birthdate.isEmpty()) {
            userOptions.setBirthdate(birthdate);
        }

        userService.saveUserOptions(userOptions);
        model.addAttribute("message", "Successfully saved user options.");

        //return "userOptions";
        return "redirect:/user/profile";
    }

    // Page displaying registration form
    @GetMapping("/user/register")
    public String getUser (Model model) {
        model.addAttribute("user", new User()); //builds user from form input provided by userRegister.html
        model.addAttribute("showForm",true);
        return "userRegister";
    }

    // Handles result of registration form
    @PostMapping("/user/register")
    public String registerUser (Model model, @ModelAttribute User user) { //get the user object from before
        if (userRepository.findByUsername(user.getUsername()) == null) {
            userService.saveNewUser(user);
            model.addAttribute("message", "Account made successfully.");
            model.addAttribute("showForm",false);
        } else {
            model.addAttribute("message", "Account creation failed. Username already exists.");
            model.addAttribute("showForm",true);
        }

        return "userRegister"; //return to a different page? maybe the home page?
    }

    //deletes current user
    @RequestMapping("/user/delete")
    public String deleteUser () {
        userService.deleteUser();
        return "redirect:/logout";
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////
