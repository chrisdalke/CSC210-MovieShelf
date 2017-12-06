package com.grup.movieshelf.Controller.API.Entity;

import com.grup.movieshelf.JPA.Entity.Movies.Title;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleWithShelfStatus {

    private Title title;
    private boolean containedInShelf;
}
