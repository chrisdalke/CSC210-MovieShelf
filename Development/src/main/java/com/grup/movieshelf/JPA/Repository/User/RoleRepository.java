package com.grup.movieshelf.JPA.Repository.User;

import com.grup.movieshelf.JPA.Entity.Users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

}
