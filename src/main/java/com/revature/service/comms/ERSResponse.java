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
    private String message;

    // error text to explain problem, eg which ID wasn't found

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
     * (Attempts to) determine the type of objects contained in the given List, and 
     * assigns it to the correct instance variable. The other list is set to be empty.
     * If the given list is empty, both lists will be set to be empty.
     * Assumes that lists will only contain ALL user profiles, or ALL reimb-reqs.
     * 
     * @param type
     * @param message
     * @param returnedList : must be a List of all UserProfiles, or ReimbReqs
     */
    public ERSResponse(
            ERSResponseType type, 
            String message, 
            List returnedList) {

        this.type = type;
        this.message = message;

        if (returnedList.isEmpty()){

            this.returnedUserProfiles = new ArrayList<UserProfile>();
            this.returnedReimbursementRequests = new ArrayList<ReimbursementRequest>();
        }
        else if (returnedList.get(0).getClass() == UserProfile.class){

            this.returnedUserProfiles = (List<UserProfile>)returnedList;
            this.returnedReimbursementRequests = new ArrayList<ReimbursementRequest>();
        }
        else{ // assume it must be a list of reimb-reqs

            this.returnedUserProfiles = new ArrayList<UserProfile>();
            this.returnedReimbursementRequests = (List<ReimbursementRequest>)returnedList;
        }
    }

    public ERSResponse(
            ERSResponseType type, 
            String message, 
            List<UserProfile> returnedUserProfiles,
            List<ReimbursementRequest> returnedReimbursementRequests){

        this.type = type;
        this.message = "";
        this.returnedUserProfiles = returnedUserProfiles;
        this.returnedReimbursementRequests = returnedReimbursementRequests;
    }

    // getters (no setters?)
    
    public ERSResponseType getType() {
        return this.type;
    }

    /**
     * Returns a reference to the list, not a copy
     * 
     * @return 
     */
    public List<UserProfile> getReturnedUserProfiles() {
        return this.returnedUserProfiles;
    }

    /**
     * Returns a reference to the list, not a copy
     * 
     * @return
     */
    public List<ReimbursementRequest> getReturnedReimbursementRequests() {
        return this.returnedReimbursementRequests;
    }

    /**
     * If no message was given, returns the empty string
     * 
     * @return
     */
    public String getMessage() {
        return this.message;
    }
}
