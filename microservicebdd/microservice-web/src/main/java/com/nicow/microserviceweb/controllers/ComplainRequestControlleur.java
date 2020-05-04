package com.nicow.microserviceweb.controllers;

import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicemodel.entities.ComplainRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class ComplainRequestControlleur {

    static final Log logger = LogFactory.getLog(ComplainRequestControlleur.class);

    @Autowired
    private ComplainRequestDao complainRequestDao;

    @GetMapping(value = "/GetAllRequests")
    public List<ComplainRequest> getAllRequests() {
        List requestList = new ArrayList();
        requestList = complainRequestDao.findAll();
        return requestList;
    }
}
