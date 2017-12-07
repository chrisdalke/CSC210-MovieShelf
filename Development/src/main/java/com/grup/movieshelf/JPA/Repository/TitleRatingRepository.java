package com.grup.movieshelf.JPA.Repository;

import com.grup.movieshelf.JPA.Entity.Movies.TitleRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleRatingRepository extends JpaRepository<TitleRating, String> {

    TitleRating findByTitleId(String titleId);

    List<TitleRating> findByNumVotesBetween(int min, int max);

    List<TitleRating> findByAverageRatingBetween(float min, float max);
}
