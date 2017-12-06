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
import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Entity.Users.UserTitle;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import com.grup.movieshelf.JPA.Repository.UserRepository;
import com.grup.movieshelf.JPA.Repository.FriendshipRepository;
import com.grup.movieshelf.JPA.Repository.UserTitlesRepository;
import com.grup.movieshelf.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/////////////////////////////////////////////////////////////
// Index Controller
// Displays the index / landing page.
/////////////////////////////////////////////////////////////

@Controller
public class IndexController {

    //------------------------------------------------
    // Dependencies
    //------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    UserTitlesRepository userTitlesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    MetadataService metadataService;

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    // Index page, customized based on whether the user is looged in or not.
    @GetMapping("/")
    public String indexPage(HttpServletRequest request, Model model){
        if (request.getUserPrincipal() == null) {
            return "redirect:/login";
        } else {
            User user = userService.getLoggedInUser();
            model.addAttribute("title", "Logged In");
            model.addAttribute("userData", user);

            ArrayList<ArrayList<Title>> titleRows = new ArrayList<>();
            // Build titles list into static rows to make the page appear to load faster
            List<UserTitle> titles = userTitlesRepository.getAllByUserId(user.getUserId());

            for (int i = 0; i < titles.size(); i +=3){
                ArrayList<Title> singleRow = new ArrayList<>();
                if (i + 0 < titles.size()){
                    singleRow.add(titleRepository.getByTitleId(titles.get(i+0).getTitleId()));
                }
                if (i + 1 < titles.size()){
                    singleRow.add(titleRepository.getByTitleId(titles.get(i+1).getTitleId()));
                }
                if (i + 2 < titles.size()){
                    singleRow.add(titleRepository.getByTitleId(titles.get(i+2).getTitleId()));
                }
                titleRows.add(singleRow);
            }

            model.addAttribute("userTitleRows",titleRows);
            model.addAttribute("titleRepo", titleRepository);
            model.addAttribute("friendRepo", friendshipRepository);
            model.addAttribute("userRepo", userRepository);
            model.addAttribute("metadataService", metadataService);
        }

        UserOptions userOptions = userService.getUserOptions();
        model.addAttribute("userProfile", userOptions);

        return "index";
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////