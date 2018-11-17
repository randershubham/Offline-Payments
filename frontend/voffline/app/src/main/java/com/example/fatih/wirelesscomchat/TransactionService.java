package com.example.fatih.wirelesscomchat;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TransactionService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String SEND_MONEY = "com.example.fatih.wirelesscomchat.action.SEND_MONEY";

    // TODO: Rename parameters
    public static final String MONEY = "com.example.fatih.wirelesscomchat.extra.MONEY";
    public static final String HOST = "com.example.fatih.wirelesscomchat.extra.HOST";
    public static final String PORT = "com.example.fatih.wirelesscomchat.extra.PORT";

    public static final String TAG = TransactionService.class.getSimpleName();
    private static final int SOCKET_TIMEOUT = 5000;

    public TransactionService() {
        super("TransactionService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startSendMoney(Context context, String param1) {
        Intent intent = new Intent(context, TransactionService.class);
        intent.setAction(SEND_MONEY);
        intent.putExtra(MONEY, param1);
        context.startService(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();

            Context context = getApplicationContext();
            if (action.equals(SEND_MONEY)) {
                String money = intent.getExtras().getString(MONEY);
                String host = intent.getExtras().getString(HOST);
                int port = intent.getExtras().getInt(PORT);
                Socket socket = new Socket();
                byte buf[]  = new byte[1024];
                int len;
                try {
                    Log.d(TAG, "Opening client socket - ");
                    if (!socket.isConnected()){
//                        socket.bind(null);
                        socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);
                    }
                    Log.d(TAG, "Client socket - " + socket.isConnected());
                    OutputStream os = socket.getOutputStream();
                    InputStream is = null;
                    is = new ByteArrayInputStream(money.getBytes(StandardCharsets.UTF_8));
                    while ((len = is.read(buf)) != -1) {
                        os.write(buf, 0, len);
                    }
                    Log.d(TAG, "Client: Data written");
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                } finally {
                    if (socket.isConnected()) {
                        try {
                            Log.d(TAG, "Closing socked");
                            socket.close();
                        } catch (IOException e) {
                            // Give up
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
