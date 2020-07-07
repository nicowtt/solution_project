package com.nicow.microservicebusiness.contract;

import com.nicow.microservicemodel.entities.ComplainResponse;
import org.springframework.stereotype.Service;

@Service
public interface ComplainResponseManager {

    Boolean checkIfUserCanUpdateRequestPopularity(ComplainResponse responseInput, String pseudoInput);

}
