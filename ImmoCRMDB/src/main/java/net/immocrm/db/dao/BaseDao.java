package net.immocrm.db.dao;

import java.util.List;

import net.immocrm.db.BaseEntity;

public interface BaseDao<E extends BaseEntity> {

	E fetchById(Integer id);
	
	List<E> fetchAll();
	List<E> search(String pattern);

	void removeCommited(E entity) throws IntegrityConstraintViolationException;

	void startTransaction();
	void commit();
	void rollback();

}