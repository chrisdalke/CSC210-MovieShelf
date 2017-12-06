package com.grup.movieshelf.JPA.Entity.Movies;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
public class TitleGenre {

    @Id
    @NotNull
    @Column(name = "tconst", unique = false, updatable = false)
    private String titleId;

    @Column(name = "genre", unique = false, updatable = true)
    private String genre;
}
