package com.grup.movieshelf.Controller.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SessionController {

    // Page showing history of all the sessions a user has been a part of.
    @RequestMapping("/sessions/history")
    public String sessionHistory() {
        return "sessionHistory";
    }
}
