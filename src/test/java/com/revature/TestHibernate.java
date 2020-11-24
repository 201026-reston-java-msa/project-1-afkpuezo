/**
 * This class tests to make sure that I've set up hibernate properly.
 */
package com.revature;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.revature.repository.Util.HibernateConnectionUtil;

import org.hibernate.Session;
import org.junit.Test;

public class TestHibernate {
    
    @Test
    public void testConnection(){
        Session session = HibernateConnectionUtil.getSession();
        assertTrue(session.isConnected());
    }
}
