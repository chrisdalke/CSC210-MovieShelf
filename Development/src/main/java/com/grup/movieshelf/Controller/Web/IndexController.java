package com.grup.movieshelf.Controller.Web;

import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Entity.Users.Friendship;
import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Repository.TitleRepository;
import com.grup.movieshelf.JPA.Repository.User.UserRepository;
import com.grup.movieshelf.JPA.Repository.User.FriendshipRepository;
import com.grup.movieshelf.JPA.Repository.User.UserTitlesRepository;
import com.grup.movieshelf.JPA.Utility.HibernateSecurityService;
import com.grup.movieshelf.JPA.Utility.HibernateUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;

    @Autowired
    HibernateSecurityService hibernateSecurityService;

    @Autowired
    UserTitlesRepository userTitlesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    @GetMapping("/")
    public String testWeb(HttpServletRequest request, Model model){


        if (request.getUserPrincipal() == null) {
            model.addAttribute("title", "Not Logged In");
        } else {
            User user = hibernateSecurityService.getLoggedInUser();
            model.addAttribute("title", "Logged In");
            model.addAttribute("userData", user);
            model.addAttribute("userTitles", userTitlesRepository.getAllByUserId(user.getUserId()));
            model.addAttribute("titleRepo", titleRepository);
            model.addAttribute("friendRepo", friendshipRepository);
            model.addAttribute("friendships", friendshipRepository.getAllByUserId(user.getUserId()));
            model.addAttribute("userRepo", userRepository);
        }

        UserOptions userOptions = hibernateUserDetailsService.getUserOptionsForUser();
        model.addAttribute("userProfile", userOptions);

        return "index";
    }



    @PostMapping("/index/addFriend")
    public String addFriend (Model model, @RequestParam("userName") String userName) {
        hibernateUserDetailsService.addFriend(userName);
        return "redirect:/";
    }

    @RequestMapping("/index/removeFriend/{friendshipId}")
    public String removeFriend (Model model, @PathVariable("friendshipId") String friendshipId) {
        hibernateUserDetailsService.removeFriend(friendshipId);
        return "redirect:/";
    }
}