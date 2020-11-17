/**
 * This interface describes the public-facing methods needed for retrieving reimb-reqs 
 * from, and storing them in, the database.
 * There is also an enum.
 */
package com.revature.repository.DAO.interfaces;

import java.util.List;

import com.revature.model.ReimbursementRequest;
import com.revature.repository.DAO.exceptions.DAOException;

public interface ReimbursementRequestDAO {
    
    /**
     * Used to control search parameters.
     * I don't think this needs the complicated enum features
     */
    public enum SearchType{
        ALL, PENDING, RESOLVED;
    }

    /**
     * 
     * @param ID : if ID != -1, search is limited to reqs submitted by the employee with 
     *      the matching ID. If ID == -1, reqs from any employee will be considered.
     * @param searchBy : results are limited to reqs whose Status correpsonds to searchBy
     * @return
     */
    public List<ReimbursementRequest> getReimbursementRequests(
            int ID, SearchType searchBy) throws DAOException;
}
