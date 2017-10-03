package com.grup.movieshelf.Controller.API;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chrisdalke on 10/2/17.
 */
@RestController
public class TestRestController {

    @RequestMapping("/test_rest")
    public String testRestController(){
        return "Hello World!";
    }
}
