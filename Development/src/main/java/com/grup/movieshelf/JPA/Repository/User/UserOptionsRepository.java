package com.grup.movieshelf.JPA.Repository.User;

import com.grup.movieshelf.JPA.Entity.Users.UserOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOptionsRepository extends JpaRepository<UserOptions, Integer> {

    UserOptions findByUserId(Integer userId);
}
