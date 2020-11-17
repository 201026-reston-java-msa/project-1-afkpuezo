/**
 * This class is used for communicating between the front-end and service layers.
 * When the user wants/attempts to take an action, the front end will create a
 * corresponding ERSRequest object and pass it to the service layer to be processed.
 */
package com.revature.service.comms;

import java.util.Map;

import com.revature.model.UserProfile;

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
    private UserProfile currentUser; // i'm still iffy on how to represent this
    private Map<String, String> params;
}
