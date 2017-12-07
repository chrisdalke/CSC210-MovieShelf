package com.grup.movieshelf.JPA.Repository;

import com.grup.movieshelf.JPA.Entity.Movies.TitleGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleGenreRepository extends JpaRepository<TitleGenre, String> {

    List<TitleGenre> getAllByTitleId(String titleId);

    List<TitleGenre> getAllByGenre(String genre);
}
