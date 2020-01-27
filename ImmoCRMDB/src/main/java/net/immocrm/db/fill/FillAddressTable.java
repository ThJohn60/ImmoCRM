package net.immocrm.db.fill;

import net.immocrm.db.AddressEntity;
import net.immocrm.db.dao.AddressDao;
import net.immocrm.db.dao.EntityManagerProvider;

public class FillAddressTable {

	public static void main(String[] args) throws Exception {
		new EntityManagerProvider().connect();
		
		AddressDao dao = new AddressDao();
		
		dao.startTransaction();
		
		AddressEntity entity = new AddressEntity();
		entity.setId(-1);
		entity.setStreet("Blumenstraße 12");
		entity.setPostalCode("76133");
		entity.setCity("Karlsruhe");
		dao.save(entity);

		entity = new AddressEntity();
		entity.setId(-2);
		entity.setStreet("Bunsenstraße 41");
		entity.setPostalCode("76135");
		entity.setCity("Karlsruhe");
		dao.save(entity);

		entity = new AddressEntity();
		entity.setId(-3);
		entity.setStreet("Schulstraße 6");
		entity.setPostalCode("76870");
		entity.setCity("Kandel");
		dao.save(entity);
		
		entity = new AddressEntity();
		entity.setId(-4);
		entity.setStreet("Bahnhofstraße 9a");
		entity.setPostalCode("76646");
		entity.setCity("Bruchsal");
		dao.save(entity);

		entity = new AddressEntity();
		entity.setId(-5);
		entity.setStreet("Erbprinzenstraße 41");
		entity.setPostalCode("75175");
		entity.setCity("Pforzheim");
		dao.save(entity);

		entity = new AddressEntity();
		entity.setId(-6);
		entity.setStreet("Lichtentaler Straße 2b");
		entity.setPostalCode("76530");
		entity.setCity("Baden-Baden");
		dao.save(entity);

		dao.commit();
	}

}
