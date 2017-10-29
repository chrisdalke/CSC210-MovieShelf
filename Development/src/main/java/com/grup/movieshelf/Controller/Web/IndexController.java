package com.grup.movieshelf.Controller.Web;

import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Utility.HibernateSecurityService;
import com.grup.movieshelf.JPA.Utility.HibernateUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;

    @Autowired
    HibernateSecurityService hibernateSecurityService;

    @GetMapping("/")
    public String testWeb(HttpServletRequest request, Model model){


        if (request.getUserPrincipal() == null) {
            model.addAttribute("title", "Not Logged In");
        } else {
            model.addAttribute("title", "Logged In");
            model.addAttribute("userData", hibernateSecurityService.getLoggedInUser());
        }

        UserOptions userOptions = hibernateUserDetailsService.getUserOptionsForUser();
        model.addAttribute("userProfile", userOptions);

        return "index";
    }


    @PostMapping("/shelf/addMovie")
    public String addTitle (Model model, @RequestParam("titleId") String titleId) {
        System.out.println("Trying to add titleId "+titleId+" to list for current user.");
        hibernateUserDetailsService.addTitleToShelf(titleId);

        return "redirect:/";
    }

    @RequestMapping("/shelf/removeMovie/{titleId}")
    public String removeTitle (Model model, @PathVariable("titleId") String titleId) {
        System.out.println("Trying to remove titleId "+titleId+" to list for current user.");
        hibernateUserDetailsService.removeTitleFromShelf(titleId);

        return "redirect:/";
    }
}