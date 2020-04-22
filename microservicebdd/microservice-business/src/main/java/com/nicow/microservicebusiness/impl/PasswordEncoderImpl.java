package com.nicow.microservicebusiness.impl;

import com.nicow.microservicebusiness.contract.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {
    /**
     * For hashing password
     *
     * @param passwordPlaintext
     * @return -> hashed password
     */
    @Override
    public String hashPassword(String passwordPlaintext) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(passwordPlaintext);
    }

    /**
     * For check password
     * @param pPasswordPlainText -> pass plain text
     * @param pHashingPassword -> hashingPass
     * @return
     */
    @Override
    public boolean checkPassword(String pPasswordPlainText, String pHashingPassword) {
        boolean pass = false;

        pass = BCrypt.checkpw(pPasswordPlainText,pHashingPassword);
        return pass;
    }
}
