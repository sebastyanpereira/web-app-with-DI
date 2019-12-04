package ru.itpark.implementation.router;

import lombok.RequiredArgsConstructor;
import ru.itpark.framework.annotation.Component;
import ru.itpark.implementation.controller.AutoController;
import ru.itpark.framework.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class RouterImpl implements Router {
    private final AutoController autoController;

    @Override
    public void route(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String requestUri = request.getRequestURI();
        if (requestUri.equals("/")) {
            String method = request.getMethod().toUpperCase();
            switch (method) {
                case "GET":
                    autoController.doGet(request, response);
                    break;
                case "POST":
                    autoController.doPost(request, response);
                    break;
            }
        } else if (requestUri.equals("/search")) {
            autoController.getSearch(request, response);
        } else if (requestUri.startsWith("/images")) {
            autoController.getImage(request, response);
        }
    }
}
