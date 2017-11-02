package com.grup.movieshelf.Controller.API;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchAPIController {

    @Autowired
    TitleRepository titleRepository;

    @PostMapping("/search")
    public List<Title> search (Model model, @RequestParam("searchString") String searchString) {
        System.out.println("User searched \"" + searchString + "\".");

        // TODO: make search bar support not just movie titles (actors, directors, etc.)
        List<Title> titles = titleRepository.getAllByTitleNameContaining(searchString);

        System.out.println("Returned results:");
        for(Title title : titles) {
            System.out.println(title.getTitleName() + " (" + title.getYear() + ")");
        }

        return titles;
    }
}
