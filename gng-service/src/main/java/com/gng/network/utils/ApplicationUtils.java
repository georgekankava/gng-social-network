package com.gng.network.utils;

import org.springframework.security.crypto.codec.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by georgekankava on 10.05.17.
 */
public class ApplicationUtils {

    public static String passwordDigest(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte [] digest = md.digest(password.getBytes());
        return new String(Hex.encode(digest));
    }

    public static boolean validatePasswordMatches(String password, String confirmNewPassword) {
        if (!password.equals(confirmNewPassword)) {
            return false;
        }
        return true;
    }
}
