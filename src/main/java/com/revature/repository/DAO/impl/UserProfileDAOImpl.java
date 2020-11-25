/**
 * This class provides an implementation of DAO functionality for UserProfiles.
 * 
 * @author Andrew Curry
 */
package com.revature.repository.DAO.impl;

import java.util.List;

import com.revature.model.UserPassword;
import com.revature.model.UserProfile;
import com.revature.repository.DAO.exceptions.DAOException;
import com.revature.repository.DAO.interfaces.UserProfileDAO;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        crit.add(Restrictions.eq("username", username));
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
        crit.add(Restrictions.eq("emailAddress", emailAddress));
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
        //UserProfile user = getUserProfile(userID);
        crit.add(Restrictions.eq("user.ID", userID));
        List<UserPassword> passList = crit.list();

        if (passList.isEmpty()){
            session.close();
            throw new DAOException(  
                "getPassword: No password for account with ID " + userID);
        } 

        UserPassword uPass = passList.get(0);
        session.evict(uPass);
        session.close();
        return uPass.getPass();
    }
    
    /**
     * Should return the safe, encrypted version of the password.
     * Throws exception if there is a database communication problem.
     * Throws exception if the user is not found.
     */
    @SuppressWarnings("unchecked")
    public String getPassword(String username) throws DAOException{
        
        Session session = HibernateConnectionUtil.getSession();

        UserProfile user = getUserProfile(username); // a little hacky but it works
        int userID = user.getID();
        Criteria crit = session.createCriteria(UserPassword.class);
        crit.add(Restrictions.eq("user.id", userID));
        List<UserPassword> passList = crit.list();

        if (passList.isEmpty()){
            session.close();
            throw new DAOException(  
                String.format("getPassword: No password for account with username '%s'",
                username));
        } 

        UserPassword uPass = passList.get(0);
        session.evict(uPass);
        session.close();
        return uPass.getPass();
    }

    /**
     * Returns a filled-out UserProfile object.
     * Throws exception if there is a database communication problem.
     * Returns null if the user was not found.
     * @param userID
     * @return
     * @throws DAOException
     */
    public UserProfile getUserProfile(int userID) throws DAOException{

        Session session = HibernateConnectionUtil.getSession();
        UserProfile up = (UserProfile) session.get(UserProfile.class, userID);
        session.evict(up);
        session.close();
        return up;
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
    @SuppressWarnings("unchecked")
    public UserProfile getUserProfile(String username) throws DAOException{
        
        Session session = HibernateConnectionUtil.getSession();

        Criteria crit = session.createCriteria(UserProfile.class);
        crit.add(Restrictions.eq("username", username));
        List<UserProfile> userList = crit.list();
        
        if (userList.isEmpty()) {
            session.close();
            throw new DAOException(
                String.format(
                            "getUserProfile: No password for account with username '%s'", 
                            username));
        }
        
        UserProfile up = userList.get(0);
        session.evict(up);
        session.close();
        return up;
    }

    /**
     * Returns a list of all employee profiles in the system.
     * Returns an empty list if there are no employees.
     * Throws exception if there is a database communication problem. 
     * @return
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
    public List<UserProfile> getAllEmployeeProfiles() throws DAOException{
        
        Session session = HibernateConnectionUtil.getSession();

        Criteria crit = session.createCriteria(UserProfile.class);
        // I thought I would have to cast the enum to string, but I don't. Neat.
        crit.add(Restrictions.eq("role", UserProfile.UserRole.EMPLOYEE));
        List<UserProfile> userList = crit.list();
        
        // ? is this actually necessary?
        for (UserProfile user : userList){
            session.evict(user);
        }

        session.close();
        return userList;
    }

    /**
     * Saves/writes the given UserProfile to the database.
     * If a matching user DOES exist, the entry will be updated
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


        Session session = HibernateConnectionUtil.getSession();
        Transaction tx = session.beginTransaction();
        if (up.getID() == -1) session.save(up);
        else session.saveOrUpdate(up);
        tx.commit();
        session.evict(up);
        session.close();
        return up.getID();
    }
}
