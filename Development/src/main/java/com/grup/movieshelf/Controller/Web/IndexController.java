package com.grup.movieshelf.Controller.Web;

import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Utility.HibernateUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;

    @RequestMapping("/")
    public String testWeb(HttpServletRequest request, Model model){

        if (request.getUserPrincipal() == null) {
            model.addAttribute("title", "Not Logged In");
        } else {
            model.addAttribute("title", "Logged In");
        }

        UserOptions userOptions = hibernateUserDetailsService.getUserOptionsForUser();
        model.addAttribute("userProfile", userOptions);

        return "index";
    }
}