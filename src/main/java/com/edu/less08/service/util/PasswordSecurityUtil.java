package com.edu.less08.service.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordSecurityUtil {
    public String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException();
        }
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String passwordForCheck, String hashedPassword) {
        return BCrypt.checkpw(passwordForCheck, hashedPassword);
    }
}
