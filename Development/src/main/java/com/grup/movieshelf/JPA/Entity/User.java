package com.grup.movieshelf.JPA.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User implements Comparable<User>, Serializable, UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "userId", unique = true, updatable = false)
    private Integer userId = -1;

    @NotNull
    @Column(name = "username", unique = true, updatable = true)
    private String username;

    // Hashed password using bcrypt2
    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UsersRoles",
            inverseJoinColumns = { @JoinColumn(referencedColumnName = "roleName")},
            joinColumns = { @JoinColumn( referencedColumnName = "userId")})
    private Set<Role> roles = new HashSet< Role >();

    @CreationTimestamp
    @Column(name = "created")
    private Timestamp dateCreated = Timestamp.from(Instant.now());

    @Override
    public int compareTo(User other){
        return Integer.compare(userId,other.userId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


