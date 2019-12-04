package ru.itpark.framework.servlet;

import ru.itpark.framework.container.Container;
import ru.itpark.framework.container.ContainerDefaultImpl;
import ru.itpark.framework.router.Router;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

public class FrontController extends HttpServlet {
    private Router router;

    @Override
    public void init() {
        final Container container = new ContainerDefaultImpl();
        final Map<Class, Object> components;
        try {
            components = container.init();
            router = (Router) components.values().stream()
                    .filter(o -> Arrays.asList(o.getClass().getInterfaces()).contains(Router.class))
                    .findFirst().orElseThrow(() -> new RuntimeException());
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            router.route(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

