package com.example.fatih.wirelesscomchat;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fatih.wirelesscomchat.model.Transaction;

import java.util.ArrayList;

/**
 * Created by vivek on 2/25/18.
 */

public class TransAdapter extends ArrayAdapter<Transaction> {

    public static final String TAG = TransAdapter.class.getSimpleName();

    Context mContext;

    public TransAdapter(Context context, ArrayList<Transaction> transactions) {
        super(context, 0, transactions);
        mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Log.d(TAG, "Inside get View");
        Transaction t = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.li_payee_name);
        TextView tvTransactionId = (TextView) convertView.findViewById(R.id.li_transaction_id);
        TextView tvDate = (TextView) convertView.findViewById(R.id.li_date);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.li_transaction_amt);

        Log.d(TAG, String.format(t.toString()));
        // Populate the data into the template view using the data object
        tvName.setText(t.getName());
        tvTransactionId.setText(t.getTransactionId());
        tvDate.setText(t.getDate());
        if (t.isCredit()){
            tvAmount.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        }else{
            tvAmount.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        }
        tvAmount.setText(t.getTransactionAmt());
        // Return the completed view to render on screen
        return convertView;
    }


}
