package com.nicow.microserviceweb.ws;

import com.nicow.microservicemodel.events.ComplainResponseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ComplainResponseWs {

    @Autowired
    private SimpMessagingTemplate websocket;

    @Async
    @EventListener(ComplainResponseEvent.class)
    public void onComplainResponseEvent(ComplainResponseEvent event) {
        websocket.convertAndSend("/topic/request/response/" + event.getComplainRequestId(), event);
    }
}
