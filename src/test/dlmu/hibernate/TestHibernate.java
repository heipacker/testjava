package test.dlmu.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import test.dlmu.hibernate.model.Event;

public class TestHibernate {
	
	SessionFactory sessionFactory;
	
	Session session;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestHibernate tester = new TestHibernate();
		try {
			tester.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tester.save();
		tester.query();
		
		tester.close();
	}
	
	void query(){
		session.beginTransaction();
		List<Event> result = session.createQuery( "from Event" ).list();
		for (Event event : (List<Event>) result ) {
		    System.out.println("Event (" + event.getDate() + ") : " + event.getTitle());
		}
		session.getTransaction().commit();
	}
	
	void save(){
		session.beginTransaction();
		session.save(new Event("Our very first event!", new Date() ) );
		session.save(new Event("A follow up event", new Date() ) );
		session.getTransaction().commit();
	}
	
	void close(){
		session.close();
		sessionFactory.close();
	}
	
	@SuppressWarnings("deprecation")
	void setUp() throws Exception {
	    // A SessionFactory is set up once for an application
	    sessionFactory = new Configuration()
	            .configure() /*configures settings from hibernate.cfg.xml*/
	            .buildSessionFactory();
	    session = sessionFactory.openSession();
	}
}
