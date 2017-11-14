package com.grup.movieshelf.Controller.API;

import com.grup.movieshelf.Controller.API.Entity.*;
import com.grup.movieshelf.Controller.API.Entity.ResponseStatus;
import com.grup.movieshelf.JPA.Entity.Users.Friendship;
import com.grup.movieshelf.JPA.Repository.User.FriendshipRepository;
import com.grup.movieshelf.JPA.Repository.User.UserRepository;
import com.grup.movieshelf.JPA.Utility.HibernateSecurityService;
import com.grup.movieshelf.JPA.Utility.HibernateUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class FriendAPI {

    @Autowired
    private HibernateSecurityService hibernateSecurityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private HibernateUserDetailsService hibernateUserDetailsService;

    @PostMapping("/api/friends")
    public ResponseStatus addFriend (@RequestParam("userName") String friendUserName) {
        Integer userId = hibernateSecurityService.getLoggedInUser().getUserId();
        Integer friendId = userRepository.findByUsername(friendUserName).getUserId();
        String friendship_name = userId+"_"+friendId;
        if (friendshipRepository.getByFriendshipId(friendship_name) == null) {
            Friendship friendship = new Friendship(userId, friendId);
            friendshipRepository.save(friendship);
        }

        return new ResponseStatus();
    }

    @RequestMapping("/api/friends")
    public ResponseStatus removeFriend (@RequestParam("friendshipId") String friendId) {
        if (friendshipRepository.getByFriendshipId(friendId) != null) {
            Friendship friendship = friendshipRepository.getByFriendshipId(friendId);
            friendshipRepository.delete(friendship);
        }

        return new ResponseStatus();
    }
}
