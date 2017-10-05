package com.grup.movieshelf.Controller.API;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

    @RequestMapping("/test_rest")
    public String testRestController(){
        return "Hello World!";
    }

    @RequestMapping("/numbers")
    public String returnNumbers(){
        String numbers = "";
        for (int i = 1; i <= 10; i++){
            numbers += i + " ";
        }
        return numbers;
    }

    @RequestMapping(value = "/poop",method = RequestMethod.POST)
    public String testPost(){
        return "Poop";
    }
}
