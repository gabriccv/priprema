package com.example.shop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ColorCheckingService extends Service {

    private static final String CHANNEL_ID = "ColorCheckingServiceChannel";
    private Handler handler = new Handler();
    private Runnable checkSwitchState = new Runnable() {
        @Override
        public void run() {
            if (MainActivity.isSwitchOn) {
                sendNotification();
            } else {
                sendBroadcast(new Intent("com.example.shop.SWITCH_OFF"));
            }
            handler.postDelayed(this, 10000); // Check every minute
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        handler.post(checkSwitchState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(checkSwitchState);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Switch State")
                .setContentText("The switch is ON")
                .setSmallIcon(R.drawable.baseline_notifications_none_24)
                .build();

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(2, notification);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Color Checking Service Channel",
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
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//
//public class ColorCheckingService extends Service {
//    private static final String CHECK_CHANNEL_ID = "CheckServiceChannel";
//    private Handler handler;
//    private Runnable checkSwitchRunnable;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        createNotificationChannel();
//
//        handler = new Handler();
//        checkSwitchRunnable = new Runnable() {
//            @Override
//            public void run() {
//                checkSwitchState();
//                handler.postDelayed(this, 60000); // Check every minute
//            }
//        };
//        handler.post(checkSwitchRunnable);
//
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (handler != null && checkSwitchRunnable != null) {
//            handler.removeCallbacks(checkSwitchRunnable);
//        }
//    }
//
//    private void checkSwitchState() {
//        boolean isSwitchOn = MainActivity.isSwitchOn;
//
//        if (isSwitchOn) {
//            sendNotification("Switch is ON");
//        } else {
//            sendBroadcast(new Intent("com.example.shop.SWITCH_OFF"));
//        }
//    }
//
//    private void sendNotification(String message) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHECK_CHANNEL_ID)
//                .setContentTitle("Check Service")
//                .setContentText(message)
//                .setSmallIcon(R.drawable.baseline_check_24);
//
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        if (manager != null) {
//            manager.notify(2, builder.build());
//        }
//    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel serviceChannel = new NotificationChannel(
//                    CHECK_CHANNEL_ID,
//                    "Check Service Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            if (manager != null) {
//                manager.createNotificationChannel(serviceChannel);
//            }
//        }
//    }
//}
