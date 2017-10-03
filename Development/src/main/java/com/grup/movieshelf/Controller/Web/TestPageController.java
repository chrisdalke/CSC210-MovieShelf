package com.grup.movieshelf.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestPageController {

    @RequestMapping("/test_web")
    public String testWeb(Model model){
        model.addAttribute("testAttribute","Hello World!");
        return "testWeb";
    }
}
