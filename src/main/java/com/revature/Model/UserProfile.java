/**
 * This class represents User Profiles within the application. 
 * 
 * @Author Andrew Curry
 */
package com.revature.Model;

public class UserProfile {
    
    // enums

    /**
     * Enumerates the user roles.
     * I'm experimenting with this more advanced enum syntax.
     */
    public enum Role{

        NONE(0, "NONE"), // never used?
        LOGGED_OUT(1, "LOGGEDOUT"), // used if there is no user currently logged in
        EMPLOYEE(2, "Employee"),
        MANAGER(3, "Manager");

        private int ID;
        private String name;

        private Role(int ID, String name){
            this.ID = ID;
            this.name = name;
        }

        public int getID(){
            return ID;
        }

        public String getName(){
            return name;
        }
    } // end enum Role

    // class/static variables

    // instance variables
    private int id;
    private Role role;
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;

    // constructor(s)
    public UserProfile(int id, Role role){

        this.id = id;
        this.role = role;
        username = "";
        firstName = "";
        lastName = "";
        emailAddress = "";
    }

    public UserProfile(
            int id, 
            Role role, 
            String username, 
            String firstName, 
            String lastName, 
            String emailAddress) {

        this.id = id;
        this.role = role;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    // getters and setters
    // (currently no way to set ID or Role outside of constructor)

    public int getId() {
        return this.id;
    }

    public Role getRole() {
        return this.role;
    }

    public String getUsername() {
        return this.username;
    }

    /**
     * Does NOT check to make sure the username is in the valid format.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Does NOT check to make sure the firstName is in the valid format.
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    /**
     * Does NOT check to make sure the lastName is in the valid format.
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    /**
     * Does NOT check to make sure the emailAddress is in the valid format.
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
