package pl.t32.dvdrental.web.controller;

import pl.t32.dvdrental.ejb.DvdRepository;
import pl.t32.dvdrental.ejb.DvdRepositoryDao;
import pl.t32.dvdrental.model.Dvd;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "DvdController", urlPatterns = {"/dvd/list", "/dvd/add", "/dvd/remove/*"})
public class DvdController extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(DvdController.class.getName());

    @EJB
    private DvdRepositoryDao dvdRepo;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/dvd/list":
                handleDvdList(req, resp);
                break;
            case "/dvd/add":
                handleDvdAdd(req, resp);
                break;
            case "/dvd/remove":
                handleDvdRemove(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/dvd/add")) {
            handleDvdAddPost(req, resp);
        }
    }

    private void handleDvdList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Dvd> dvds = dvdRepo.findAll();
        req.setAttribute("dvdList", dvds);
        req.getRequestDispatcher("/WEB-INF/views/dvd/dvd_list.jsp").forward(req, resp);
    }

    private void handleDvdAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/dvd/dvd_add.jsp").forward(req, resp);
    }

    private void handleDvdAddPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String price = req.getParameter("price");
        String description = req.getParameter("description");
        if (!validate(title, author, description, price)) {
            req.getRequestDispatcher("/WEB-INF/views/dvd/dvd_list.jsp").forward(req, resp);
            return;
        }

        Dvd dvd = new Dvd();
        dvd.setAuthor(author);
        dvd.setDescription(description);
        dvd.setTitle(title);
        try {
            dvd.setPrice(convertPrice(price));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "", e);
        }
        dvdRepo.save(dvd);
        resp.sendRedirect(req.getContextPath() + "/dvd/list");
    }

    private BigDecimal convertPrice(String price) throws NumberFormatException {
        if (price == null || price.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(price);
    }

    private boolean validate(String title, String author, String description, String price) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }
        if (author == null || author.trim().isEmpty()) {
            return false;
        }
        if (description == null || description.trim().isEmpty()) {
            return false;
        }
        try {
            convertPrice(price);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void handleDvdRemove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        Long id = convertId(pathInfo);
        if (id != null) {
            try {
                dvdRepo.remove(id);
            } catch (Throwable t) {
                LOGGER.log(Level.SEVERE, "Book remove failed", t);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/dvd/list");
    }

    private Long convertId(String s) {
        if (s == null || !s.startsWith("/")) {
            return null;
        }
        try {
            return Long.parseLong(s.substring(1));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
