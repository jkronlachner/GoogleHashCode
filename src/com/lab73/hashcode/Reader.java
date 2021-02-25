package com.lab73.hashcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {
    public static String readFile(String filename) {
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            StringBuilder builder = new StringBuilder();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                builder.append(data).append("\n");
            }
            myReader.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return "";
        }
    }

}
