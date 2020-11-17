/**
 * This interface describes the public-facing methods needed for retrieving users from,
 * and storing them in, the database
 */
package com.revature.repository.DAO.interfaces;

import java.util.List;

import com.revature.model.UserProfile;

public interface UserProfileDAO {
    
    public boolean checkExists(int userID);
    public boolean checkExists(String username);

    public String getPassword(int userID);
    public String getPassword(String username);

    public UserProfile getUserProfile(int userID);
    public UserProfile getUserProfile(String username);

    public List<UserProfile> getAllUserProfiles();

    public void saveUserProfile(UserProfile up);
}
