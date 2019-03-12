package ace.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import ace.domain.UserRole;
import ace.server.UserRoleDAO;


public class UserRoleManager implements UserRoleDAO {
	String json;
	private static SessionFactory factory;

    @Override
	public UserRole getUserRoleByID(int ID){
    	
    	  factory = new Configuration().configure().buildSessionFactory();
	      Session session = factory.openSession();
	      Transaction trans = null;
	      UserRole userRole = null;
	      
	      try {
	    	 trans = session.beginTransaction();
	    	 
	    	 userRole =  (UserRole) session.get(UserRole.class, ID);
	         trans.commit();
	         
	      } catch (HibernateException e) {
	         if (trans!=null) trans.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	      return userRole;
    }
  
}
