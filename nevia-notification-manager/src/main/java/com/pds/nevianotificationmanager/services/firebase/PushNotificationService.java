package com.pds.nevianotificationmanager.services.firebase;


import com.pds.nevianotificationmanager.dto.firebase.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PushNotificationService {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);


    private final FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    public void sendPushNotificationWithToken(PushNotificationRequest request){
        try {
          fcmService.sendMessageWithToken(request);
          log.info("Sent notification to " + request.getToken());
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
