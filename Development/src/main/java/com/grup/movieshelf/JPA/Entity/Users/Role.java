///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.JPA.Entity.Users;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/////////////////////////////////////////////////////////////
// Role Model Object
// Represents an account role eg. ADMIN, GUEST, etc.
/////////////////////////////////////////////////////////////

@Entity
@Table(name = "Roles")
@Data
@AllArgsConstructor
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

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////