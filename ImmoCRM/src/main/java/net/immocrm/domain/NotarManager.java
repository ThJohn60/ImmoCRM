package net.immocrm.domain;

import net.immocrm.db.dao.IntegrityConstraintViolationException;
import net.immocrm.db.dao.NotarDao;
import net.immocrm.domain.valid.DbRemoveException;

public class NotarManager {

	private final NotarDao dao;


	public NotarManager() {
		this.dao = new NotarDao();
	}


	public void save(Notar n) {
		NotarDomain notar = (NotarDomain)n;
		notar.beforePersistence();
		dao.saveCommitted(notar.getEntity());
	}

	public void delete(Notar n) {
		try {
			NotarDomain notar = (NotarDomain)n;
			dao.removeCommited(notar.getEntity());
		} catch (IntegrityConstraintViolationException e) {
			throw new DbRemoveException(n, "der Notar", e.getCause());
		}
	}

}
