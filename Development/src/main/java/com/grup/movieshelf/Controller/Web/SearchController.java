package com.grup.movieshelf.Controller.Web;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    TitleRepository titleRepository;

    @PostMapping("/search")
    public String search (Model model, @RequestParam("searchString") String searchString) {
        System.out.println("User searched \"" + searchString + "\".");

        // TODO: make search bar support not just movie titles (actors, directors, etc.)
        List<Title> titles = titleRepository.getAllByTitleNameContaining(searchString);


        System.out.println("Returned results:");
        for(Title title : titles) {
            System.out.println(title.getTitleName() + " (" + title.getYear() + ")");
        }

        // TODO: return "/search/results"
        return "redirect:/";
    }
}
