package ru.nexign.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nexign.crm.messaging.MessageProducer;
import ru.nexign.jpa.enums.ResponseStatus;
import ru.nexign.jpa.dto.request.body.DepositRequestBody;
import ru.nexign.jpa.dto.response.body.DepositResponseBody;
import ru.nexign.jpa.dto.response.body.ReportResponseBody;

import java.security.Principal;

@RestController
@RequestMapping(path = "/abonent")
public class AbonentController {
    private final MessageProducer sender;
    private final ObjectMapper mapper;

    @Autowired
    public AbonentController(MessageProducer sender) {
        this.sender = sender;
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @RequestMapping(
            method = RequestMethod.PATCH,
            path = "/pay"
    )
    public ResponseEntity<?> depositMoney(@RequestBody DepositRequestBody request) {
        var response = sender.send(request);

        if (response.getStatus().equals(ResponseStatus.SUCCESS)) {
            try {
                return ResponseEntity.ok(mapper.readValue(response.getMessage(), DepositResponseBody.class));
            } catch (JsonProcessingException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/report/{phoneNumber}"
    )
    public ResponseEntity<?> getLastReport(@PathVariable String phoneNumber, Principal principal) {
        var response = sender.send(phoneNumber, principal.getName());

        if (response.getStatus().equals(ResponseStatus.SUCCESS)) {
            try {
                return ResponseEntity.ok(mapper.readValue(response.getMessage(), ReportResponseBody.class));
            } catch (JsonProcessingException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body(response.getMessage());
        }
    }
}
