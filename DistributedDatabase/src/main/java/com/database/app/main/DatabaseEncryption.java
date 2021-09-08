package com.database.app.main;

public class DatabaseEncryption {
    public static String encrypt(String strToEncrypt) {
        try {
            return strToEncrypt;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            return strToDecrypt;
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
