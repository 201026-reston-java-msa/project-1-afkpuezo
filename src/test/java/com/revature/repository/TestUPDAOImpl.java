/**
 * This class contains unit tests for the UserProfileDAOImpl class.
 */
package com.revature.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.Transaction;

import com.revature.model.UserPassword;
import com.revature.model.UserProfile;
import com.revature.model.UserProfile.UserRole;
import com.revature.repository.DAO.exceptions.DAOException;
import com.revature.repository.DAO.impl.UserProfileDAOImpl;
import com.revature.repository.DAO.interfaces.UserProfileDAO;
import com.revature.repository.Util.HibernateConnectionUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUPDAOImpl {
    
    // class / static vars
    private UserProfileDAO updao;

    @Before
    public void setup(){
        HibernateConnectionUtil.enterTestMode();
        updao = new UserProfileDAOImpl();
    }

    @After
    public void cleanup(){
        HibernateConnectionUtil.exitTestMode();
        HibernateConnectionUtil.forceDropSessionFactory(); // unnecessary?
    }

    @Test
    public void testCheckExistsByID() throws DAOException{

        int userID = 999;
        assertFalse(updao.checkExists(userID));
        // now put one in there
        Session session = HibernateConnectionUtil.getSession();
        UserProfile up = new UserProfile(userID, UserRole.EMPLOYEE);
        Transaction tx = session.beginTransaction();
        session.save(up);
        tx.commit();
        userID = up.getID();
        session.evict(up);

        session.close();
        assertTrue(updao.checkExists(userID));
    }

    @Test
    public void testCheckExistsByUsername() throws DAOException{

        String username = "not_found";
        assertFalse(updao.checkExists(username));

        Session session = HibernateConnectionUtil.getSession();
        UserProfile up = new UserProfile(-1, UserRole.EMPLOYEE);
        username = "found";
        up.setUsername(username);
        Transaction tx = session.beginTransaction();
        session.save(up);
        tx.commit();
        session.evict(up);

        session.close();
        assertTrue(updao.checkExists(username));
    }

    @Test
    public void testCheckExistsByEmail() throws DAOException{

        String emailAddress = "not_found";
        assertFalse(updao.checkExistsEmail(emailAddress));

        Session session = HibernateConnectionUtil.getSession();
        UserProfile up = new UserProfile(-1, UserRole.EMPLOYEE);
        emailAddress = "found";
        up.setEmailAddress(emailAddress);
        Transaction tx = session.beginTransaction();
        session.save(up);
        tx.commit();
        session.evict(up);

        session.close();
        assertTrue(updao.checkExistsEmail(emailAddress));
    }

    @Test
    public void testGetUserProfile() throws DAOException{

        // should be null if no user found
        UserProfile up;
        Session session;
        String username = "username";

        session = HibernateConnectionUtil.getSession();
        up = (UserProfile) session.get(UserProfile.class, 1);
        assertNull(up);

        // now insert a user
        UserProfile inserted = new UserProfile(-1, UserRole.EMPLOYEE);
        inserted.setUsername(username);
        Transaction tx = session.beginTransaction();
        session.save(inserted);
        tx.commit();
        int userID = inserted.getID();
        session.evict(inserted);
        UserProfile debugUser = (UserProfile) session.get(UserProfile.class, userID);
        session.close();
        System.out.println("DEBUG: userID is: " + userID);
        System.out.println("DEBUG: debugUser is null? : " + (debugUser == null));

        // see if we can find it
        up = updao.getUserProfile(userID);
        assertNotNull(up);
        assertEquals(userID, up.getID());
        assertEquals(username, up.getUsername());
    }

    /**
     * This should test both by ID and username
     * 
     * @throws DAOException
     * @throws HibernateException
     */
    @Test
    public void testGetPassword() throws DAOException, HibernateException{

        // create a user with a password
        Session session = HibernateConnectionUtil.getSession();
        UserProfile up = new UserProfile(-1, UserRole.EMPLOYEE);
        String username = "username";
        up.setUsername(username);
        Transaction tx = session.beginTransaction();
        session.save(up);
        tx.commit();
        session.evict(up);
        int userID = up.getID();
        String password = "password";
        UserPassword uPass = new UserPassword();
        uPass.setUser(up);
        uPass.setPass(password);
        tx = session.beginTransaction();
        session.save(uPass);
        tx.commit();
        session.evict(uPass);

        // now see if we can get it
        String foundPass = updao.getPassword(userID);
        assertEquals(password, foundPass);
    }


}
