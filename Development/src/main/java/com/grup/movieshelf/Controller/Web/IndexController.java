package com.grup.movieshelf.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String testWeb(HttpServletRequest request, Model model){

        if (request.getUserPrincipal() == null) {

            model.addAttribute("title", "Not Logged In");
        } else {
            model.addAttribute("title", "Logged In");
        }

        return "index";
    }
}
