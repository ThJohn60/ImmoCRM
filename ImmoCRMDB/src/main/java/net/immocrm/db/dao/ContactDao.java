package net.immocrm.db.dao;

import net.immocrm.db.ContactEntity;

public class ContactDao extends AbstractDao<ContactEntity> {

	@Override
	protected Class<ContactEntity> getEntityClass() {
		return ContactEntity.class;
	}

}
