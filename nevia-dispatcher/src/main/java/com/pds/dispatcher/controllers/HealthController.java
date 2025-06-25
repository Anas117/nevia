package com.pds.dispatcher.controllers;

import com.pds.dispatcher.dto.StatusHealthDto;
import com.pds.dispatcher.services.HealthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.Optional;

@Controller
public class HealthController {

    private final HealthService healthService;

    private static final Logger log = LoggerFactory.getLogger(HealthController.class);


    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }


    @GetMapping("/checkHealth/{microservice}/{instance}")
    public ResponseEntity<StatusHealthDto> checkHealth(@NonNull @PathVariable("microservice") String microservice, @NonNull @PathVariable("instance") Integer instance) throws IOException {
        StatusHealthDto health = healthService.checkHealth(microservice, instance);

        log.info("Statut of the microservice " + microservice + " of the instance " + instance);
        return Optional.ofNullable(health)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
