package ru.nexign.brt.activemq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.nexign.brt.service.ClientService;
import ru.nexign.brt.service.ReportService;
import ru.nexign.jpa.dto.ClientDto;
import ru.nexign.jpa.enums.ResponseStatus;
import ru.nexign.jpa.request.Request;
import ru.nexign.jpa.request.body.DepositRequestBody;
import ru.nexign.jpa.request.body.TariffRequestBody;
import ru.nexign.jpa.response.Response;

@Service
@Slf4j
public class CrudMessageConsumer {
    private final ClientService clientService;
    private final ReportService reportService;
    private final ObjectMapper mapper;

    @Autowired
    public CrudMessageConsumer(ClientService clientService, ReportService reportService) {
        this.clientService = clientService;
        this.reportService = reportService;
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @JmsListener(destination = "${deposit.mq}")
    public Response receiveDepositRequest(@Payload Request request) {
        log.info("Request received: {}", request.getMessage());

        try {
            var response = clientService.depositMoney(mapper.readValue(request.getMessage(), DepositRequestBody.class));
            return new Response(mapper.writeValueAsString(response), ResponseStatus.SUCCESS);
        } catch (JsonProcessingException | RuntimeException e) {
            return new Response("Brt service: " + e.getMessage(), ResponseStatus.ERROR);
        }
    }

    @JmsListener(destination = "${tariff.mq}")
    public Response receiveTariffRequest(@Payload Request request) {
        log.info("Request received: {}", request.getMessage());

        try {
            var response = clientService.changeTariff(mapper.readValue(request.getMessage(), TariffRequestBody.class));
            return new Response(mapper.writeValueAsString(response), ResponseStatus.SUCCESS);
        } catch (JsonProcessingException | RuntimeException e) {
            return new Response("Brt service: " + e.getMessage(), ResponseStatus.ERROR);
        }
    }

    @JmsListener(destination = "${client.mq}")
    public Response receiveClientDto(@Payload Request request) {
        log.info("Request received: {}", request.getMessage());

        try {
            var response = clientService.createClient(mapper.readValue(request.getMessage(), ClientDto.class));

            return new Response(mapper.writeValueAsString(response), ResponseStatus.SUCCESS);
        } catch (JsonProcessingException | RuntimeException e) {
            return new Response("Brt service: " + e.getMessage(), ResponseStatus.ERROR);
        }
    }

    @JmsListener(destination = "${client.report.mq}")
    public Response receivePhoneNumber(@Payload Request request) {
        log.info("Request received: {}", request.getMessage());

        try {
            var response = reportService.getLastReport(request.getMessage());

            return new Response(mapper.writeValueAsString(response), ResponseStatus.SUCCESS);
        } catch (JsonProcessingException | RuntimeException e) {
            return new Response("Brt service: " + e.getMessage(), ResponseStatus.ERROR);
        }
    }
}
