package ua.nure.kn155.omelchenko.web;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

import ua.nure.kn155.omelchenko.User;

public class DetailsServletTest extends MockServletTestCase {
	private static final long ID = 1000L;
	private static final String USER_LASTNAME = "Watson";
	private static final String USER_NAME = "Jhon";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createServlet(DetailsServlet.class);
	}

	@Test
	public void testDetails() {
		Date date = new Date();
		User user = new User( ID ,USER_NAME, USER_LASTNAME, date);
		addRequestParameter("id", "1000");
		addRequestParameter("firstName", USER_NAME);
		addRequestParameter("lastName", USER_LASTNAME);
		addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(date));
		addRequestParameter("okButton", "Ok");
		doPost();
	}
}