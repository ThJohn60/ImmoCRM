package net.immocrm.domain;

import java.util.ArrayList;
import java.util.List;

import net.immocrm.db.PreferencesEntity;
import net.immocrm.db.dao.IntegrityConstraintViolationException;
import net.immocrm.db.dao.PreferencesDao;
import net.immocrm.domain.valid.DbRemoveException;

public class PreferenceManager {

	public static final String SKIN = "skin";

	public static final String APP_WIDTH = "appWidth";
	public static final String APP_HEIGHT = "appHeight";

	public static final String OVERVIEW_VIEW_DIVIDER_POS = "overviewDivider";


	public static Preference emptyDomain() {
		return new PreferencesDomain();
	}


	private final PreferencesDao dao;


	public PreferenceManager() {
		dao = new PreferencesDao();
	}

	public Preference fetchPreferences(String name) {
		PreferencesEntity entity = dao.fetchByName(name);
		if (entity != null) {
			return new PreferencesDomain(entity);
		}
		return null;
	}

	public Long fetchLong(String name) {
		String value = fetchString(name);
		if (value != null) {
			return Long.parseLong(value);
		}
		return null;
	}

	public String fetchString(String name) {
		PreferencesEntity entity = dao.fetchByName(name);
		if (entity != null) {
			return entity.getValue();
		}
		return null;
	}

	public void save(String name, Long value) {
		if (value == null) {
			save(name, (String)null);
		}
		save(name, value.toString());
	}

	public void save(String name, String value) {
		PreferencesEntity entity = dao.fetchByName(name);
		if (value == null) {
			if (entity != null) {
				delete(entity);
			}
		} else {
			save(name, value, entity);
		}
	}

	private void save(String name, String value, PreferencesEntity entity) {
		if (entity == null) {
			entity = new PreferencesEntity();
			entity.setName(name);
		}
		entity.setValue(value);
		try {
			dao.startTransaction();
			dao.save(entity);
			dao.commit();
		} catch (Exception e) {
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
		}
	}

	private void delete(PreferencesEntity entity) {
		try {
			dao.removeCommited(entity);
		} catch (IntegrityConstraintViolationException e) {
			throw new DbRemoveException(new PreferencesDomain(entity), "die Einstellung", e.getCause());
		}
	}

	public Preference fetchById(Integer id) {
		return new PreferencesDomain(dao.fetchById(id));
	}

	@SuppressWarnings("unused")
	private List<Preference> convert(List<PreferencesEntity> entities) {
		List<Preference> result = new ArrayList<>();
		for (PreferencesEntity e : entities) {
			result.add(new PreferencesDomain(e));
		}
		return result;
	}

}
