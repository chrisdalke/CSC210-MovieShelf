package com.grup.movieshelf.Controller.API.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithAddedStatus {

    private Integer userId;
    private String username;
    private boolean added;
}
