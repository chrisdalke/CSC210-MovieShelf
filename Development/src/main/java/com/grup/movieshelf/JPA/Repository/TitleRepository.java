package com.grup.movieshelf.JPA.Repository;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, String> {

    Title getByTitleId(String titleId);

    List<Title> getAllByTitleNameContaining(String search);
}
