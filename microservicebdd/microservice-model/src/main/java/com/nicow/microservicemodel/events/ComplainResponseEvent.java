package com.nicow.microservicemodel.events;

import com.nicow.microservicemodel.entities.ComplainResponse;
import org.springframework.context.ApplicationEvent;

public class ComplainResponseEvent extends ApplicationEvent {
    public enum TYPE {
        CREATE,
        UPDATE,
        DELETE
    }

    private ComplainResponseEvent.TYPE type;
    private ComplainResponse complainResponse;
    private String complainRequestId;

    public ComplainResponseEvent(Object source,
                                 ComplainResponseEvent.TYPE type,
                                 ComplainResponse complainResponse,
                                 String complainRequestId) {
        super(source);
        this.type = type;
        this.complainResponse = complainResponse;
        this.complainRequestId = complainRequestId;
    }

    public ComplainResponseEvent.TYPE getType() {
        return type;
    }

    public void setType(ComplainResponseEvent.TYPE type) {
        this.type = type;
    }

    public ComplainResponse getComplainResponse() {
        return complainResponse;
    }

    public void setComplainResponse(ComplainResponse complainResponse) {
        this.complainResponse = complainResponse;
    }

    public String getComplainRequestId() {
        return complainRequestId;
    }

    public void setComplainRequestId(String complainRequestId) {
        this.complainRequestId = complainRequestId;
    }
}
