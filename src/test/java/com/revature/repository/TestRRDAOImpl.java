/**
 * This class contains unit tests for the ReimbursementRequestDAOImpl class.
 */
package com.revature.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.repository.DAO.impl.ReimbursementRequestDAOImpl;
import com.revature.repository.DAO.interfaces.ReimbursementRequestDAO;
import com.revature.repository.Util.HibernateConnectionUtil;

public class TestRRDAOImpl {
    
    // class / static vars
    private ReimbursementRequestDAO rrdao;

    @Before
    public void setup(){
        HibernateConnectionUtil.enterTestMode();
        rrdao = new ReimbursementRequestDAOImpl();
    }

    @After
    public void cleanup(){
        HibernateConnectionUtil.exitTestMode();
        HibernateConnectionUtil.forceDropSessionFactory(); // unnecessary?
    }
}
