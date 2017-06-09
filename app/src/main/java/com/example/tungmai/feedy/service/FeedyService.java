package com.example.tungmai.feedy.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.api.ConnectSever;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TungMai on 5/20/2017.
 */

public class FeedyService extends Service {
//    private MediaPlayer mediaPlayer;

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(ConnectSever.IP_SEVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void call(Object... args) {
            String message = args[0].toString();
//            Log.e("message", message);
            try {
                JSONObject jsonObject = new JSONObject(message);
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                initNotification(title,content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initNotification(String title, String content) {
        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(content);
//        builder.setOngoing(true);
//        Intent intent = new Intent(this, MyActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        Notification notification = builder.build();
        manager.notify(213, notification);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.coemcho);
        mSocket.connect();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        mediaPlayer.start();
        mSocket.on("server_send_notifi", onMessage);
        return START_STICKY;
    }




}
