package com.grup.movieshelf.JPA.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Roles")
@Data
@NoArgsConstructor
public class Role implements Comparable<Role>, Serializable {

    @Id
    @Column( name = "roleName", unique = true, updatable = false )
    private String roleName;

    private Set<User> users;

    @ManyToMany(mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    @Override
    public int compareTo(Role other) {
        return getRoleName().compareTo(other.getRoleName());
    }
}
