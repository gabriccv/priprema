package com.example.shop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MusicService extends Service {

    private static final String CHANNEL_ID = "MusicServiceChannel";
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        showNotification();

        mediaPlayer = MediaPlayer.create(this, R.raw.music); // Ensure you have sample_music.mp3 in res/raw folder
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("PAUSE")) {
                mediaPlayer.pause();
            } else if (intent.getAction().equals("PLAY")) {
                mediaPlayer.start();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {
        Log.d("MusicService", "Prikazuje se notifikacija"); // Dodajte log za prikaz notifikacije

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.setAction("PLAY");
        PendingIntent playPendingIntent = PendingIntent.getService(
                this,
                1,
                playIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        Intent pauseIntent = new Intent(this, MusicService.class);
        pauseIntent.setAction("PAUSE");
        PendingIntent pausePendingIntent = PendingIntent.getService(
                this,
                2,
                pauseIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Music Service")
                .setContentText("Playing music")
                .setSmallIcon(R.drawable.baseline_music_note_24)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.baseline_play_arrow_24, "Play", playPendingIntent)
                .addAction(R.drawable.baseline_pause_24, "Pause", pausePendingIntent)
                .build();

        startForeground(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}

//package com.example.shop;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.os.Build;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//
//public class MusicService extends Service {
//
//    private static final String CHANNEL_ID = "MusicServiceChannel";
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        createNotificationChannel();
//        showNotification();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // Start playing music
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // Stop playing music
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    private void showNotification() {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                this,
//                0,
//                notificationIntent,
//                PendingIntent.FLAG_IMMUTABLE // Add this flag
//        );
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Music Service")
//                .setContentText("Playing music")
//                .setSmallIcon(R.drawable.baseline_music_note_24)
//                .setContentIntent(pendingIntent)
//                .build();
//
//        startForeground(1, notification);
//    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    CHANNEL_ID,
//                    "Music Service Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            if (manager != null) {
//                manager.createNotificationChannel(serviceChannel);
//            }
//        }
//    }
//}
