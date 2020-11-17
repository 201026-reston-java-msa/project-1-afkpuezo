/**
 * This class is for handling view-requests, that is, requests to view something, rather
 * than a 'get a look at the request handler'.
 * 
 * It is used by the ServiceFront; it contains handle methods for those request types
 * whose (primary) purpose is to retrieve information from the database and display it to
 * the user.
 * 
 * Each handler method assumes that the ServiceFront has checked to make sure the user's
 * role is one that is permitted to take that action. Other permission/possibility checks
 * may still occur.
 */
package com.revature.service;

import com.revature.model.ReimbursementRequest;
import com.revature.repository.DAO.exceptions.DAOException;
import com.revature.repository.DAO.interfaces.ReimbursementRequestDAO;
import com.revature.repository.DAO.interfaces.UserProfileDAO;
import com.revature.repository.DAO.interfaces.ReimbursementRequestDAO.SearchType;
import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSResponse;
import com.revature.service.comms.ERSResponse.ERSResponseType;

import java.util.List;

public class ViewRequestHandler {
    
    // enums -------------------------------

    // constants ---------------------------

    // static / class variables ------------

    // instance variables ------------------
    private UserProfileDAO updao;
    private ReimbursementRequestDAO rrdao;

    // constructor(s) ----------------------

    public ViewRequestHandler(UserProfileDAO updao, ReimbursementRequestDAO rrdao){

        this.updao = updao;
        this.rrdao = rrdao;
    }

    // handler methods ---------------------------------------------

    /**
     * Allows an employee to view all of their own pending reimb-reqs.
     * If there are no such requests, succeeds and returns a response with an empty list.
     * Fails if the employee doesnt exist.
     * Fails if there is a DAOException.
     * 
     * @param req
     * @return
     */
    public ERSResponse handleEmployeeViewPending(ERSRequest req){

        ERSResponse res;
        int userID = req.getUserID();

        try{
            if (!updao.checkExists(userID)) return userDoesNotExist(userID);

            List<ReimbursementRequest> reimbs = 
                    rrdao.getReimbursementRequests(userID, SearchType.ALL);
            
            return new ERSResponse(
                    ERSResponseType.SUCCESS,
                    (List)reimbs);
        }
        catch(DAOException e){

        }

        return null; // shouldn't be reached? but satisfies compiler
    }

    public ERSResponse handleEmployeeViewResolved(ERSRequest req) {
        return null;
    }

    public ERSResponse handleEmployeeViewSelf(ERSRequest req) {
        return null;
    }

    public ERSResponse handleAllRequests(ERSRequest req) {
        return null;
    }

    public ERSResponse handleManagerViewPending(ERSRequest req) {
        return null;
    }

    public ERSResponse handleManagerViewResolved(ERSRequest req) {
        return null;
    }

    public ERSResponse handleViewAllEmployees(ERSRequest req) {
        return null;
    }

    public ERSResponse handleManagerViewByEmployee(ERSRequest req) {
        return null;
    }

    // helper methods ----------------------

    /**
     * Returns a response indicating that a user was not found.
     */
    private ERSResponse userDoesNotExist(int userID) {

        return new ERSResponse(
            ERSResponseType.INVALID_PARAMETER, 
            String.format("No user profile with ID %d was found.", userID));
    }
}
