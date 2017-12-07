package com.grup.movieshelf.JPA.Entity.Movies;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
public class TitleRating implements Comparable<TitleRating>, Serializable {

    @Id
    @NotNull
    @Column(name = "tconst", unique = true, updatable = false)
    private String titleId;

    @NotNull
    @Column(name = "average_rating", unique = false, updatable = true)
    private Float averageRating;

    @NotNull
    @Column(name = "num_votes", unique = false, updatable = true)
    private Integer numVotes;

    @Override
    public int compareTo(TitleRating o) {
        return getTitleId().compareTo(o.getTitleId());
    }
}
