package com.nicow.microservicebusiness.impl;

import com.nicow.microservicebusiness.contract.ComplainRequestManager;
import com.nicow.microservicedao.complainDao.ComplainRequestDao;
import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.entities.ComplainRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ComplainrequestManagerImpl implements ComplainRequestManager {

    @Autowired
    private ComplainUserDao complainUserDao;

    @Autowired
    private ComplainRequestDao complainRequestDao;

    public Boolean checkIfUserCanUpdateRequestPopularity(ComplainRequest requestInput, String pseudoInput) {
        Optional<String> pseudo = requestInput.getUserWhoChangePopularityList().stream()
                .filter(p -> p.equals(pseudoInput))
                .findFirst();

        if (pseudo.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public void forgetOldRequests(int days) {
        List<ComplainRequest> requestList = complainRequestDao.findAllByForgottenFalse();
        Calendar today = Calendar.getInstance();

        Calendar lastResponseCalendarAndSevenDays = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);

        for (int i = 0; i < requestList.size(); i++) {
            try {
                lastResponseCalendarAndSevenDays.setTime(sdf.parse(requestList.get(i).getLastResponseDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            lastResponseCalendarAndSevenDays.add(Calendar.DAY_OF_MONTH, days);
            if (today.compareTo(lastResponseCalendarAndSevenDays) > 0) {
                requestList.get(i).setForgetIt(true);
                complainRequestDao.save(requestList.get(i));
            }
        }
    }

}
