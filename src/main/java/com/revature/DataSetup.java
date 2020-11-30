/**
 * This class is a (very clumsy) solution for filling the database with a starting set of 
 * data for the purpose of demonstration.
 * 
 * Also, by the specification, there is no way to register user accounts through the 
 * website, so I suppose this is the only way of creating new users...
 * 
 * This will never be called in ordinary operation, only manually triggered by me.
 * Hibernate has a built-in way to accomplish this sort of thing, but I find this easier
 * for now.
 * 
 * It has a failsafe where it will not add anything if it detects that the databse is
 * already populated.
 */
package com.revature;

import com.revature.model.UserPassword;
import com.revature.model.UserProfile;
import com.revature.model.UserProfile.UserRole;
import com.revature.repository.DAO.exceptions.DAOException;
import com.revature.repository.DAO.impl.UserProfileDAOImpl;
import com.revature.repository.DAO.interfaces.UserProfileDAO;
import com.revature.repository.Util.HibernateConnectionUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DataSetup {
   
    public static void main (String[] args) throws DAOException{

        // make the DAOs - skip the service layer
        UserProfileDAO updao = new UserProfileDAOImpl();
        //ReimbursementRequestDAO rrdao = new ReimbursementRequestDAOImpl();

        // check to make sure it's okay to proceed
        if (updao.checkExists(1)) return;

        // prepare hibernate vars/references
        Session session = HibernateConnectionUtil.getSession();
        Transaction tx;

        // create and save the actual objects/data

        // the first manager account
        UserProfile man0 = new UserProfile();
        man0.setID(-1);
        man0.setRole(UserRole.MANAGER);
        man0.setUsername("admin");
        man0.setFirstName("Adam");
        man0.setLastName("Strator");
        man0.setEmailAddress("admin@ers.com");
        tx = session.beginTransaction();
        session.save(man0);
        tx.commit();
        session.evict(man0);

        UserPassword man0Pass = new UserPassword();
        man0Pass.setUser(man0);
        man0Pass.setPass("admin");
        tx = session.beginTransaction();
        session.save(man0Pass);
        tx.commit();
        session.evict(man0Pass);

        // the second manager account
        UserProfile man1 = new UserProfile();
        man1.setID(-1);
        man1.setRole(UserRole.MANAGER);
        man1.setUsername("manager");
        man1.setFirstName("Mandy");
        man1.setLastName("Baus");
        man1.setEmailAddress("mandy@ers.com");
        tx = session.beginTransaction();
        session.save(man1);
        tx.commit();
        session.evict(man1);

        UserPassword man1Pass = new UserPassword();
        man1Pass.setUser(man1);
        man1Pass.setPass("manager");
        tx = session.beginTransaction();
        session.save(man1Pass);
        tx.commit();
        session.evict(man1Pass);

        // the first employee acount
        UserProfile empl0 = new UserProfile();
        empl0.setID(-1);
        empl0.setRole(UserRole.EMPLOYEE);
        empl0.setUsername("user");
        empl0.setFirstName("Eustace");
        empl0.setLastName("Guy");
        empl0.setEmailAddress("eustace@ers.com");
        tx = session.beginTransaction();
        session.save(empl0);
        tx.commit();
        session.evict(empl0);

        UserPassword empl0Pass = new UserPassword();
        empl0Pass.setUser(empl0);
        empl0Pass.setPass("password");
        tx = session.beginTransaction();
        session.save(empl0Pass);
        tx.commit();
        session.evict(empl0Pass);

        session.close();
    }
}
