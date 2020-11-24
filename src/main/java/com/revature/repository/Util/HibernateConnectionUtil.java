/**
 * Shamelessly taken from one of the provided class demos.
 */
package com.revature.repository.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnectionUtil {

	// constants
	public static final String CONFIG_FILE_NAME = "hibernate.cfg.xml";
	public static final String TEST_CONFIG_FILE_NAME = "test_hibernate.cfg.xml";

	// class / static variables
	private static boolean isInitialized = false;
	private static boolean isTestMode = false;

    private static SessionFactory sf; 
	
	//get a Session from the SessionFactory Method
	public static Session getSession(){

		if (!isInitialized){ // figure out what kind to make

			String fileName;

			if (isTestMode) fileName = TEST_CONFIG_FILE_NAME;
			else fileName = CONFIG_FILE_NAME;

			sf = new Configuration().configure(fileName).buildSessionFactory();
			isInitialized = true;
		}

		return sf.openSession();
	}

	/**
	 * Switches to using the test configuration file. If starting in normal mode and there
	 * is already a SessionFactory initialized, it will be dropped.
	 * If already in testMode, does nothing.
	 */
	public void enterTestMode(){

		if (!isTestMode && isInitialized){
			sf.close();
			sf = null;
			isInitialized = false;
		}
		isTestMode = true;
	}

	/**
	 * Switches to using the normal configuration file. If starting in test mode and there
	 * is already a SessionFactory initialized, it will be dropped.
	 * If already in normal mode, does nothing.
	 */
	public void exitTestMode(){

		if (isTestMode && isInitialized){
			sf.close();
			sf = null;
			isInitialized = false;
		}
		isTestMode = false;
	}
}
