package com.grup.movieshelf.JPA.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "Sessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue
    @Column(name = "sessionId", unique = true, updatable = false)
    private String titleId;

    @NotNull
    @Column(name = "isExpired", unique = false, updatable = true)
    private boolean isExpired;

    @NotNull
    @Column(name = "sessionCode", unique = true, updatable = true)
    private String sessionCode;

    public static String generateSessionCode(){
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String sessionCode = "";
        for (int i = 0; i < 6; i++){
            sessionCode += alphabet.charAt(ThreadLocalRandom.current().nextInt(0, alphabet.length()));
        }
        return sessionCode;
    }
}
