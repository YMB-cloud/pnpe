package com.app.auth;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean verify(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}
