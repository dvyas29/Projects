package com.database.app.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class DatabaseSecurity {
    static Scanner scannerPassword;
    final private String DATABASE_DIRECTORY = "dql";
    final private String PASSWORD_FILE = DATABASE_DIRECTORY + "\\admin\\" + "dqlpass.txt";

    public DatabaseSecurity() {
        this.initializePassword();
    }

    private static String acceptPassword() {
        scannerPassword = new Scanner(System.in);
		return scannerPassword.nextLine();
    }

    public void initializePassword() {
        if (Files.notExists(Paths.get(PASSWORD_FILE))) {
            createPasswordFile();
        } else {
            List<String> dataLines = null;
            try {
                dataLines = Files.readAllLines(Paths.get(PASSWORD_FILE));
                checkPassword(dataLines.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createPasswordFile() {
        try {
            Files.createFile(Paths.get(PASSWORD_FILE));
            String password;
            String confirmPassword;
            do {
                System.out.print("Create password : ");
                password = acceptPassword();
                System.out.print("Confirm password : ");
                confirmPassword = acceptPassword();
                if (!password.equals(confirmPassword)) {
                    System.out.println("Password and Confirm Password do not match.");
                }
            } while (!password.equals(confirmPassword));
            Files.writeString(Paths.get(PASSWORD_FILE), DatabaseEncryption.encrypt(password) + "\n", StandardOpenOption.APPEND);
            Files.writeString(Paths.get(PASSWORD_FILE), DatabaseEncryption.encrypt(confirmPassword), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkPassword(String password) {
        String enteredPassword;
        String decryptedPassword;
        do {
            System.out.print("Enter password : ");
            enteredPassword = acceptPassword();
            decryptedPassword = DatabaseEncryption.decrypt(enteredPassword);
            if (!password.equals(enteredPassword)) {
                System.out.println("Incorrect password.");
            }
        } while (!password.equals(decryptedPassword));
    }
}
