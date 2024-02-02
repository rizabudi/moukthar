package com.ot.grhq.client;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.net.URI;
import java.net.URISyntaxException;

public class MainService extends Service {

    private final String SERVICE_RESTART_INTENT = "com.ot.grhq.receiver.restartservice";
    private static final String SERVER_URI = "ws://192.168.8.102:8080";

    private static WebSocketClient client;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            client = new WebSocketClient(getApplicationContext(), new URI(SERVER_URI));

            if (!client.isOpen())
                client.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Log.d("eeee", "First");
        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.d("eeee", "The service is working");
                handler.postDelayed(this, delay);
            }
        }, delay);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent restartService = new Intent(SERVICE_RESTART_INTENT);
        sendBroadcast(restartService);
    }
}
