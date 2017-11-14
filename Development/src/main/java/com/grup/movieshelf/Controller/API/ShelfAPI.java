package com.grup.movieshelf.Controller.API;

import com.grup.movieshelf.Controller.API.Entity.*;
import com.grup.movieshelf.Controller.API.Entity.ResponseStatus;
import com.grup.movieshelf.JPA.Utility.HibernateUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShelfAPI {

    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;

    @PostMapping("/user/shelf")
    public ResponseStatus addTitle (Model model, @RequestParam("titleId") String titleId) {
        hibernateUserDetailsService.addTitleToShelf(titleId);

        return new ResponseStatus();
    }

    @DeleteMapping("/user/shelf/{titleId}")
    public ResponseStatus removeTitle (Model model, @PathVariable("titleId") String titleId) {
        hibernateUserDetailsService.removeTitleFromShelf(titleId);

        return new ResponseStatus();
    }
}
