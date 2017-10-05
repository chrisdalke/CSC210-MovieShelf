package com.grup.movieshelf.Controller.Web.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserRegistrationController {

    @RequestMapping("/user/register")
    public String registerEndpoint(Model model){
        return "userRegister";
    }
}
