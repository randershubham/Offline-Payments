package com.example.fatih.wirelesscomchat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by vmanohar on 3/7/18.
 */

public class Utils {

    public static String getTransactionId() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000))+"");
        for (int i = 0; i < 5; i++){
            sb.append(chars[rnd.nextInt(chars.length)]);
        }
        return sb.toString();
    }

    public static String getCurrentFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(System.currentTimeMillis()));
        return dateString;
    }

}
