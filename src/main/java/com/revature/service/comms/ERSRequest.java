/**
 * This class is used for communicating between the front-end and service layers.
 * When the user wants/attempts to take an action, the front end will create a
 * corresponding ERSRequest object and pass it to the service layer to be processed.
 */
package com.revature.service.comms;

import java.util.HashMap;
import java.util.Map;

import com.revature.model.UserProfile;
import com.revature.model.UserProfile.UserRole;

public class ERSRequest {
    
    // enums

    /**
     * Describes what kind of action the user is trying to do.
     * I don't think this needs the 'advanced' enum features because it will never be
     * saved/written anywhere else.
     * Hopefully the names aren't too inconsistent. I tried to only use EMPLOYEE or
     * MANAGER as a prefix when there was a risk of ambiguity.
     */
    public enum Type{

        NONE,
        LOGIN,
        LOGOUT,
        SUBMIT_REQUEST,
        EMPLOYEE_VIEW_PENDING,
        EMPLOYEE_VIEW_RESOLVED,
        EMPLOYEE_VIEW_SELF,
        EMPLOYEE_UPDATE_SELF,
        APPROVE_REQUEST,
        DENY_REQUEST,
        VIEW_ALL_REQUESTS,
        MANAGER_VIEW_ALL_PENDING,
        MANAGER_VIEW_ALL_RESOLVED,
        VIEW_ALL_EMPLOYEES,
        MANAGER_VIEW_BY_EMPLOYEE
    }

    // class/static variables

    // instance variables
    private Type type;
    private int userID; // still not sure if this is the right way to do this
    private UserRole userRole;

    private Map<String, String> params;

    // constructor(s)
    
    // removed this no-args constructor because I think it's not correct to change any of
    // these properties after initialization
    /**
     * Defaults to NONE type, -1 userID, and LOGGED_OUT UserRole
     */
    /*
    public ERSRequest(){

        this.type = Type.NONE;
        this.userID = -1;
        this.userRole = UserRole.LOGGED_OUT;
        this.params = new HashMap<String, String>();
    }
    */

    public ERSRequest(Type type, int userID, UserRole userRole){

        this.type = type;
        this.userID = userID;
        this.userRole = userRole;
        this.params = new HashMap<String, String>();
    }

    // getters and setters - mostly just getters

    public Type getType() {
        return this.type;
    }

    public int getUserID() {
        return this.userID;
    }

    public UserRole getUserRole() {
        return this.userRole;
    }

    // methods for getting and putting params

    /**
     * Adds the given parameter to the Request.
     * If the given key is already used, the old param will be thrown away.
     * 
     * @param key
     * @param param
     */
    public void putParameter(String key, String param){

        params.put(key, param);
    }

    /**
     * Returns the parameter associated with the given key. Returns null if the key
     * has not been added to the map.
     * 
     * @param key
     * @return the param, or null if the key has not been added to the map
     */
    public String getParameter(String key){

        if (hasParameter(key)) return params.get(key);
        else return null;
    }

    /**
     * Determines whether or not this ERSreq has a param associated with the given key.
     * 
     * @param key
     * @return
     */
    public boolean hasParameter(String key){

        return params.containsKey(key);
    }
}
