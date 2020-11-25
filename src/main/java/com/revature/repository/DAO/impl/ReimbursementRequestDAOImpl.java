/**
 * This class provides an implementation of DAO functionality for reimb-reqs.
 * 
 * @author Andrew Curry
 */
package com.revature.repository.DAO.impl;

import java.util.List;

import com.revature.model.ReimbursementRequest;
import com.revature.repository.DAO.exceptions.DAOException;

public class ReimbursementRequestDAOImpl {

    // constants

    // class/static variables

    // instance variables

    // constructor(s)

    // ---------------------------
    // methods from ReimbursementRequestDAO
    // ---------------------------
    
    /**
     * Used to control search parameters.
     * I don't think this needs the complicated enum features
     */
    public enum SearchType{
        ALL, PENDING, RESOLVED;
    }

    /**
     * Returns a list of reimb-reqs matching the given constraints.
     * 
     * @param authorID : if authorID != -1, search is limited to reqs submitted by the 
     *      employee with the matching authorID. If authorID == -1, reqs from any employee
     *      will be considered.
     * @param searchBy : results are limited to reqs whose Status correpsonds to searchBy
     * @return
     * @throws DAOException
     */
    public List<ReimbursementRequest> getReimbursementRequests(
            int authorID, SearchType searchBy) throws DAOException{
        return null;
    }

    /**
     * Saves/writes the given reimb-req to the database.
     * Returns the ID of the reimb-req.
     * If given a new reimb-req (ID = -1), the system will determine a new, unique ID, and
     * return that.
     * 
     * @param reimb
     * @return ID of the reimb-req
     * @throws DAOException
     */
    public int saveReimbursementRequest(ReimbursementRequest reimb) throws DAOException{
        return 0;
    }

    /**
     * Returns true if there is a reimb-req in the database with the given id, false
     * otherwise.
     * 
     * @param reimbID
     * @return
     * @throws DAOException
     */
    public boolean checkExists(int reimbID) throws DAOException{
        return false;
    }

    /**
     * 
     * 
     * @param reimbID
     * @return
     * @throws DAOException
     */
    public ReimbursementRequest getReimbursementRequest(int reimbID) throws DAOException{
        return null;
    }
}
