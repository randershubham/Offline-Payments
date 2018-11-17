package com.example.fatih.wirelesscomchat;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fatih.wirelesscomchat.model.Peer;
import com.example.fatih.wirelesscomchat.model.RecipientDetails;
import com.example.fatih.wirelesscomchat.model.SenderDetails;
import com.example.fatih.wirelesscomchat.model.Transaction;
import com.example.fatih.wirelesscomchat.model.TransactionDetails;
import com.example.fatih.wirelesscomchat.model.TransferInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.example.fatih.wirelesscomchat.Constants.FLOW;
import static com.example.fatih.wirelesscomchat.Constants.PORT;
import static com.example.fatih.wirelesscomchat.Constants.RECEIVE;
import static com.example.fatih.wirelesscomchat.Constants.SEND;
import static com.example.fatih.wirelesscomchat.Constants.SK_AMT;
import static com.example.fatih.wirelesscomchat.Constants.SK_TXNS;
import static com.example.fatih.wirelesscomchat.Utils.getCurrentFormattedDate;
import static com.example.fatih.wirelesscomchat.Utils.getTransactionId;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.PeerListListener,
        WifiP2pManager.ConnectionInfoListener, WifiP2pManager.ChannelListener, WifiP2pManager.ActionListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;

    ArrayList<WifiP2pDevice> deviceList;
    HashMap<String, Peer> peerHashMap = new HashMap<>();


    ListView peerListView;
    ListView txnListView;
    PeersAdapter peersAdapter;
    TransAdapter transAdapter;

    RelativeLayout overlayView;
    RelativeLayout sendView;
    RelativeLayout receiveView;
    RelativeLayout successView;
    RelativeLayout appBar;

    Button btnDiscover;
    Button btnDisconnect;
    Button btnSendPayment;

    EditText etAmount;
    EditText etDesc;

    TextView connectionStatusTitle;
    TextView acceptMsg;
    TextView successMsg;
    TextView appBarTitle;
    TextView balanceAmt;
    TextView balanceAmtSendView;
    ProgressBar findingProgress;

    String hostAddress;

    int flow;

    ArrayList<Peer> peerArrayList = new ArrayList<>();
    ArrayList<Transaction> transactions = new ArrayList<>();

    SharedPreferences prefs;
    private WifiP2pDevice currentDevice;

    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences(Constants.DATA_STORE, MODE_PRIVATE);
        mAPIService = ApiUtils.getAPIService();
        initializeViews();
        initializeWifiManager();
        loadTransaction();
        setUpListeners();
        initialView();
        peersAdapter = new PeersAdapter(this, peerArrayList);
        peerListView.setAdapter(peersAdapter);
        acceptMsg.setVisibility(View.GONE);
    }

    private void initialView() {
        sendView.setVisibility(View.GONE);
        receiveView.setVisibility(View.VISIBLE);
        overlayView.setVisibility(View.VISIBLE);
    }

    private void initializeWifiManager() {
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    private void setUpListeners() {
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareLayoutForDiscover();
                discoverPeers();
            }
        });

        peerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Item clicked " + position);
                connectToDevice(position);
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();
            }
        });

        btnSendPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPayment();
            }
        });
    }

    private void sendPayment() {
        peerHashMap.clear();
        String amount = etAmount.getText().toString();
        String desc = etDesc.getText().toString();
        String msg = "Payment for Rs." + amount + " has been initiated successfully";
        successMsg.setText(msg);
        etAmount.setText("");
        etDesc.setText("");
        TransferInfo tr = getTransferPayload(amount);
        Gson gson = new Gson();
        String payload = gson.toJson(tr);
        Intent serviceIntent = new Intent(MainActivity.this, TransactionService.class);
        serviceIntent.setAction(TransactionService.SEND_MONEY);
        serviceIntent.putExtra(TransactionService.MONEY, payload);
        serviceIntent.putExtra(TransactionService.HOST, hostAddress);
        serviceIntent.putExtra(TransactionService.PORT, PORT);
        Log.d(TAG, "Starting service");
        startService(serviceIntent);
        showSuccess();
        updateDataUi(amount, tr.getTransactionDetails().getId(),
                tr.getTransactionDetails().getDate(), tr.getSenderDetails().getSenderName(), false);

        mAPIService.makePayment(tr).enqueue(new Callback<TransferInfo>() {
            @Override
            public void onResponse(Call<TransferInfo> call, Response<TransferInfo> response) {

                Log.d(TAG, "Response received "+response.message());

                if(response.isSuccessful()) {
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<TransferInfo> call, Throwable throwable) {
                Log.d(TAG, "Unable to send payment api");
            }
        });

    }

    private void initializeViews() {
        peerListView = (ListView) findViewById(R.id.peers_list_view);
        txnListView = (ListView) findViewById(R.id.lv_transaction);

        overlayView = (RelativeLayout) findViewById(R.id.layout_overlay);
        receiveView = (RelativeLayout) findViewById(R.id.layout_receive);
        sendView = (RelativeLayout) findViewById(R.id.layout_send);
        successView = (RelativeLayout) findViewById(R.id.layout_success);
        appBar = (RelativeLayout) findViewById(R.id.app_bar);

        connectionStatusTitle = (TextView) findViewById(R.id.connection_status_text);
        acceptMsg = (TextView) findViewById(R.id.accept_msg);
        successMsg = (TextView) findViewById(R.id.success_msg);
        appBarTitle = (TextView) findViewById(R.id.app_bar_title);
        balanceAmt = (TextView) findViewById(R.id.bal_amt);
        balanceAmtSendView = (TextView) findViewById(R.id.bal_amt_send);

        findingProgress = (ProgressBar) findViewById(R.id.progressBar_find_peer);

        btnDiscover = (Button) findViewById(R.id.btn_discover);
        btnDisconnect = (Button) findViewById(R.id.btn_disconnect);
        btnSendPayment = (Button) findViewById(R.id.send_amount_btn);

        etAmount = (EditText) findViewById(R.id.send_amount);
        etDesc = (EditText) findViewById(R.id.send_amount_desc);

        balanceAmt.setText(String.valueOf(getBalance()));
        balanceAmtSendView.setText(String.valueOf(getBalance()));

    }

    private void prepareLayoutForDiscover() {
        ViewGroup.LayoutParams layoutParams = overlayView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        peerListView.setVisibility(View.VISIBLE);
        connectionStatusTitle.setVisibility(View.GONE);
        findingProgress.setVisibility(View.VISIBLE);
        sendView.setVisibility(View.GONE);
    }

    private void loadTransaction() {

        @SuppressWarnings("serial")
        Type collectionType = new TypeToken<List<Transaction>>() {
        }.getType();

        Gson gson = new Gson();
        transactions = gson.fromJson(prefs.getString(SK_TXNS, null), collectionType);
        Log.d(TAG, "Load Transaction "+transactions);
        transAdapter = new TransAdapter(this, transactions);
        txnListView.setAdapter(transAdapter);
    }

    private void discoverPeers() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Discovery Initiated ",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int reasonCode) {
                Toast.makeText(getApplicationContext(), "Discovery is a failure " + reasonCode,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        flow = getIntent().getIntExtra(FLOW, RECEIVE);
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    @Override
    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
        Log.d(TAG, "onPeersAvailable" + System.currentTimeMillis());
        Log.d(TAG, "Peers " + wifiP2pDeviceList.getDeviceList());
        if (wifiP2pDeviceList.getDeviceList().size() > 0) {
            deviceList = new ArrayList(wifiP2pDeviceList.getDeviceList());
            for (WifiP2pDevice device : deviceList) {
                if (!peerHashMap.containsKey(device.deviceAddress)) {
                    Peer p = new Peer();
                    p.setName(device.deviceName);
                    p.setStatus(getStatus(device.status));
                    p.setId(device.deviceAddress);
                    peerHashMap.put(device.deviceAddress, p);
                    peerArrayList.add(p);
                    Log.d(TAG, "" + peerArrayList);
                } else {
                    Log.d(TAG, "Peer already exists");
                }
            }
            peersAdapter.notifyDataSetChanged();
        } else {
            peerArrayList.clear();
            peersAdapter.notifyDataSetChanged();
            connectionStatusTitle.setText(R.string.not_connected);
            connectionStatusTitle.setTextColor(ContextCompat.getColor(this, R.color.red));
            btnDiscover.setVisibility(View.VISIBLE);
            btnDisconnect.setVisibility(View.GONE);
        }
    }

    void connectToDevice(int position) {
        final WifiP2pDevice toConnect = deviceList.get(position);
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = toConnect.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        if (getIntent().getIntExtra(FLOW, -1) == SEND) {
            Log.d(TAG, "Flow Send");
            config.groupOwnerIntent = 0;
        } else {
            Log.d(TAG, "Flow Receive");
            config.groupOwnerIntent = 15;
        }
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Successfully connected  at" + System.currentTimeMillis());
                Log.i(TAG, "Successfully connected to " + toConnect.deviceName);
                peersAdapter.clear();
                peersAdapter.notifyDataSetChanged();
                acceptMsg.setVisibility(View.VISIBLE);
                acceptMsg.setText("Tap 'Accept' on " + toConnect.deviceName + " to establish connection");
            }

            @Override
            public void onFailure(int reason) {
                Log.i(TAG, "Error while connecting to device. Reason code: " + reason);
            }

        });

    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        Log.d(TAG, "Connection Established " + System.currentTimeMillis());
        displaySendReceiveView();
        if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
            new FileServerAsyncTask(MainActivity.this).execute();
            Log.d(TAG, "Network Group Formed, I am the owner");
            connectionStatusTitle.setText(R.string.ready_receive);
        } else if (wifiP2pInfo.groupFormed) {
            // The other device acts as the client. In this case, we enable the
            // get file button.
            connectionStatusTitle.setText(R.string.ready_send);
            Log.d(TAG, "Network Group Formed, I am not the owner :(");
            hostAddress = wifiP2pInfo.groupOwnerAddress.getHostAddress();
        }
    }

    private void displaySendReceiveView() {
        peersAdapter.clear();
        peersAdapter.notifyDataSetChanged();
        ViewGroup.LayoutParams layoutParams = overlayView.getLayoutParams();
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        layoutParams.height = (int) (56 * scale + 0.5f);
        overlayView.setLayoutParams(layoutParams);
        overlayView.setVisibility(View.VISIBLE);

        acceptMsg.setVisibility(View.GONE);
        findingProgress.setVisibility(View.GONE);

        connectionStatusTitle.setTextColor(ContextCompat.getColor(this, R.color.green));
        connectionStatusTitle.setVisibility(View.VISIBLE);

        btnDiscover.setVisibility(View.GONE);
        btnDisconnect.setVisibility(View.VISIBLE);
        Log.d(TAG, "Flow: " + flow);
        if (flow == RECEIVE) {
            appBarTitle.setText(R.string.accounts_title);
            receiveView.setVisibility(View.VISIBLE);
            sendView.setVisibility(View.GONE);
        } else {
            appBarTitle.setText(R.string.send_title);
            sendView.setVisibility(View.VISIBLE);
            receiveView.setVisibility(View.GONE);
        }
    }

    public void goBack(View view){
        disconnect();
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        disconnect();
        super.onBackPressed();
    }

    public String getStatus(int status) {
        switch (status) {
            case 0: {
                return "CONNECTED";
            }
            case 1: {
                return "INVITED";
            }
            case 2: {
                return "FAILED";
            }
            case 3: {
                return "AVAILABLE";
            }
            case 4: {
                return "UNAVAILABLE";
            }
            default: {
                return "UNAVAILABLE";
            }
        }
    }

    public int getBalance(){
        int amt = prefs.getInt(SK_AMT, 0);
        return amt;
    }


    public boolean updateBalance(int amt){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SK_AMT, amt);
        return editor.commit();
    }

    public void showSuccess(){
        receiveView.setVisibility(View.GONE);
        sendView.setVisibility(View.GONE);
        overlayView.setVisibility(View.GONE);
        appBar.setVisibility(View.GONE);
        successView.setVisibility(View.VISIBLE);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        successView.setVisibility(View.GONE);
                        receiveView.setVisibility(View.VISIBLE);
                        overlayView.setVisibility(View.VISIBLE);
                        sendView.setVisibility(View.GONE);
                        appBar.setVisibility(View.VISIBLE);
                    }
                },
                4000);
    }

    public void disconnect() {
        mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {

            @Override
            public void onFailure(int reasonCode) {
                Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);

            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "Disconnected Successfully");
                btnDiscover.setVisibility(View.VISIBLE);
                btnDisconnect.setVisibility(View.GONE);
                peerHashMap.clear();
            }

        });
    }

    @Override
    public void onChannelDisconnected() {
        Log.d(TAG, "Disconnected");
        peerHashMap.clear();
        connectionStatusTitle.setText(R.string.not_connected);
        connectionStatusTitle.setTextColor(ContextCompat.getColor(this, R.color.red));
        btnDiscover.setVisibility(View.VISIBLE);
        btnDisconnect.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "ActionListener Success");
    }

    @Override
    public void onFailure(int reason) {
        Log.d(TAG, "ActionListener Failure");
    }

    public void setCurrentDevice(WifiP2pDevice currentDevice) {
        this.currentDevice = currentDevice;
    }

    public WifiP2pDevice getCurrentDevice() {
        return currentDevice;
    }

    public static class FileServerAsyncTask extends AsyncTask<Void, Void, String> {

        @SuppressLint("StaticFieldLeak")
        private MainActivity mainActivity;

        FileServerAsyncTask(MainActivity activity) {
            mainActivity = activity;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                Log.d(TAG, "Server: Socket opened");
                Socket client = serverSocket.accept();
                Log.d(TAG, "Server: connection done");
                InputStream inputstream = client.getInputStream();
                String moneyReceived = convertStreamToString(inputstream);
                Log.d(TAG, "Money Received " + moneyReceived);
                serverSocket.close();
                return moneyReceived;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Expection");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "Completed Receiving money");
            Log.d(TAG, "Money " + s);
            mainActivity.disconnect();
            Gson gson = new Gson();
            TransferInfo tr = gson.fromJson(s, TransferInfo.class);
            String amount = tr.getTransactionDetails().getAmount();
            String txnId = tr.getTransactionDetails().getId();
            String date = tr.getTransactionDetails().getDate();
            String senderName = tr.getSenderDetails().getSenderName();
            String msg = "Payment for Rs." + amount + " has been initiated successfully";
            mainActivity.successMsg.setText(msg);
            mainActivity.showSuccess();
            mainActivity.updateDataUi(amount, txnId, date, senderName, true);
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

    private void updateDataUi(String amount, String txnId, String dateString, String name, boolean credit) {
        int amt = Integer.parseInt(amount);
        int existingBalance = getBalance();
        int newBalance;
        if (credit){
            Log.d(TAG, "Credit ");
            newBalance = existingBalance + amt;
        }else {
            Log.d(TAG, "Debit  ");
            newBalance = existingBalance - amt;
        }
        updateBalance(newBalance);
        balanceAmt.setText("Rs "+getBalance());
        balanceAmtSendView.setText("Rs "+getBalance());

        Transaction t = new Transaction();
        t.setDate(dateString);
        t.setName(name);
        t.setTransactionAmt("Rs "+amount);
        t.setTransactionId(txnId);
        t.setCredit(credit);
        transactions.add(0, t);
        updateTxns();
        transAdapter.notifyDataSetChanged();
    }

    private void updateTxns() {
        Gson gson = new Gson();
        String txn = gson.toJson(transactions);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SK_TXNS, txn);
        editor.commit();
    }


    public TransferInfo getTransferPayload(String amout) {
        SenderDetails s = new SenderDetails();
        s.setSenderAccountNumber(Constants.SENDER_ACCOUNT_NO);
        s.setCvv(Constants.SENDER_CVV);
        s.setSenderAddress(Constants.SENDER_ADDRESS);
        s.setSenderCardExpiryDate(Constants.SENDER_EXPIRY);
        s.setSenderCity(Constants.SENDER_CITY);
        s.setSenderCountryCode(Constants.SENDER_COUNTRY_CODE);
        s.setSenderEmailAddress(Constants.SENDER_EMAIL);
        s.setSenderMobileNumber(Constants.SENDER_MOBILE_NUMBER);
        s.setSenderName(Constants.SENDER_NAME);
        s.setSenderReference(Constants.SENDER_REFERENCE);
        s.setSenderStateCode(Constants.SENDER_STATE);
        TransactionDetails t = new TransactionDetails();
        t.setAmount(amout);
        t.setId(getTransactionId());
        t.setDate(getCurrentFormattedDate());
        TransferInfo tr = new TransferInfo();
        tr.setSenderDetails(s);
        tr.setTransactionDetails(t);
        RecipientDetails recipientDetails = new RecipientDetails();
        recipientDetails.setRecipientName(Constants.RPT_NAME);
        recipientDetails.setRecipientEmailAddress(Constants.RPT_EMAIL);
        recipientDetails.setRecipientMobileNumber(Constants.RPT_MOBILE);
        recipientDetails.setRecipientPrimaryAccountNumber(Constants.RPT_PAN);
        tr.setRecipientDetails(recipientDetails);
        return tr;
    }
}
