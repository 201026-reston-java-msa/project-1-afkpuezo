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

import com.revature.repository.DAO.interfaces.ReimbursementRequestDAO;
import com.revature.repository.DAO.interfaces.UserProfileDAO;
import com.revature.service.comms.ERSRequest;
import com.revature.service.comms.ERSResponse;

public class ViewRequestHandler {
    
    // enums -------------------------------

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
     * 
     * @param req
     * @return
     */
    public ERSResponse handleEmployeeViewPending(ERSRequest req){

        
        return null;
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
}
