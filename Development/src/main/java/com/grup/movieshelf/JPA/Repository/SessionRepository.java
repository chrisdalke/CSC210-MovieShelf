package com.grup.movieshelf.JPA.Repository;

import com.grup.movieshelf.JPA.Entity.Session;
import com.grup.movieshelf.JPA.Entity.Users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, String> {

    boolean existsBySessionCode(String sessionCode);
}
