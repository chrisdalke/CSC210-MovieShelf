package com.grup.movieshelf.JPA.Entity.Sessions;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "UserSession")
@Data
@NoArgsConstructor
public class UserSession implements Comparable<UserSession>, Serializable {

    public UserSession(Integer userId, Integer sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.userSessionId = String.valueOf(userId) + String.valueOf(sessionId);
    }

    @Id
    @Column(name = "userSessionId", unique = true, updatable = false)
    private String userSessionId;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "sessionId")
    private Integer sessionId;

    @Override
    public int compareTo(UserSession o) {
        return getUserSessionId().compareTo(o.getUserSessionId());
    }
}
