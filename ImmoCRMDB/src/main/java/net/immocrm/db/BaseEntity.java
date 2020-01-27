package net.immocrm.db;

import java.sql.Timestamp;

public interface BaseEntity {

	Integer getId();
	void setId(Integer id);

	boolean isNew();

	Timestamp getCreatedOn();

}