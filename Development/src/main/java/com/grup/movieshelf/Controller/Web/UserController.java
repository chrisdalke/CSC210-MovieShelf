package com.grup.movieshelf.Controller.Web;

import com.grup.movieshelf.JPA.Entity.Users.User;
import com.grup.movieshelf.JPA.Entity.Users.Friendship;
import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import com.grup.movieshelf.JPA.Repository.User.FriendshipRepository;
import com.grup.movieshelf.JPA.Repository.User.RoleRepository;
import com.grup.movieshelf.JPA.Repository.User.UserRepository;
import com.grup.movieshelf.JPA.Utility.HibernateSecurityService;
import com.grup.movieshelf.JPA.Utility.HibernateUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
/*import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;*/
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    HibernateSecurityService hibernateSecurityService;

    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;

    @RequestMapping("/login")
    public String userLoginEndpoint(Model model){
        User userObject = hibernateSecurityService.getLoggedInUser();
        if (userObject == null) {
            return "userLogin";
        } else {
            return "redirect:/";
        }
    }

    /*
    Displays the user profile.
     */
    @RequestMapping("/user/profile")
    public String displayUserProfile(Model model){
        UserOptions userOptions = hibernateUserDetailsService.getUserOptionsForUser();
        model.addAttribute("userProfile", userOptions);
        return "userProfile";
    }

    /*
    Allows the user to edit their profile.
     */
    @GetMapping("/user/options")
    public String displayUserOptions(Model model) {
        UserOptions userOptions = hibernateUserDetailsService.getUserOptionsForUser();
        model.addAttribute("userOptions", userOptions);
        return "userOptions";
    }

    @PostMapping("/user/options")
    public String saveUserOptions(Model model, @ModelAttribute UserOptions userOptionsFormData) {

        UserOptions userOptions = hibernateUserDetailsService.getUserOptionsForUser();

        String displayName = userOptionsFormData.getDisplayName();
        String primaryLanguage = userOptionsFormData.getPrimaryLanguage();
        String birthdate = userOptionsFormData.getBirthdate();

        if(!displayName.isEmpty()) {
            userOptions.setDisplayName(displayName);
        }
        if(!primaryLanguage.isEmpty()) {
            userOptions.setPrimaryLanguage(primaryLanguage);
        }
        if(!birthdate.isEmpty()) {
            userOptions.setBirthdate(birthdate);
        }

        hibernateUserDetailsService.saveUserOptions(userOptions);
        model.addAttribute("message", "Successfully saved user options.");

        //return "userOptions";
        return "redirect:/user/profile";
    }

    @GetMapping("/user/register")
    public String getUser (Model model) {
        model.addAttribute("user", new User()); //builds user from form input provided by userRegister.html
        model.addAttribute("showForm",true);
        return "userRegister";
    }

    @PostMapping("/user/register")
    public String registerUser (Model model, @ModelAttribute User user) { //get the user object from before

        if (userRepository.findByUsername(user.getUsername()) == null)
        {
            hibernateUserDetailsService.saveNewUser(user);
            model.addAttribute("message", "Account made successfully.");
            model.addAttribute("showForm",false);
        }
        else
        {
            model.addAttribute("message", "Account creation failed. Username already exists.");
            model.addAttribute("showForm",true);
        }

        return "userRegister"; //return to a different page? maybe the home page?
    }

    @PostMapping("/user/addFriend")
    public String addFriend (Model model, @RequestParam("userName") String userName) {
        User userObject = hibernateSecurityService.getLoggedInUser();
        User friendObject = userRepository.findByUsername(userName);
        String friendship_name = userObject.getUserId()+"_"+friendObject.getUserId();
        if (friendshipRepository.findByFriendshipId(friendship_name) == null)
        {
            Friendship friendship = new Friendship(userObject.getUserId(), friendObject.getUserId());
            friendshipRepository.save(friendship);
            model.addAttribute("message", "Friend added successfully.");
            model.addAttribute("showForm",false);
        }
        else
        {
            model.addAttribute("message", "Friend could not be added. Friendship already exists.");
            model.addAttribute("showForm",true);
        }

        return "index";
    }

    @PostMapping("/user/removeFriend")
    public String removeFriend (Model model, @RequestParam("friendshipId") String friendshipId) {
/*        User userObject = hibernateSecurityService.getLoggedInUser();
        User friendObject = userRepository.findByUsername(friend.getUsername());
        String friendship_name = userObject.getUserId() + "_" + friendObject.getUserId();*/
        if (friendshipRepository.findByFriendshipId(friendshipId) == null)
        {
            model.addAttribute("message", "Friend could not be removed. Friendship does not exists.");
            model.addAttribute("showForm",true);
        }
        else
        {
            Friendship friendship = friendshipRepository.findByFriendshipId(friendshipId);
            friendshipRepository.delete(friendship);
            model.addAttribute("message", "Friend deleted successfully.");
            model.addAttribute("showForm",false);
        }

        return "index";
    }
}
