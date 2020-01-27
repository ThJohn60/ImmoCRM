package net.immocrm.db.dao;

import net.immocrm.db.MerkmalTypeEntity;

public class MerkmalTypeDao extends AbstractDao<MerkmalTypeEntity> {
	
	@Override
	protected Class<MerkmalTypeEntity> getEntityClass() {
		return MerkmalTypeEntity.class;
	}

}
