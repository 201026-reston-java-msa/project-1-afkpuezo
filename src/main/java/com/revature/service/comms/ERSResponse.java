/**
 * This class is used for communicating between the front-end and service layers.
 * When the service layer evaluates an ERSRequest, it returns an object of this class,
 * containing the relevant information.
 */
package com.revature.service.comms;

import java.util.ArrayList;
import java.util.List;

import com.revature.model.ReimbursementRequest;
import com.revature.model.UserProfile;

public class ERSResponse {
    
    // enums ---------------------

    /**
     * Indicates how the prompting ERSRequest was carried out. Either success, or some
     * flavor of failure.
     */
    public enum ERSResponseType{

        NONE, // never used?
        SUCCESS, 
        FORBIDDEN, // if the current user doesn't have permission
        INVALID_PARAMETER, // eg, user not found
        DATABASE_ERROR // seemingly valid request, but some problem with the database
    }

    // class/static variables -----------------

    // instance variables -----------------
    private ERSResponseType type;
    private List<UserProfile> returnedUserProfiles;
    private List<ReimbursementRequest> returnedReimbursementRequests;
    private String message; // error text to explain problem, eg which ID wasn't found

    // constructor(s) ------------------

    /**
     * Initializes the message to the empty string, and the Lists to empty lists.
     * 
     * @param type
     */
    public ERSResponse(ERSResponseType type){

        this.type = type;
        this.message = "";
        this.returnedUserProfiles = new ArrayList<UserProfile>();
        this.returnedReimbursementRequests = new ArrayList<ReimbursementRequest>();
    }

    /**
     * (I think) This constructor is best for reporting failures. Initializes the
     * Lists to empty lists.
     * 
     * @param type
     * @param message
     */
    public ERSResponse(ERSResponseType type, String message){

        this.type = type;
        this.message = message;
        this.returnedUserProfiles = new ArrayList<UserProfile>();
        this.returnedReimbursementRequests = new ArrayList<ReimbursementRequest>();
    }

    /**
     * Sets the list of reimb-reqs to be an empty list.
     * 
     * @param type
     * @param message
     * @param returnedUserProfiles
     */
    public ERSResponse(
            ERSResponseType type, 
            String message, 
            List<UserProfile> returnedUserProfiles) {

        this.type = type;
        this.message = message;
        this.returnedUserProfiles = returnedUserProfiles;
        this.returnedReimbursementRequests = new ArrayList<ReimbursementRequest>();
    }

    /*
    public ERSResponse(
            ERSResponseType type, 
            String message, 
            List<ReimbursementRequest> returnedReimbursementRequests) {

    }
    */

    )
}
