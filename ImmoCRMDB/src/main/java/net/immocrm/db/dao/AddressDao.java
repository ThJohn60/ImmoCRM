package net.immocrm.db.dao;

import net.immocrm.db.AddressEntity;

public class AddressDao extends AbstractDao<AddressEntity> {

	@Override
	protected Class<AddressEntity> getEntityClass() {
		return AddressEntity.class;
	}

}
