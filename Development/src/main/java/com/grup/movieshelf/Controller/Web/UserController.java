package com.grup.movieshelf.Controller.Web;

import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Repository.RoleRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import com.grup.movieshelf.JPA.Utility.HibernateUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;

    @RequestMapping("/login")
    public String userLoginEndpoint(Model model){
        return "userLogin";
    }

    /*
    Displays the user profile.
     */
    @GetMapping("/user/profile")
    public String displayUserProfile(Model model){
        UserOptions userOptions = hibernateUserDetailsService.getUserOptionsForUser();
        model.addAttribute("userProfile", userOptions);
        return "userProfile";
    }

    /*
    Allows the user to edit their profile.
     */
    @GetMapping("/user/options")
    public String displayUserOptions(Model model) {
        UserOptions userOptions = hibernateUserDetailsService.getUserOptionsForUser();
        model.addAttribute("userOptions", userOptions);
        return "userOptions";
    }


    @PostMapping("/user/options")
    public String saveUserOptions(Model model, @ModelAttribute UserOptions userOptionsFormData) {

        UserOptions userOptions = hibernateUserDetailsService.getUserOptionsForUser();

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

        hibernateUserDetailsService.saveUserOptions(userOptions);
        model.addAttribute("message", "Successfully saved user options.");

        //return "userOptions";
        return "redirect:/user/profile";
    }

    @GetMapping("/user/register")
    public String getUser (Model model) {
        model.addAttribute("user", new User()); //builds user from form input provided by userRegister.html
        model.addAttribute("showForm",true);
        return "userRegister";
    }

    @PostMapping("/user/register")
    public String registerUser (Model model, @ModelAttribute User user) { //get the user object from before

        if (userRepository.findByUsername(user.getUsername()) == null)
        {
            hibernateUserDetailsService.saveNewUser(user);
            model.addAttribute("message", "Account made successfully.");
            model.addAttribute("showForm",false);
        }
        else
        {
            model.addAttribute("message", "Account creation failed. Username already exists.");
            model.addAttribute("showForm",true);
        }

        return "userRegister"; //return to a different page? maybe the home page?
    }
}
