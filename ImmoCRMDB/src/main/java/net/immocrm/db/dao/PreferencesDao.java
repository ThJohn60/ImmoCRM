package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import javax.persistence.NoResultException;

import net.immocrm.db.PreferencesEntity;

public class PreferencesDao extends AbstractDao<PreferencesEntity> {
	
	public PreferencesEntity fetchByName(String name) {
		try {
			PreferencesEntity result = getEntityManager()
					.createNamedQuery("Preferences.fetchByName", PreferencesEntity.class)
					.setParameter("name", name)
					.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	protected Class<PreferencesEntity> getEntityClass() {
		return PreferencesEntity.class;
	}

}
