package com.grup.movieshelf.JPA.Entity.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Roles")
@Data
@NoArgsConstructor
public class Role implements Comparable<Role>, Serializable {

    @Id
    @Column( name = "roleName", unique = true, updatable = false )
    private String roleName;

    @Override
    public int compareTo(Role other) {
        return getRoleName().compareTo(other.getRoleName());
    }
}
