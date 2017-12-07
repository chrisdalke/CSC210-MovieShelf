package com.grup.movieshelf.Controller.API.Entity;

import com.grup.movieshelf.JPA.Entity.Sessions.UserSession;
import com.grup.movieshelf.JPA.Entity.Sessions.UserSuggestion;
import com.grup.movieshelf.JPA.Entity.Users.User;
import lombok.Data;

import java.util.List;

@Data
public class UserSessionData {

    private User user;
    private UserSession userSession;
    private List<UserSuggestion> userSuggestionList;
}
