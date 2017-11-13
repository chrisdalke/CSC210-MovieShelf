package com.grup.movieshelf.JPA.Repository.User;

import com.grup.movieshelf.JPA.Entity.Users.UserTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserTitlesRepository extends JpaRepository<UserTitle, String> {

    UserTitle getByUserIdAndTitleId(Integer userId, String titleId);

    List<UserTitle> getAllByUserId(Integer userId);
}
