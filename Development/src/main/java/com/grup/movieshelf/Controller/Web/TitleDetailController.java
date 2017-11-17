///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.Web;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import com.grup.movieshelf.Service.SessionService;
import com.grup.movieshelf.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/////////////////////////////////////////////////////////////
// Movie Detail Controller
// Manages pages displaying details about a movie title.
/////////////////////////////////////////////////////////////

@Controller
public class TitleDetailController {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    UserService userService;

    @Autowired
    TitleRepository titleRepository;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    @GetMapping("/titles/{titleId}")
    public String movieDetailView(@PathVariable("titleId") String titleId, Model model) throws Exception {

        Title title = titleRepository.getByTitleId(titleId);
        if (title == null){
          throw new Exception("Title does not exist!");
        }

        model.addAttribute("title",title);

        return "movieDetailView";
    }

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////