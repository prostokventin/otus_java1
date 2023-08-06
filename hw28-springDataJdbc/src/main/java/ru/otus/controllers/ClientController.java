package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.model.*;
import ru.otus.service.DBServiceClient;


import java.util.List;
import java.util.Set;

@Controller
public class ClientController {

    private final DBServiceClient dbServiceClient;

    public ClientController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping("/")
    public String clients(Model model) {
        List<Client> clients = dbServiceClient.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(String name, String street, String number) {
        dbServiceClient.saveClient(new Client(name, new Address(street), Set.of(new Phone(number))));
        return new RedirectView("/", true);
    }

}
