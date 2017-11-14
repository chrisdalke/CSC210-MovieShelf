package com.grup.movieshelf.Controller.API.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStatus {
    private int status = 0;
    private String message = "The operation succeeded.";
}
