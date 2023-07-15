package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dao.UserDao;
import ru.otus.model.User;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;


public class ClientsApiServlet extends HttpServlet {

    private static final String CLIENTS_LIST_PAGE_TEMPLATE = "clientList.html";
    private static final String TEMPLATE_ATTR_CLIENT = "clientList";
    private final DBServiceClient dbServiceClient;
    private final TemplateProcessor templateProcessor;

    public ClientsApiServlet(DBServiceClient dbServiceClient,
                             TemplateProcessor templateProcessor) {
        this.dbServiceClient = dbServiceClient;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        var clientList = dbServiceClient.findAll();
        paramsMap.put(TEMPLATE_ATTR_CLIENT, clientList);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_LIST_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("client_name");
        String address = request.getParameter("client_address");
        String phone = request.getParameter("client_phone");

        var client = new Client(null, name, new Address(null, address),
                List.of(new Phone(null, phone)));
        dbServiceClient.saveClient(client);

        response.setContentType("text/html");
        response.getWriter().println("<p>Клиент добавлен<p>");

    }

}
