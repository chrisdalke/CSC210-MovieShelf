package com.grup.movieshelf.Controller.API;

import com.grup.movieshelf.Controller.API.Entity.*;
import com.grup.movieshelf.Controller.API.Entity.ResponseStatus;
import com.grup.movieshelf.JPA.Entity.Users.UserTitle;
import com.grup.movieshelf.JPA.Utility.HibernateUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShelfAPI {

    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;

    @PostMapping("/api/shelf")
    public ResponseStatus addTitle (Model model, @RequestParam("titleId") String titleId) {
        hibernateUserDetailsService.addTitleToShelf(titleId);

        return new ResponseStatus();
    }

    @DeleteMapping("/api/shelf/{titleId}")
    public ResponseStatus removeTitle (Model model, @PathVariable("titleId") String titleId) {
        hibernateUserDetailsService.removeTitleFromShelf(titleId);

        return new ResponseStatus();
    }

    public void addTitleToShelf(String titleId) {
        Integer userId = hibernateSecurityService.getLoggedInUser().getUserId();
        UserTitle userTitle = new UserTitle(userId, titleId);
        userTitleRepository.save(userTitle);
    }

    public void removeTitleFromShelf(String titleId) {
        Integer userId = hibernateSecurityService.getLoggedInUser().getUserId();
        UserTitle userTitle = userTitleRepository.getByUserIdAndTitleId(userId, titleId);
        userTitleRepository.delete(userTitle);
    }

}
