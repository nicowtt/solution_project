package com.nicow.microserviceweb.controllers;

import com.nicow.microservicebusiness.contract.ComplainRequestManager;
import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicedao.complainDao.ComplainResponseDao;
import com.nicow.microservicemodel.entities.ComplainRequest;
import com.nicow.microservicemodel.entities.ComplainResponse;
import com.nicow.microservicemodel.events.ComplainRequestEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ComplainRequestControlleur {

    static final Log logger = LogFactory.getLog(ComplainRequestControlleur.class);

    @Autowired
    private ComplainRequestDao complainRequestDao;

    @Autowired
    private ComplainResponseDao complainResponseDao;

    @Autowired
    private ComplainRequestManager complainRequestManager;

    @Autowired
    private SimpMessagingTemplate websocket;

    /**
     * to get all requests
     * @return all requests
     */
    @GetMapping(value = "/getAllRequests")
    public List<ComplainRequest> getAllRequests() {
        return complainRequestDao.findAll();
    }

    @GetMapping(value = "/getAllRequestsNotForgotten")
    public List<ComplainRequest> getAllRequestsNotForgotten() {
        complainRequestManager.forgetOldRequests(7);
        return complainRequestDao.findAllByForgottenFalse();
    }

    /**
     * to change request popularity
     * @param userPseudoInput
     * @param complainRequestInput
     * @return 200 if ok
     */
    @PostMapping(value = "/changeRequestPopularity/{userPseudoInput}", consumes = "application/json")
    public ResponseEntity<String> changeRequestPopularity(
            @PathVariable String userPseudoInput,
            @RequestBody ComplainRequest complainRequestInput) {
        Boolean userIsAuthorized = false;

        // check on bdd if user connected has already updated this request
        Optional<ComplainRequest> requestOnBdd = complainRequestDao.findById(complainRequestInput.getId());
        if (requestOnBdd.isPresent()) {
            userIsAuthorized = complainRequestManager.checkIfUserCanUpdateRequestPopularity( requestOnBdd.get() , userPseudoInput);
        }

        // update
        if (userIsAuthorized) {
            complainRequestInput.addUserWhoIncreasePopularity(userPseudoInput);

            // brodcast for update request
            this.broadcast(new ComplainRequestEvent(
                    this,
                    ComplainRequestEvent.TYPE.UPDATE,
                    complainRequestInput
            ));

            complainRequestDao.save(complainRequestInput);
            logger.info("User " + userPseudoInput + " change popularity of: " + complainRequestInput.getRequest());
            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.FORBIDDEN));
        }
    }

    /**
     * to get only one request
     * @param requestId
     * @return the request
     */
    @GetMapping(value = "/getOneRequest/{requestId}")
    public Optional<ComplainRequest> getOneRequest(@PathVariable String requestId) {
        return complainRequestDao.findById(requestId);
    }

    /**
     * to create new request
     * @param complainRequestInput
     * @return 200 if ok
     */
    @PostMapping(value = "/newRequest", consumes = "application/json")
    public ResponseEntity<String> addRequest(
            @RequestBody ComplainRequest complainRequestInput) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String todayDate = dateFormat.format(date);

        complainRequestInput.setCreationDate(todayDate);
        complainRequestInput.setUserWhoChangePopularityList(new ArrayList<>());
        complainRequestInput.setComplainResponsesId(new ArrayList<>());
        complainRequestInput.setLastResponseDate(todayDate);
        complainRequestInput.setForgetIt(false);

        ComplainRequest complainRequestSaved = complainRequestDao.save(complainRequestInput);
        if (complainRequestSaved.getId() != null) {
            logger.info("new request created by: " + complainRequestInput.getCreatorEmail() + "on: "
                    + complainRequestInput.getCreationDate());

            // broadcast new complainRequest
            this.broadcast(new ComplainRequestEvent(
                    this,
                    ComplainRequestEvent.TYPE.CREATE,
                    complainRequestSaved));

            return (new ResponseEntity<>(HttpStatus.OK));
        }
        return (new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
    }

    @PostMapping(value = "/deleteRequest", consumes = "application/json")
    public ResponseEntity<String> deleteResponse(
            @RequestBody ComplainRequest complainRequestInput) {
        // delete all response associate
        for (String requestResponseId: complainRequestInput.getComplainResponsesId()) {
            Optional<ComplainResponse> responseToDeleteOpt = complainResponseDao.findById(requestResponseId);
            if (responseToDeleteOpt.isPresent()) {
                ComplainResponse responseToDelete = responseToDeleteOpt.get();
                complainResponseDao.delete(responseToDelete);
            }
        }
        // delete request
        Optional<ComplainRequest> requestToDeleteOpt = complainRequestDao.findById(complainRequestInput.getId());
        if (requestToDeleteOpt.isPresent()) {
            ComplainRequest requestToDelete = requestToDeleteOpt.get();

            // brodcast for delete request
            this.broadcast(new ComplainRequestEvent(
                    this,
                    ComplainRequestEvent.TYPE.DELETE,
                    requestToDelete
                    ));

            complainRequestDao.delete(requestToDelete);
            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping(value = "/updateRequest", consumes = "application/json")
    public ResponseEntity<String> updateRequest(
            @RequestBody ComplainRequest complainRequestInput) {

        // brodcast for update request
        this.broadcast(new ComplainRequestEvent(
                this,
                ComplainRequestEvent.TYPE.UPDATE,
                complainRequestInput
        ));

        // save
        ComplainRequest updatedRequest = complainRequestDao.save(complainRequestInput);
        if (updatedRequest!= null) {
            logger.info("la request d'id: " + updatedRequest.getId() + " à été maj");
            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private void broadcast(ComplainRequestEvent complainRequestEvent) {
        websocket.convertAndSend("/topic/request", complainRequestEvent);
    }
}
