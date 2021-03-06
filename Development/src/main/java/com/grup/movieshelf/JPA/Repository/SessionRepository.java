///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.JPA.Repository;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import com.grup.movieshelf.JPA.Entity.Sessions.Session;
import org.springframework.data.jpa.repository.JpaRepository;

/////////////////////////////////////////////////////////////
// Sessions Repository
/////////////////////////////////////////////////////////////

public interface SessionRepository extends JpaRepository<Session, Integer> {

    boolean existsBySessionCode(String sessionCode);
    Session getBySessionCode(String sessionCode);

}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////