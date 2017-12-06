package com.grup.movieshelf.Controller.API.Entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResult {

    List<TitleWithShelfStatus> titles;
    List<UserWithAddedStatus> users;

    public SearchResult() {
        titles = new ArrayList<>();
        users = new ArrayList<>();
    }
}
