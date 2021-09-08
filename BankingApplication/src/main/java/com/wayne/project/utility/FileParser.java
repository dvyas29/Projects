package com.wayne.project.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser implements IFileParser {

    public List<String> loadCredentials(BufferedReader stream) {

        List<String> credentialsList = new ArrayList<String>();
        try{
            String line;
            String[] temp;
            while( (line = stream.readLine()) != null ) {

                line = line.trim();
                temp = line.split("=");
                credentialsList.add( temp[1].trim() );
            }
        }
        catch (IOException e) {
            System.out.println("Exception Occurred");
        }
        return credentialsList;
    }
}
