package com.grup.movieshelf.JPA.Entity.Sessions;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SessionResult")
@Data
@NoArgsConstructor
public class SessionResult {

    public SessionResult(@NotNull Integer sessionId, @NotNull String titleId) {
        this.sessionId = sessionId;
        this.titleId = titleId;
        sessionResultId = sessionId+"_"+titleId;
    }

    @Id
    @Column(name = "sessionResultId", unique = true, updatable = false)
    private String sessionResultId;

    @NotNull
    @Column(name = "sessionId")
    private Integer sessionId;

    @NotNull
    @Column(name = "tconst")
    private String titleId;
}
