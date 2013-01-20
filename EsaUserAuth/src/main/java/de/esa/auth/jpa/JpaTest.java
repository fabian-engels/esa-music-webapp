package de.esa.auth.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import de.esa.auth.domain.UserObject;
//import de.esa.auth.domain.UserObject;

public class JpaTest {

	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}
	/**
	 * @param args
	 * @throws IllegalNameException 
	 */
	public static void main(String[] args) throws IllegalNameException {
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager manager =  JPAUtil.getManager(); //factory.createEntityManager(); //

		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			test.createUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();

		test.listUser();		
		
		System.out.println(".. done");
		manager.close();
	}


	private void createUser() {
		int numOfEmployees = manager.createQuery("Select a From UserObject a", UserObject.class).getResultList().size();
		if (numOfEmployees == 0) {

			manager.persist(new UserObject("aaa1Susi", "pw1Susi"));
			manager.persist(new UserObject("aaa2Susi", "pw2Susi"));

		}
	}
	
	private void listUser() {
		List<UserObject> resultList = manager.createQuery("Select a From UserObject a", UserObject.class).getResultList();
		System.out.println("num of employess:" + resultList.size());
		for (UserObject next : resultList) {
			System.out.println("next user: " + next);
		}
	}


	

	private static UserObject getUserByName(final String name) {

		final List<UserObject> resultList = JPAUtil.getManager().createQuery("Select a From UserObject a", UserObject.class).getResultList();
		
		for (UserObject next : resultList) {
			if (next.getName().equals(name)) {
				return next;
			}
		}
		return null;
	}


}
