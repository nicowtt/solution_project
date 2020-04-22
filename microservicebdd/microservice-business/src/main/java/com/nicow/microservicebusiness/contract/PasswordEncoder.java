package com.nicow.microservicebusiness.contract;

import org.springframework.stereotype.Service;

@Service
public interface PasswordEncoder {

    String hashPassword(String passwordPlaintext);
    boolean checkPassword(String pPasswordPlainText, String pHashingPassword);

}
