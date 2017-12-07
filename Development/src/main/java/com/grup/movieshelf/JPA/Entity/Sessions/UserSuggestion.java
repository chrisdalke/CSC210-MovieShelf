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

    @Id
    @GeneratedValue
    @Column(name = "userSuggestionId", unique = true, updatable = false)
    private Integer userSuggestionId = -1;

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
