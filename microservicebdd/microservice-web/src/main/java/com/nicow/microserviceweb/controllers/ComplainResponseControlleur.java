package com.nicow.microserviceweb.controllers;

import com.nicow.microservicebusiness.contract.ComplainResponseManager;
import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicedao.complainDao.ComplainResponseDao;
import com.nicow.microservicemodel.entities.ComplainRequest;
import com.nicow.microservicemodel.entities.ComplainResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            complainResponseDao.save(complainResponseInput);

            logger.info("User " + userPseudoInput + " change popularity of: " + complainResponseInput.getResponse());
            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.FORBIDDEN));
        }
    }

}
