package com.grup.movieshelf.JPA.Entity.Sessions;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation implements Comparable<Recommendation> {
    Title title;
    Integer score;


    @Override
    public int compareTo(Recommendation o) {
        return o.getScore().compareTo(getScore());
    }
}
