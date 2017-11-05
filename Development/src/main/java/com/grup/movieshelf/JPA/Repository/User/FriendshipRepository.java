package com.grup.movieshelf.JPA.Repository;

import com.grup.movieshelf.JPA.Entity.Users.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, String> {

    Friendship findByFriendshipId(String friendshipId);

/*    List<Friendship> getAllByUserIdContaining(String search);
    List<Friendship> getAllByUserId2Containing(String search); */
}
