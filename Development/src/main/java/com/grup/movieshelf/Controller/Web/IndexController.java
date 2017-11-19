///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Controller.Web;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
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

    //------------------------------------------------
    // Request Mappings
    //------------------------------------------------

    // Index page, customized based on whether the user is looged in or not.
    @GetMapping("/")
    public String indexPage(HttpServletRequest request, Model model){
        if (request.getUserPrincipal() == null) {
            model.addAttribute("title", "Not Logged In");
        } else {
            User user = userService.getLoggedInUser();
            model.addAttribute("title", "Logged In");
            model.addAttribute("userData", user);
            model.addAttribute("userTitles", userTitlesRepository.getAllByUserId(user.getUserId()));
            model.addAttribute("titleRepo", titleRepository);
            model.addAttribute("friendRepo", friendshipRepository);
            model.addAttribute("userRepo", userRepository);
        }

        UserOptions userOptions = userService.getUserOptions();
        model.addAttribute("userProfile", userOptions);

        return "index";
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////