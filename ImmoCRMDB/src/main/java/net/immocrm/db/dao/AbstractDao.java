package net.immocrm.db.dao;

import static net.immocrm.db.dao.EntityManagerProvider.getEntityManager;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.TypedQuery;

import net.immocrm.db.BaseEntity;

abstract class AbstractDao<E extends BaseEntity> implements BaseDao<E> {

	@Override
	public E fetchById(Integer id) {
		E entity = getEntityManager().find(getEntityClass(), id);
		return entity;
	}

	@Override
	public List<E> fetchAll() {
		TypedQuery<E> query = getEntityManager().createNamedQuery(getEntityName() + ".fetchAll", getEntityClass());
		List<E> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<E> search(String pattern) {
		TypedQuery<E> query = getEntityManager().createNamedQuery(getEntityName() + ".search", getEntityClass());
		query.setParameter("pattern", "%" + pattern.toLowerCase() + "%");
		List<E> resultList = query.getResultList();
		return resultList;
	}

	private String getEntityName() {
		String result = getEntityClass().getAnnotation(Entity.class).name();
		return result;
	}

	// for test only
	public void flush() {
		getEntityManager().flush();
	}


	protected abstract Class<E> getEntityClass();


	public void saveCommitted(E entity) {
		startTransaction();
		save(entity);
		commit();
	}

	public E save(E entity) {
		if (entity.isNew()) {
			getEntityManager().persist(entity);
			return entity;
		}
		return getEntityManager().merge(entity);
	}

	public E copy(E entity) {
		if (!entity.isNew()) {
			getEntityManager().detach(entity);
			entity.setId(null);
		}
		return save(entity);
	}


	@Override
	public void removeCommited(E entity) throws IntegrityConstraintViolationException {
		try {
			startTransaction();
			remove(entity);
			commit();
		} catch (Exception e) {
			rollback();
			if (DatabaseExceptionManager.isIntegrityConstraintViolation(e)) {
				throw new IntegrityConstraintViolationException(e);
			}
		}
	}

	public void remove(E entity) {
		if (entity.isNew()) {
			getEntityManager().detach(entity);
		} else {
			E toBeRemoved = fetchById(entity.getId());
			getEntityManager().remove(toBeRemoved);
		}
	}


	@Override
	public void startTransaction() {
		getEntityManager().getTransaction().begin();
	}

	@Override
	public void commit() {
		getEntityManager().getTransaction().commit();
	}

	@Override
	public void rollback() {
		try {
			if (getEntityManager().getTransaction().isActive()) {
				getEntityManager().getTransaction().rollback();
			}
		} catch (Exception e) {
			// ignore
		}
	}

}
