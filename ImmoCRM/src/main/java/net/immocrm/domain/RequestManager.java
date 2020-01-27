package net.immocrm.domain;

import net.immocrm.db.dao.IntegrityConstraintViolationException;
import net.immocrm.db.dao.RequestDao;
import net.immocrm.domain.valid.DbRemoveException;

public class RequestManager {

	private final RequestDao dao;


	public RequestManager() {
		dao = new RequestDao();
	}


	public void save(Request p) {
		RequestDomain request = (RequestDomain)p;
		try {
			dao.startTransaction();
			persist(request);
			dao.commit();
		} catch (Exception e) {
			RollbackHandler handler = new RollbackHandler(dao);
			handler.rollbackSave(e);
		}
	}

	void persist(RequestDomain request) {
		if (request.getPurchaser().isNew()) {// TODO check whether necessary
			PersonManager personMan = new PersonManager();
			personMan.persist(request.getPurchaser());
		}
		dao.save(request.getEntity());
	}

	public void delete(Request r) {
		try {
			RequestDomain request = (RequestDomain)r;
			dao.removeCommited(request.getEntity());
		} catch (IntegrityConstraintViolationException e) {
			throw new DbRemoveException(r, "die Anfrage", e.getCause());
		}
	}

}
