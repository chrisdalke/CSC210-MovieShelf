package com.grup.movieshelf.Controller.Web.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserOptionsController {

    @RequestMapping("/user/options")
    public String userOptionsEndpoint(Model model){
        return "userOptions";
    }
}
