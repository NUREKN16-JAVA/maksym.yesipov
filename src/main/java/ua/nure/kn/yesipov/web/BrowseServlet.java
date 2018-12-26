package main.java.ua.nure.kn.yesipov.web;

import main.java.ua.nure.kn.yesipov.User;
import main.java.ua.nure.kn.yesipov.database.DaoFactory;
import main.java.ua.nure.kn.yesipov.database.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class BrowseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (nonNull(req.getParameter("addButton"))) {
            add(req, resp);
        } else if (nonNull(req.getParameter("editButton"))) {
            edit(req, resp);
        } else if (nonNull(req.getParameter("deleteButton"))) {
            delete(req, resp);
        } else if (nonNull(req.getParameter("detailsButton"))) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Collection<User> users = DaoFactory.getInstance().getUserDao().findAll();
            req.getSession(true).setAttribute("users", users);
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
        } catch (DatabaseException | IOException e) {
            throw new ServletException(e);
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");

        if (isNull(idStr) || idStr.isEmpty()) {
            req.setAttribute("error", "You must select a user");
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(new Long(idStr));
            req.getSession().setAttribute("user", user);
        } catch (DatabaseException e) {
            req.setAttribute("error", "ERROR: " + e.toString());
            req.getRequestDispatcher("/browse.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/edit").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add").forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = getIdString(req, resp);

        if (Objects.isNull(idString)) {
            return;
        }
        try {
            User user = DaoFactory.getInstance().getUserDao().find(Long.parseLong(idString));
            DaoFactory.getInstance().getUserDao().delete(user);
        } catch (DatabaseException e) {
            redirectBackWithError(req, resp, "Error :" + e.toString());
            return;
        }
        resp.sendRedirect("/browse");
    }

    private void redirectBackWithError(HttpServletRequest req, HttpServletResponse resp, String s) throws ServletException, IOException {
        req.setAttribute("error", s);
        req.getRequestDispatcher("/browse").forward(req, resp);
    }

    private void putUserToSesseonAndRedirect(HttpServletRequest req, HttpServletResponse resp, String pathToRedirect) throws ServletException, IOException {
        String idString = getIdString(req, resp);

        if (Objects.isNull(idString)) {
            return;
        }

        try {
            User user = DaoFactory.getInstance().getUserDao().find(Long.parseLong(idString));
            req.getSession(true).setAttribute("user", user);
        } catch (Exception e) {
            redirectBackWithError(req, resp, "Error :" + e.toString());
            return;
        }
        req.getRequestDispatcher(pathToRedirect).forward(req, resp);
    }

    private String getIdString(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idString = req.getParameter("id");

        if (Objects.isNull(idString) || idString.trim().isEmpty()) {
            redirectBackWithError(req, resp, "You should choose user");
            return null;
        }
        return idString;
    }

    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        putUserToSesseonAndRedirect(req, resp, "/details");
    }
}
