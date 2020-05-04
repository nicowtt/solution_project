package com.nicow.microserviceweb.controllers;

import com.nicow.microservicedao.complainDao.ComplainThemeDao;
import com.nicow.microservicemodel.entities.ComplainTheme;
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
public class ComplainThemeController {
    static final Log logger = LogFactory.getLog(ComplainThemeController.class);

    @Autowired
    private ComplainThemeDao complainThemeDao;

    @GetMapping(value = "/GetAllThemes")
    public List<ComplainTheme> getAllThemes() {
        List themeList = new ArrayList();
        themeList = complainThemeDao.findAll();
        return themeList;
    }

}
