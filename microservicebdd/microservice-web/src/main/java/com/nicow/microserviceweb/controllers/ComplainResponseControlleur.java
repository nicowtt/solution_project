package com.nicow.microserviceweb.controllers;

import com.nicow.microservicebusiness.contract.ComplainResponseManager;
import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicedao.complainDao.ComplainResponseDao;
import com.nicow.microservicemodel.entities.ComplainRequest;
import com.nicow.microservicemodel.entities.ComplainResponse;
import com.nicow.microservicemodel.events.ComplainRequestEvent;
import com.nicow.microservicemodel.events.ComplainResponseEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ComplainResponseControlleur {

    static final Log logger = LogFactory.getLog(ComplainResponseControlleur.class);

    @Autowired
    private ComplainResponseDao complainResponseDao;

    @Autowired
    private ComplainRequestDao complainRequestDao;

    @Autowired
    private ComplainResponseManager complainResponseManager;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * to change response popularity
     * @param userPseudoInput -> who change
     * @param complainResponseInput -> response to update
     * @return string status
     */
    @PostMapping(value = "/changeResponsePopularity/{userPseudoInput}", consumes = "application/json")
    public ResponseEntity<String> changeResponsePopularity(
            @PathVariable String userPseudoInput,
            @RequestBody ComplainResponse complainResponseInput) {
        Boolean userIsAuthorized = false;

        // check on bdd if user connected has already updated this request
        Optional<ComplainResponse> requestOnBdd = complainResponseDao.findById(complainResponseInput.getId());
        if (requestOnBdd.isPresent()) {
            userIsAuthorized = complainResponseManager.checkIfUserCanUpdateRequestPopularity( requestOnBdd.get() , userPseudoInput);
        }

        // update
        if (userIsAuthorized) {
            complainResponseInput.addUserWhoIncreasePopularity(userPseudoInput);
            ComplainResponse updatedResponse = complainResponseDao.save(complainResponseInput);

            // notify updated response
            this.notifyResponseEvent(
                    ComplainResponseEvent.TYPE.UPDATE,
                    updatedResponse,
                    complainResponseInput.getRequestId());

            logger.info("User " + userPseudoInput + " change popularity of: " + complainResponseInput.getResponse());
            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.FORBIDDEN));
        }
    }

    /**
     * to get all responses for one request
     * @param requestId -> request concerned
     * @return list of responses
     */
    @GetMapping(value = "/getAllResponsesForOneRequest/{requestId}")
    public List<ComplainResponse> getAllResponsesForOneRequest(@PathVariable String requestId) {
        List<ComplainResponse> responses = new ArrayList<>();
        Optional<ComplainRequest> requestConcerned = complainRequestDao.findById(requestId);
        if (requestConcerned.isPresent()) {
            for (String responseId: requestConcerned.get().getComplainResponsesId() ) {
                Optional<ComplainResponse> optionalResponse = complainResponseDao.findById(responseId);
                if (optionalResponse.isPresent()) {
                    responses.add(optionalResponse.get());
                }
            }
        }
        return responses;
    }

    /**
     * for add response
     * @param complainResponseInput -> input body
     * @return string status
     */
    @PostMapping(value = "/newResponse", consumes = "application/json")
    public ResponseEntity<String> addResponse(
            @RequestBody ComplainResponse complainResponseInput) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String todayDate = dateFormat.format(date);

        complainResponseInput.setUserWhoChangePopularityList(new ArrayList<>());
        // write date
        complainResponseInput.setCreationDate(todayDate);
        // save
        ComplainResponse newResponse = complainResponseDao.save(complainResponseInput);

        // update request
        Optional<ComplainRequest> optRequest = complainRequestDao.findById(complainResponseInput.getRequestId());
        if (optRequest.isPresent() && newResponse.getId() != null) {
            ComplainRequest requestToUpdate = optRequest.get();
            // notify new response
            this.notifyResponseEvent(ComplainResponseEvent.TYPE.CREATE, newResponse, complainResponseInput.getRequestId());

            // update last date on request
            requestToUpdate.setLastResponseDate(complainResponseInput.getCreationDate());
            // add this new response on request -> responseId
            requestToUpdate.addResponseIdOnRequest(newResponse.getId());

            // notify update request
            this.notifyRequestEvent(ComplainRequestEvent.TYPE.UPDATE, requestToUpdate);

            complainRequestDao.save(requestToUpdate);

            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping(value = "/updateResponse", consumes = "application/json")
    public ResponseEntity<String> updateResponse(
            @RequestBody ComplainResponse complainResponseInput) {
        // save
        ComplainResponse updatedResponse = complainResponseDao.save(complainResponseInput);

        // notify updated response
        this.notifyResponseEvent(
                ComplainResponseEvent.TYPE.UPDATE,
                updatedResponse,
                complainResponseInput.getRequestId());

        if (updatedResponse != null) {
            logger.info("la reponse d'id: " + updatedResponse.getId() + " à été maj");
            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping(value = "/deleteResponse", consumes = "application/json")
    public ResponseEntity<String> deleteResponse(
            @RequestBody ComplainResponse complainResponseInput) {
        // get list of response on request
        Optional<ComplainRequest> requestOnBdd = complainRequestDao.findById(complainResponseInput.getRequestId());
        if (requestOnBdd.isPresent()) {
            // update list of Response on request and save updated request
            ComplainRequest requestUpdated = requestOnBdd.get();
            List<String> responsesList = requestUpdated.getComplainResponsesId();
            List<String> updatedResponsesList = new ArrayList<>();
            for (String responseId: responsesList ) {
                if (!responseId.equals(complainResponseInput.getId())) {
                    updatedResponsesList.add(responseId);
                }
            }
            requestUpdated.setComplainResponsesId(updatedResponsesList);

            // notify update request
            this.notifyRequestEvent(ComplainRequestEvent.TYPE.UPDATE, requestUpdated);

            complainRequestDao.save(requestUpdated);
            // delete response
            complainResponseDao.delete(complainResponseInput);

            // notify delete response
            this.notifyResponseEvent(ComplainResponseEvent.TYPE.DELETE, complainResponseInput, requestUpdated.getId());

            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    void notifyResponseEvent(ComplainResponseEvent.TYPE type,
                             ComplainResponse complainResponse,
                             String complainRequestId) {
        eventPublisher.publishEvent(new ComplainResponseEvent(this, type, complainResponse, complainRequestId));
    }

    void notifyRequestEvent(ComplainRequestEvent.TYPE type, ComplainRequest complainRequest) {
        eventPublisher.publishEvent(new ComplainRequestEvent(this, type, complainRequest));
    }
}
