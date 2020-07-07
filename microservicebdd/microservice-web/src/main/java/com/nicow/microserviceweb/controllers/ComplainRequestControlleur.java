package com.nicow.microserviceweb.controllers;

import com.nicow.microservicebusiness.contract.ComplainRequestManager;
import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicemodel.entities.ComplainRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ComplainRequestControlleur {

    static final Log logger = LogFactory.getLog(ComplainRequestControlleur.class);

    @Autowired
    private ComplainRequestDao complainRequestDao;

    @Autowired
    private ComplainRequestManager complainRequestManager;

    @GetMapping(value = "/getAllRequests")
    public List<ComplainRequest> getAllRequests() {
        List requestList = new ArrayList();
        requestList = complainRequestDao.findAll();
        return requestList;
    }

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
            complainRequestDao.save(complainRequestInput);
            logger.info("User " + userPseudoInput + " change popularity of: " + complainRequestInput.getRequest());
            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.FORBIDDEN));
        }
    }

    @GetMapping(value = "/getOneRequest/{requestId}")
    public Optional<ComplainRequest> getOneRequest(@PathVariable String requestId) {
        return complainRequestDao.findById(requestId);
    }
}
