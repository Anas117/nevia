package com.pds.nevianotificationmanager.controller.firebase;

import com.pds.nevianotificationmanager.dto.firebase.PushNotificationRequest;
import com.pds.nevianotificationmanager.dto.firebase.PushNotificationResponse;
import com.pds.nevianotificationmanager.services.firebase.PushNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/push-notification")
public class PushNotificationController {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationController.class);


    private final PushNotificationService pushNotificationService;

    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/token")
    public PushNotificationResponse sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationWithToken(request);
        log.info("Notification has been sent to " + request.getToken());
        return new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent.");
    }
}
