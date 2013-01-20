package de.esa.auth.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManager manager = buildManager();

	private static EntityManager buildManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager manager = factory.createEntityManager();
		return manager;
	}

	public static EntityManager getManager() {
		if (manager == null || !manager.isOpen()) {
			manager = buildManager();
		}
		return manager;
	}

	public static void shutdown() {
		// Close caches and connection pools
		manager.close();
		manager = null;
	}

}