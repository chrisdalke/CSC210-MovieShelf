package com.grup.movieshelf.JPA.Entity.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Friendship")
@Data
@NoArgsConstructor
public class UserTitle implements Comparable<UserTitle>, Serializable {

    public UserTitle(Integer userId, Integer userId2) {
        this.userId = userId;
        this.userId2 = userId2;
        this.friendshipId = String.valueOf(userId) + "_" + String.valueOf(userId2);
    }

    @Column(name = "userId", unique = true, updatable = false)
    private Integer userId;

    @Column(name = "userId2", unique = true, updatable = false)
    private Integer userId2;

    @Id
    @Column(name = "friendshipId", unique = true, updatable = false)
    private String friendshipId;

    @Override
    public int compareTo(Friendship o) {
        return getFriendshipId().compareTo(o.getFriendshipId());
    }
}
