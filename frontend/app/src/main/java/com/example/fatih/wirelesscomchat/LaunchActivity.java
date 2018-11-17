package com.example.fatih.wirelesscomchat;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.fatih.wirelesscomchat.Constants.*;

public class LaunchActivity extends AppCompatActivity{

    ImageView receive;
    ImageView send;
    public static final String TAG = LaunchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        receive = (ImageView) findViewById(R.id.img_receive);
        send = (ImageView) findViewById(R.id.img_send);

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                intent.putExtra(FLOW, RECEIVE);
                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                intent.putExtra(FLOW, SEND);
                startActivity(intent);
            }
        });

    }

}
