package com.wayne.project.utility;

// https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
// Looked into how to fetch characters
public class RandomCharacter implements IRandomCharacter {

    private int sizeOfRandomCharacters;

    public RandomCharacter(int size) {

        this.sizeOfRandomCharacters = size;
    }

    public String generateRandomAlphabeticCharacters() {

        StringBuilder resultString = new StringBuilder();
        String alphabeticString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int sizeOfRandomString = alphabeticString.length();
        int randomNumber = 0;
        for(int i=0; i<sizeOfRandomCharacters;i++) {
            randomNumber = (int) (sizeOfRandomString * Math.random());
            resultString.append(alphabeticString.charAt(randomNumber));
        }
        return resultString.toString();

    }

    @Override
    public String generateRandomAlphaNumericCharacters() {

        StringBuilder resultString = new StringBuilder();
        String alphaNumeric = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int sizeOfRandomString = alphaNumeric.length();
        int randomNumber = 0;
        for(int i=0; i<sizeOfRandomCharacters;i++) {
            randomNumber = (int) (sizeOfRandomString * Math.random());
            resultString.append(alphaNumeric.charAt(randomNumber));
        }
        return resultString.toString();
    }

    public int generateRandomNumericCharacters() {

        StringBuilder resultString = new StringBuilder();
        String alphaNumeric = "1234567890";
        int sizeOfRandomString = alphaNumeric.length();
        int randomNumber = 0;
        for(int i=0; i<sizeOfRandomCharacters;i++) {
            randomNumber = (int) (sizeOfRandomString * Math.random());
            resultString.append(alphaNumeric.charAt(randomNumber));
        }
        return Integer.parseInt(resultString.toString());
    }
}
