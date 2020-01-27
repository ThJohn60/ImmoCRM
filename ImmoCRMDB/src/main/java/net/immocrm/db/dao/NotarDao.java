package net.immocrm.db.dao;

import net.immocrm.db.NotarEntity;

public class NotarDao extends AbstractDao<NotarEntity> {
	
	@Override
	protected Class<NotarEntity> getEntityClass() {
		return NotarEntity.class;
	}

}
