package com.grup.movieshelf.JPA.Repository.User;

import com.grup.movieshelf.JPA.Entity.Users.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, String> {

    Friendship findByFriendshipId(String friendshipId);
}
