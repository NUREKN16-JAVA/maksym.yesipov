package main.java.ua.nure.kn.yesipov.web;

import main.java.ua.nure.kn.yesipov.User;
import main.java.ua.nure.kn.yesipov.database.DaoFactory;
import main.java.ua.nure.kn.yesipov.database.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends EditServlet {

    @Override
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().create(user);
    }

    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }
}
