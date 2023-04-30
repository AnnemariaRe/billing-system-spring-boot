package ru.nexign.cdr.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nexign.cdr.generator.client.ClientGenerator;
import ru.nexign.jpa.dao.ClientsRepository;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class ClientsService {
    private final ClientsRepository clientRepo;
    private final ClientGenerator generator;

    @Autowired
    public ClientsService(ClientsRepository clientRepo, ClientGenerator generator) {
        this.clientRepo = clientRepo;
        this.generator = generator;
    }

    @PostConstruct
    public void init()  {
        var clients = generator.createClients();
        clientRepo.saveAll(clients);
        log.info("Clients are generated successfully");
    }
}
