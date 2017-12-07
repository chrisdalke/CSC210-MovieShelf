package com.grup.movieshelf.JPA.Entity.Movies;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
public class TitleRating {

    @Id
    @NotNull
    @Column(name = "tconst", unique = true, updatable = false)
    private String titleId;

    @Column(name = "average_rating", unique = false, updatable = true)
    private float averageRating;

    @Column(name = "num_votes", unique = false, updatable = true)
    private int numVotes;
}
