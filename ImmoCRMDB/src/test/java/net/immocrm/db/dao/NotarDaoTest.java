package net.immocrm.db.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.immocrm.db.NotarEntity;

public class NotarDaoTest {

	private NotarDao notarDao;

//	@Before
	public void setUp() throws Exception {
		notarDao = new NotarDao();
	}

//	@Test
	public void testFetchAll() {
		List<NotarEntity> result = notarDao.fetchAll();
		assertEquals(9, result.size());
	}

//	@Test
	public void testSearchByName() {
		List<NotarEntity> result = notarDao.search("kand");
		assertEquals(4, result.size());
	}

}
