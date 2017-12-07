///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.JPA.Repository;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Sessions.UserSession;
import com.grup.movieshelf.JPA.Entity.Sessions.UserSuggestion;
import org.h2.engine.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/////////////////////////////////////////////////////////////
// Sessions Repository
/////////////////////////////////////////////////////////////

public interface UserSuggestionRepository extends JpaRepository<UserSuggestion, Integer> {

    List<UserSuggestion> getAllByUserId(Integer userId);
    List<UserSuggestion> getAllBySessionId(Integer sessionId);
    List<UserSuggestion> getAllByUserIdAndSessionId(Integer userId, Integer sessionId);

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////