package com.pds.nevianotificationmanager.controller.firebase;

import com.pds.nevianotificationmanager.dto.firebase.RegistrationTokenDto;
import com.pds.nevianotificationmanager.services.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);


    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("")
    public void registration(@RequestBody RegistrationTokenDto registrationTokenDto) {
        log.info("Registration for user" + registrationTokenDto.getEmail() + " with token " + registrationTokenDto.getToken());
        registrationService.register(registrationTokenDto);
    }

    @DeleteMapping("/{email}")
    public String unregister(@PathVariable String email) {
        log.info("Delete registration for user" + email);
        return registrationService.unregister(email);
    }
}
