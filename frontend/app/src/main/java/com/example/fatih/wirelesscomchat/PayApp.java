package com.example.fatih.wirelesscomchat;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fatih.wirelesscomchat.model.Transaction;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.example.fatih.wirelesscomchat.Constants.*;

/**
 * Created by vmanohar on 3/6/18.
 */

public class PayApp extends Application {

    public static final String TAG = PayApp.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences(Constants.DATA_STORE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int existingAmount = preferences.getInt(SK_AMT, 0);

        if (existingAmount == 0 ){
            editor.putInt(SK_AMT, TOTAL_AMOUNT);
            editor.putString(SK_TXNS, getInitialTransaction());
            editor.apply();
        }
    }


    public String getInitialTransaction(){
        Transaction t = new Transaction();
        t.setCredit(false);
        t.setTransactionId(Utils.getTransactionId());
        t.setDate(Utils.getCurrentFormattedDate());
        t.setTransactionAmt("Rs 250");
        t.setName("Cafe Coffee Day");

        Transaction t1 = new Transaction();
        t1.setCredit(true);
        t1.setTransactionId(Utils.getTransactionId());
        t1.setDate(Utils.getCurrentFormattedDate());
        t1.setTransactionAmt("Rs 150");
        t1.setName("Milano Ice Cream");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(t);
        transactions.add(t1);

        Gson gson = new Gson();
        String trans = gson.toJson(transactions);
        Log.d(TAG, "Transaction "+trans);
        return trans;
    }

}
