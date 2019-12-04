package ru.itpark.implementation.controller;

import lombok.RequiredArgsConstructor;
import ru.itpark.framework.annotation.Component;
import ru.itpark.implementation.service.AutoService;
import ru.itpark.implementation.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AutoController {
    private final AutoService autoService;
    private final FileService fileService;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List list = doSearch("");
            req.setAttribute("items", list);
            req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final String name = req.getParameter("name");
        final String description = req.getParameter("description");
        final Part part = req.getPart("image");
        final String image = fileService.writeFile(part);
        autoService.create(name, description, image);
        resp.sendRedirect("/");
    }

    public void getSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        final String name = req.getParameter("q");
        List<String> result = doSearch(name);
        req.setAttribute("items", result);
        req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
    }

    public void getImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getRequestURI() != null) {
            final String[] parts = req.getRequestURI().split("/");
            if (parts.length != 3) {
                throw new RuntimeException("Not found ");
            }
            fileService.readFile(parts[2], resp.getOutputStream());
        }
    }

    public List doSearch(String name) throws SQLException {
        if (name.isEmpty()) {
            return autoService.getAll();
        } else {
            return autoService.search(name);
        }
    }

}
