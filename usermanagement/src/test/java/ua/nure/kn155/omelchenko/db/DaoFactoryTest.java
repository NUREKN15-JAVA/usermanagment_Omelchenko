package ua.nure.kn155.omelchenko.db;

import org.junit.Test;

import junit.framework.TestCase;

public class DaoFactoryTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testGetUserDao() {
		DaoFactory daoFactory = DaoFactory.getInstance();
		assertNotNull("DaoFactory instance is null",daoFactory);
		UserDao userDao = daoFactory.getUserDao();
		assertNotNull("UserDao instance is null", userDao);
	}
}
