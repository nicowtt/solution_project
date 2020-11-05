package com.nicow.microservicebusiness.contract;

import com.nicow.microservicemodel.entities.ComplainRequest;
import org.springframework.stereotype.Service;

@Service
public interface ComplainRequestManager {

    Boolean checkIfUserCanUpdateRequestPopularity(ComplainRequest requestInput, String pseudoInput);
    void forgetOldRequests(int days);
}
