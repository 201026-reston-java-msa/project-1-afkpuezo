/**
 * This class provides an implementation of DAO functionality for UserProfiles.
 */
package com.revature.repository.DAO.impl;

import java.util.List;

import com.revature.model.UserPassword;
import com.revature.model.UserProfile;
import com.revature.repository.DAO.exceptions.DAOException;
import com.revature.repository.DAO.interfaces.UserProfileDAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.revature.repository.Util.HibernateConnectionUtil;

public class UserProfileDAOImpl implements UserProfileDAO {
    
    // constants

    // class/static variables

    // instance variables

    // constructor(s)

    // ---------------------------
    // methods from UserProfileDAO
    // ---------------------------
    /**
     * Determines if the indicated user exists.
     * Throws exception if there is a database communication problem.
     * @param userID
     * @return
     * @throws DAOException
     */
    public boolean checkExists(int userID) throws DAOException{

        Session session = HibernateConnectionUtil.getSession();
        UserProfile up = (UserProfile)session.get(UserProfile.class, userID);
        session.close();
        return up != null;
    }

    /**
     * Determines if the indicated user exists.
     * Throws exception if there is a database communication problem.
     */
    @SuppressWarnings("unchecked")
    public boolean checkExists(String username) throws DAOException{
        
        Session session = HibernateConnectionUtil.getSession();

        Criteria crit = session.createCriteria(UserProfile.class);
        crit.add(Restrictions.like("username", username));
        List<UserProfile> userList = crit.list();
        session.close();
        return !userList.isEmpty();

    }

    /**
     * Determines if the indicated user exists.
     * Throws exception if there is a database communication problem.
     */
    @SuppressWarnings("unchecked")
    public boolean checkExistsEmail(String emailAddress) throws DAOException{
        
        Session session = HibernateConnectionUtil.getSession();

        Criteria crit = session.createCriteria(UserProfile.class);
        crit.add(Restrictions.like("emailAddress", emailAddress));
        List<UserProfile> userList = crit.list();
        session.close();
        return !userList.isEmpty();
    }

    /**
     * Should return the safe, encrypted version of the password.
     * Throws exception if there is a database communication problem.
     * Throws exception if the user is not found.
     */
    @SuppressWarnings("unchecked")
    public String getPassword(int userID) throws DAOException{
        
        Session session = HibernateConnectionUtil.getSession();

        Criteria crit = session.createCriteria(UserPassword.class);
        crit.add(Restrictions.like("userID", userID));
        List<UserPassword> passList = crit.list();
        session.close();

        if (passList.isEmpty()) throw new DAOException(
                "getPassword: No password for account with ID " + userID);

        return passList.get(0).getPass();
    }
    
    /**
     * Should return the safe, encrypted version of the password.
     * Throws exception if there is a database communication problem.
     * Throws exception if the user is not found.
     */
    public String getPassword(String username) throws DAOException{
        return null;
    }

    /**
     * Returns a filled-out UserProfile object.
     * Throws exception if there is a database communication problem.
     * Throws exception if the user is not found.
     * @param userID
     * @return
     * @throws DAOException
     */
    public UserProfile getUserProfile(int userID) throws DAOException{
        return null;
    }

    /**
     * Returns a filled-out UserProfile object. 
     * Throws exception if there is a database communication problem. 
     * Throws exception if the user is not found.
     * 
     * @param username
     * @return
     * @throws DAOException
     */
    public UserProfile getUserProfile(String username) throws DAOException{
        return null;
    }

    /**
     * Returns a list of all employee profiles in the system.
     * Returns an empty list if there are no employees.
     * Throws exception if there is a database communication problem. 
     * Throws exception if the user is not found.
     * @return
     * @throws DAOException
     */
    public List<UserProfile> getAllEmployeeProfiles() throws DAOException{
        return null;
    }

    /**
     * Saves/writes the given UserProfile to the database.
     * If a matching user DOES exist, ID and Role will not be updated,
     * but all other properties will be.
     * If saving a new UserProfile, should use ID = -1. The system will automatically
     * generate a new ID and return it.
     * Throws exception if there is a database communication problem. 
     * 
     * @param up : profile to save
     * 
     * @return : The ID of the profile that was saved. If given a up with an ID, the given
     *      ID will be returned. If given a up with ID = -1, a new ID will be generated
     *      and returned.
     * 
     * @throws DAOException
     */
    public int saveUserProfile(UserProfile up) throws DAOException{
        return 0;
    }
}
