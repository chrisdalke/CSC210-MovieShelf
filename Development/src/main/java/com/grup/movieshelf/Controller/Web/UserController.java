///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.Web;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////


import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Entity.Users.UserTitle;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import com.grup.movieshelf.JPA.Repository.UserTitlesRepository;
import com.grup.movieshelf.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    private UserService userService;

    @Autowired
    private ShelfService shelfService;

    @Autowired
    private UserTitlesRepository userTitlesRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private MetadataService metaDaniel;

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
        model.addAttribute("incomingRequests", userService.getIncomingFriendRequestUsers());
        model.addAttribute("outgoingRequests", userService.getOutgoingFriendRequestUsers());
        model.addAttribute("userFriends",userService.getFriends());
        return "myProfile";
    }

    // Displays another user's profile
    @RequestMapping("/user/profile/{username}")
    public String displayAnotherUserProfile(@PathVariable("username") String username, Model model){
        User user = userRepository.findByUsername(username);
        if (user != null){
            UserOptions userOptions = userService.getUserOptions(user);
            List<User> userFriends = userService.getFriends(user.getUserId());
            List<Title> userTitles = shelfService.getShelfForUser(user.getUserId());

            ArrayList<ArrayList<Title>> titleRows = new ArrayList<>();
            // Build titles list into static rows to make the page appear to load faster
            List<UserTitle> titles = userTitlesRepository.getAllByUserId(user.getUserId());

            for (int i = 0; i < titles.size(); i +=3){
                ArrayList<Title> singleRow = new ArrayList<>();
                if (i < titles.size()){
                    singleRow.add(titleRepository.getByTitleId(titles.get(i+0).getTitleId()));
                }
                if (i + 1 < titles.size()){
                    singleRow.add(titleRepository.getByTitleId(titles.get(i+1).getTitleId()));
                }
                if (i + 2 < titles.size()){
                    singleRow.add(titleRepository.getByTitleId(titles.get(i+2).getTitleId()));
                }
                titleRows.add(singleRow);
            }
            model.addAttribute("user",user);
            model.addAttribute("userOptions",userOptions);
            model.addAttribute("userFriends",userFriends);
            model.addAttribute("userTitles",userTitles);
            model.addAttribute("incomingRequests", userService.getIncomingFriendRequestUsers());
            model.addAttribute("outgoingRequests", userService.getOutgoingFriendRequestUsers());
            model.addAttribute("userTitleRows",titleRows);
            model.addAttribute("metadataService",metaDaniel);
            return "userProfile";
        } else {
            // User does not exist
            return "redirect:/";
        }
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
            model.addAttribute("message", "Account created successfully.");
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
