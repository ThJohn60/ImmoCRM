package net.immocrm.db.dao;

import net.immocrm.db.GeoInfoEntity;

public class GeoInfoDao extends AbstractDao<GeoInfoEntity> {

	@Override
	protected Class<GeoInfoEntity> getEntityClass() {
		return GeoInfoEntity.class;
	}

}
