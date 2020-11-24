/**
 * This class represents the password to open a given account. It is segregated from
 * the rest of the user profile info.
 */
package com.revature.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="USER_PASSWORD")
public class UserPassword implements Serializable {

    // constants
    private static final long serialVersionUID = 0L; // makes compiler happy
    
    // instance vars
    @OneToOne
    @JoinColumn(name="USER_ID", nullable=false, referencedColumnName = "USER_ID")
    private UserProfile user;

    @Column(name="PASS")
    private String pass;

    // constructor(s)
    
    public UserPassword(){

    }

    // getters and setters

    public UserProfile getUser() {
        return this.user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
