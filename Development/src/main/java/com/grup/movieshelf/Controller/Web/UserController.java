package com.grup.movieshelf.Controller.Web;

import com.grup.movieshelf.JPA.Entity.User;
import com.grup.movieshelf.JPA.Entity.UserOptions;
import com.grup.movieshelf.JPA.Repository.UserOptionsRepository;
import com.grup.movieshelf.JPA.Utility.HibernateSecurityService;
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
    HibernateSecurityService hibernateSecurityService;

    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;

    @Autowired
    UserOptionsRepository userOptionsRepository;

    @RequestMapping("/login")
    public String userLoginEndpoint(Model model){
        return "userLogin";
    }

    @GetMapping("/user/options")
    public String displayUserOptions(Model model) {
        User user = hibernateSecurityService.getLoggedInUser();
        UserOptions userOptions = userOptionsRepository.findByUserId(user.getUserId());
        if (userOptions == null){
            userOptions = new UserOptions(user);
        }
        model.addAttribute("userOptions", userOptions);
        return "userOptions";
    }

    @PostMapping("/user/options")
    public String saveUserOptions(Model model, @ModelAttribute UserOptions userOptionsFormData) {

        User user = hibernateSecurityService.getLoggedInUser();
        UserOptions userOptions = userOptionsRepository.findByUserId(user.getUserId());

        if(userOptions == null) {
            userOptions = new UserOptions(user);
        }

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

        return "userOptions";
    }
}
