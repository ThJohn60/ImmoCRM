package net.immocrm.domain;

import net.immocrm.db.dao.BaseDao;
import net.immocrm.domain.valid.DbSaveException;

class RollbackHandler {

	protected final BaseDao<?> dao;


	RollbackHandler(BaseDao<?> dao) {
		this.dao = dao;
	}

	void rollbackSave(Exception e) {
		dao.rollback();
		if (Constants.DEBUG_MODE) {
			e.printStackTrace();
		}
		throw new DbSaveException(e);
	}

}
