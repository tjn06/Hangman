package com.example.svghangman;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Helpers {

    public static ArrayList<String> getWords(Context context, String filename) {
        InputStream myInputStream = null;
        Scanner in = null;
        ArrayList<String> myListOfWords = new ArrayList<>();

        String aWord;
        try {
            myInputStream = context.getAssets().open("database_file.txt");
            in = new Scanner(myInputStream);
            while (in.hasNext()) {
                aWord = in.next();
                myListOfWords.add(aWord);
                //Toast.makeText(MainActivity.this, aWord, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(context, e.getClass().getSimpleName() + ": " + e.getMessage() , Toast.LENGTH_SHORT).show();
            // e.printStackTrace();
        } finally {
            //close scanner
            if(in != null) {
                in.close();
            }
            //close input-stream
            try {
                if(myInputStream != null) {
                    myInputStream.close();
                }
            } catch (IOException e) {
                Toast.makeText(context, e.getClass().getSimpleName() + ": " + e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }//End scanner input-stream
        return myListOfWords;
    }

}

