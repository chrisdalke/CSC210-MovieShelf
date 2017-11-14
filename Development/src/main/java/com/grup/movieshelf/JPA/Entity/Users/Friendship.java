package com.grup.movieshelf.JPA.Entity.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Friendship")
@Data
@NoArgsConstructor
public class Friendship implements Comparable<Friendship>, Serializable {

    public Friendship(Integer userId, Integer userId2) {
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

    public void setUserId (int n)
    {
        userId = n;
        friendshipId = String.valueOf(userId) + "_" + String.valueOf(userId2);
    }

    public void setUserId2 (int n)
    {
        userId2 = n;
        friendshipId = String.valueOf(userId) + "_" + String.valueOf(userId2);
    }

    @Override
    public int compareTo(Friendship o) {
        return getFriendshipId().compareTo(o.getFriendshipId());
    }
}
