package net.immocrm.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EclipsLinkTest {

	public EntityManager connect() {
		EntityManagerFactory  emFactory = Persistence.createEntityManagerFactory("ImmoCRMDerbyDB");
		EntityManager entityManager = emFactory.createEntityManager();
		return entityManager;
	}

	public static void main(String[] args) throws Exception {
		long startMillis = System.currentTimeMillis();
		EclipsLinkTest elt = new EclipsLinkTest();
		EntityManager em = elt.connect();
		ImmobilieEntity pt = em.find(ImmobilieEntity.class, 1);
		System.out.println("Id: " + pt.getId());
		em.close();
		System.out.println("Runtime: " + (System.currentTimeMillis() - startMillis));
	}

}
