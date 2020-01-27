package net.immocrm.db.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.eclipse.persistence.exceptions.DatabaseException;

public class EntityManagerProvider {

	private static final String DERBY_JDBC_EMBEDDED_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String PERSISTENCE_UNIT = "ImmoCRMDerbyDB";

	private static EntityManagerProvider me;

	static EntityManager getEntityManager() {
		return me.entityManager;
	}

	private EntityManager entityManager;
	private EntityManagerFactory emFactory;


	public EntityManagerProvider() {
		EntityManagerProvider.me = this;
	}


	public void connect() throws DbConnectException {
		try {
			createEntityManager();
		} catch (PersistenceException e) {
			DatabaseException cause = (DatabaseException) e.getCause();
			if (cause.getErrorCode() == DatabaseException.SQL_EXCEPTION) {
				throw new DbAlreadyOpenException();
			}
			throw new DbConnectException(cause.getMessage());
		}
	}

	public void shutdown() {
		if (entityManager.isOpen()) {
			entityManager.close();
			emFactory.close();
		}
	}

	private void createEntityManager() {
		emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, getPersistenceProperties());
		entityManager = emFactory.createEntityManager();
	}

	private Map<Object, Object> getPersistenceProperties() {
		Map<Object, Object> properties = new HashMap<>();
		properties.put("javax.persistence.jdbc.driver", DERBY_JDBC_EMBEDDED_DRIVER);
		properties.put("javax.persistence.jdbc.url", getUrl());
		return properties;
	}

	private String getUrl() {
		String userDir = System.getProperty("user.dir");
		StringBuilder result = new StringBuilder().append("jdbc:derby:").append(userDir);
		if (userDir.contains("workspace")) {
			result.append("/../ImmoCRMDerbyDB/db/ImmoDB");
		} else {
			result.append("/ImmoCRMDerbyDB/db/ImmoDB");
		}
		return result.toString();
	}

}

