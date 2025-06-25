package com.pds.dispatcher.services;

import com.pds.dispatcher.dto.StatusHealthDto;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

@Service
public class HealthService {

    private final WebClient webClient;
    private final Environment environment;
    private final Logger logger = Logger.getLogger(HealthService.class.getName());

    public HealthService(WebClient webClient, Environment environment) {
        this.webClient = webClient;
        this.environment = environment;
    }

    public StatusHealthDto checkHealth(String nameMicroservice, Integer instance) throws IOException {

        StringBuilder uri = new StringBuilder("http://");
        String[] activeProfiles = environment.getActiveProfiles();
        String context = activeProfiles[0];

        switch (context) {
            case "prod" -> {
                uri.append("192.168.1.");
                getLastDigitAndPort(nameMicroservice, instance, uri);
            }
            case "int" -> {
                uri.append("192.168.2.");
                getLastDigitAndPort(nameMicroservice, instance, uri);
            }
            case "localhost" -> {
                uri.append("localhost:");
                switch (instance) {
                    case 1 -> uri.append("8085");
                    case 2 -> uri.append("8084");
                }
            }
        }


        uri.append("/actuator/health");

        URI uriComponent = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .build().toUri();
        try {
            return webClient.get().uri(uriComponent).retrieve().bodyToMono(StatusHealthDto.class).block();
        } catch(Exception e) {
            Process process = Runtime.getRuntime().exec("/opt/nevia/scripts/restart-nevia-user.sh");
            return new StatusHealthDto("DOWN");
        }


    }

    private void getLastDigitAndPort(String nameMicroservice, Integer instance, StringBuilder uri) {
        switch (nameMicroservice) {
            case "user-service" :
                switch (instance) {
                    case 1 -> uri.append("9:9786");
                    case 2 -> uri.append("12:9786");
                }
                break;
            case "energy-mix" :
                break;
        }
    }
}
