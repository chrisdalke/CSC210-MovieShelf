package com.grup.movieshelf.JPA.Repository;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, String> {

    Title getTitleByTitleId(String titleId);
}
