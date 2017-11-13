package com.grup.movieshelf.JPA.Repository.User;

import com.grup.movieshelf.JPA.Entity.Users.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, String> {

    Friendship getByFriendshipId(String friendshipId);

    List<Friendship> getAllByUserId(Integer userId);
}
