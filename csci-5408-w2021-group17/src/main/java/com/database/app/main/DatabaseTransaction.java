package com.database.app.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DatabaseTransaction {
    final private static String DATABASE_DIRECTORY = "dql\\";
    final private static String TRANSACTION_DIRECTORY = "transactions\\";
    final private static String TRANSACTION_FILE = DATABASE_DIRECTORY + TRANSACTION_DIRECTORY + "Transaction";

    private static String currentTransactionFile;

    public static String getTransaction() {
        return currentTransactionFile;
    }

    public static void setTransaction() {
        long currentSystemTime = System.currentTimeMillis();
        try {
            if (Files.notExists(Paths.get(DATABASE_DIRECTORY + TRANSACTION_DIRECTORY))) {
                Files.createDirectory(Paths.get(DATABASE_DIRECTORY + TRANSACTION_DIRECTORY));
            }
            currentTransactionFile = TRANSACTION_FILE + currentSystemTime + ".txt";
            Files.createFile(Paths.get(currentTransactionFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTransaction(String transaction) {
        try {
            Files.writeString(Paths.get(currentTransactionFile), transaction + "\n", StandardOpenOption.APPEND);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static List<String> readTransactions() {
        List<String> transactions = null;
        try {
            transactions = Files.readAllLines(Paths.get(currentTransactionFile));
            return transactions;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
