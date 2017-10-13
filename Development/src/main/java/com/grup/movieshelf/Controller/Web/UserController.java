package com.grup.movieshelf.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/login")
    public String userLoginEndpoint(Model model){
        return "userLogin";
    }

    @RequestMapping("/user/options")
    public String userOptionsEndpoint(Model model){
        return "userOptions";
    }

    @RequestMapping("/user/register")
    public String userRegisterEndpoint(Model model){
        return "userRegister";
    }
}
