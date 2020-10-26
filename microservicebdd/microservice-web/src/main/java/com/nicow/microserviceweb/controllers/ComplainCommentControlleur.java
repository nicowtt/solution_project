package com.nicow.microserviceweb.controllers;

import com.nicow.microservicedao.complainDao.ComplainCommentDao;
import com.nicow.microservicedao.complainDao.ComplainResponseDao;
import com.nicow.microservicemodel.entities.ComplainComment;
import com.nicow.microservicemodel.entities.ComplainRequest;
import com.nicow.microservicemodel.entities.ComplainResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController
public class ComplainCommentControlleur {

    static final Log logger = LogFactory.getLog(ComplainCommentControlleur.class);

    @Autowired
    private ComplainCommentDao complainCommentDao;

    @Autowired
    private ComplainResponseDao complainResponseDao;


    /**
     * new comment
     * @param responseId
     * @param complainCommentInput
     * @return http response
     */
    @PostMapping(value = "/newComment/{responseId}", consumes = "application/json")
    public ResponseEntity<String> addComment(
            @PathVariable String responseId,
            @RequestBody ComplainComment complainCommentInput) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String todayDate = dateFormat.format(date);

        // write date
        complainCommentInput.setCreationDate(todayDate);
        // save
        ComplainComment newComment = complainCommentDao.save(complainCommentInput);
        // update response
        Optional<ComplainResponse> optResponse = complainResponseDao.findById(responseId);
        if (optResponse.isPresent() && newComment.getId() != null) {
            ComplainResponse responseToUpdate = optResponse.get();
            // add this new comment on response -> commentList
            responseToUpdate.addCommentOnResponseCommentList(newComment);
            complainResponseDao.save(responseToUpdate);
            return (new ResponseEntity<>(HttpStatus.OK));
        } else {
            return (new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }



}
