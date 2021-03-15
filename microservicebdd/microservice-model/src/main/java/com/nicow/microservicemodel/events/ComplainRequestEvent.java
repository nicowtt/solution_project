package com.nicow.microservicemodel.events;

import com.nicow.microservicemodel.entities.ComplainRequest;
import org.springframework.context.ApplicationEvent;

public class ComplainRequestEvent extends ApplicationEvent {
    public enum TYPE {
        CREATE,
        UPDATE,
        DELETE
    }

    private TYPE type;
    private ComplainRequest complainRequest;

    public ComplainRequestEvent(Object source, TYPE type, ComplainRequest complainRequest) {
        super(source);
        this.type = type;
        this.complainRequest = complainRequest;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public ComplainRequest getComplainRequest() {
        return complainRequest;
    }

    public void setComplainRequest(ComplainRequest complainRequest) {
        this.complainRequest = complainRequest;
    }
}
