/**
 * Shamelessly taken from one of the provided class demos.
 */
package com.revature.repository.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnectionUtil {

	
    private static SessionFactory sf 
            = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	
	//get a Session from the SessionFactory Method
	public static Session getSession(){
		return sf.openSession();
	}
}
