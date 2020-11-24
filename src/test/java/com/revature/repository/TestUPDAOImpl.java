/**
 * This class contains unit tests for the UserProfileDAOImpl class.
 */
package com.revature.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hibernate.Transaction;

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
    public void testCheckExistsByID() throws DAOException, HibernateException{

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
    public void testCheckExistsByUsername() throws DAOException, HibernateException{

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
}
