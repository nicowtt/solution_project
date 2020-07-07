package com.nicow.microservicebusiness.impl;

import com.nicow.microservicebusiness.contract.ComplainResponseManager;
import com.nicow.microservicedao.complainDao.ComplainResponseDao;
import com.nicow.microservicemodel.entities.ComplainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ComplainResponseManagerImpl implements ComplainResponseManager {

    @Autowired
    private ComplainResponseDao complainResponseDao;

    public Boolean checkIfUserCanUpdateRequestPopularity(ComplainResponse responseInput, String pseudoInput) {
        Optional<String> pseudo = responseInput.getUserWhoChangePopularityList().stream()
                .filter(p -> p.equals(pseudoInput))
                .findFirst();

        if (pseudo.isPresent()) {
            return false;
        } else {
            return true;
        }
    }
}
