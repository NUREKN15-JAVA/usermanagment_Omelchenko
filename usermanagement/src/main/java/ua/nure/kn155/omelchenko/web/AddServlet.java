package ua.nure.kn155.omelchenko.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn155.omelchenko.User;
import ua.nure.kn155.omelchenko.db.DaoFactory;
import ua.nure.kn155.omelchenko.db.DatabaseException;


/**
 * Servlet for adding a user's entry to the database
 */
public class AddServlet extends EditServlet {
	@Override
	protected void processUser(User user) {
		try {
			DaoFactory.getInstance().getUserDao().create(user);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.getRequestDispatcher("/add.jsp").forward(req, resp);
			return;
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
