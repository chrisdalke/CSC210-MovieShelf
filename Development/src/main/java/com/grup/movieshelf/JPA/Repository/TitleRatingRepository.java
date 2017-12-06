package com.grup.movieshelf.JPA.Repository;

import com.grup.movieshelf.JPA.Entity.Movies.TitleRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRatingRepository extends JpaRepository<TitleRating, String> {

    TitleRating getByTitleId(String titleId);
}
