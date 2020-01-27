package net.immocrm.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class FindAllTest {

	public EntityManager connect() {
		EntityManagerFactory  emFactory = Persistence.createEntityManagerFactory("ImmoCRMDerbyDB");
		EntityManager entityManager = emFactory.createEntityManager();
		return entityManager;
	}

	public static void main(String[] args) throws Exception {
		FindAllTest elt = new FindAllTest();
		EntityManager em = elt.connect();
		long startMillis = System.currentTimeMillis();
//		TypedQuery<ImmobilieCategoryEntity> query = em.createNamedQuery("ImmobilieCategory.fetchAll", ImmobilieCategoryEntity.class);
//		List<ImmobilieCategoryEntity> resultList = query.getResultList();
//		for (ImmobilieCategoryEntity pt : resultList) {
//			System.out.println(pt.getId() + ", " + pt.getName() + ", " + pt.getCreatedOn());
//		}
		System.out.println("Runtime: " + (System.currentTimeMillis() - startMillis));
		em.close();
	}

}
