/**
 * This interface describes the public-facing methods needed for retrieving users from,
 * and storing them in, the database
 */
package com.revature.repository.DAO.interfaces;

import java.util.List;

import com.revature.model.UserProfile;
import com.revature.repository.DAO.exceptions.DAOException;

public interface UserProfileDAO {
    
    public boolean checkExists(int userID) throws DAOException;
    public boolean checkExists(String username) throws DAOException;

    public String getPassword(int userID) throws DAOException;
    public String getPassword(String username) throws DAOException;

    public UserProfile getUserProfile(int userID) throws DAOException;
    public UserProfile getUserProfile(String username) throws DAOException;

    public List<UserProfile> getAllUserProfiles() throws DAOException;

    public void saveUserProfile(UserProfile up) throws DAOException;
}
