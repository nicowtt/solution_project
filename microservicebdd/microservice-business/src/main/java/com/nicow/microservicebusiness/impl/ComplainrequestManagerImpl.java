package com.nicow.microservicebusiness.impl;

import com.nicow.microservicebusiness.contract.ComplainRequestManager;
import com.nicow.microservicedao.complainDao.ComplainUserDao;
import com.nicow.microservicemodel.entities.ComplainRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ComplainrequestManagerImpl implements ComplainRequestManager {

    @Autowired
    private ComplainUserDao complainUserDao;

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

}
