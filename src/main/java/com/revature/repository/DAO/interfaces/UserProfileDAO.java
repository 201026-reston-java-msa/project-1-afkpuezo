/**
 * This interface describes the public-facing methods needed for retrieving users from,
 * and storing them in, the database
 */
package com.revature.repository.DAO.interfaces;

import java.util.List;

import com.revature.model.UserProfile;
import com.revature.repository.DAO.exceptions.DAOException;

public interface UserProfileDAO {
    
    /**
     * Determines if the indicated user exists.
     * Throws exception if there is a database communication problem.
     * @param userID
     * @return
     * @throws DAOException
     */
    public boolean checkExists(int userID) throws DAOException;

    /**
     * Determines if the indicated user exists.
     * Throws exception if there is a database communication problem.
     */
    public boolean checkExists(String username) throws DAOException;

    /**
     * Should return the safe, encrypted version of the password.
     * Throws exception if there is a database communication problem.
     * Throws exception if the user is not found.
     */
    public String getPassword(int userID) throws DAOException;
    
    /**
     * Should return the safe, encrypted version of the password.
     * Throws exception if there is a database communication problem.
     * Throws exception if the user is not found.
     */
    public String getPassword(String username) throws DAOException;

    /**
     * Returns a filled-out UserProfile object.
     * Throws exception if there is a database communication problem.
     * Throws exception if the user is not found.
     * @param userID
     * @return
     * @throws DAOException
     */
    public UserProfile getUserProfile(int userID) throws DAOException;

    /**
     * Returns a filled-out UserProfile object. 
     * Throws exception if there is a database communication problem. 
     * Throws exception if the user is not found.
     * 
     * @param username
     * @return
     * @throws DAOException
     */
    public UserProfile getUserProfile(String username) throws DAOException;

    /**
     * Returns a list of all employee profiles in the system.
     * Returns an empty list if there are no employees.
     * Throws exception if there is a database communication problem. 
     * Throws exception if the user is not found.
     * @return
     * @throws DAOException
     */
    public List<UserProfile> getAllEmployeeProfiles() throws DAOException;

    /**
     * If no matching user exists, a new one is created.
     * If a matching user DOES exist, ID and Role will not be updated,
     * but all other properties will be.
     * Throws exception if there is a database communication problem. 
     * 
     * @param up
     * @throws DAOException
     */
    public void saveUserProfile(UserProfile up) throws DAOException;
}
