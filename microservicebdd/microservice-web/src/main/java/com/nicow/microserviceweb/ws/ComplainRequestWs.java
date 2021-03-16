package com.nicow.microserviceweb.ws;

import com.nicow.microservicemodel.events.ComplainRequestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ComplainRequestWs {

    @Autowired
    private SimpMessagingTemplate websocket;

    @Async
    @EventListener(ComplainRequestEvent.class)
    public void onComplainRequestEvent(ComplainRequestEvent event) {
        websocket.convertAndSend("/topic/request", event);
    }
}
