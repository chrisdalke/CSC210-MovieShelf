package com.grup.movieshelf.JPA.Entity.Sessions;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "UserSuggestion")
@Data
@NoArgsConstructor
public class UserSuggestion {

    public UserSuggestion(@NotNull Integer userId, @NotNull Integer sessionId, @NotNull String titleId) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.titleId = titleId;
        userSuggestionId = userId+"_"+sessionId+"_"+titleId;
    }

    @Id
    @Column(name = "userSuggestionId", unique = true, updatable = false)
    private String userSuggestionId;

    @NotNull
    @Column(name = "userId")
    private Integer userId;

    @NotNull
    @Column(name = "sessionId")
    private Integer sessionId;

    @NotNull
    @Column(name = "tconst")
    private String titleId;
}
