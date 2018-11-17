package com.example.fatih.wirelesscomchat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fatih.wirelesscomchat.model.Peer;

import java.util.ArrayList;

/**
 * Created by vivek on 2/25/18.
 */

public class PeersAdapter extends ArrayAdapter<Peer> {

    public static final String TAG = PeersAdapter.class.getSimpleName();

    public PeersAdapter(Context context, ArrayList<Peer> peers) {
        super(context, 0, peers);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Log.d(TAG, "Inside get View");
        Peer peer = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.peers_list_row, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.device_name);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.device_status);
        Log.d(TAG, String.format("Name %s Status %s", peer.getName(), peer.getStatus()));
        // Populate the data into the template view using the data object
        tvName.setText(peer.getName());
        tvStatus.setText(peer.getStatus());
        // Return the completed view to render on screen
        return convertView;
    }


}
