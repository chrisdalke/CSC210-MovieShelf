package com.grup.movieshelf.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String testWeb(Model model){

        model.addAttribute("value","Hello World");

        return "index";
    }
}
