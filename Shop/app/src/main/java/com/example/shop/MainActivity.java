//package com.example.shop;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.Switch;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class MainActivity extends AppCompatActivity {
//    public static boolean isSwitchOn = false;
//    private Switch musicSwitch;
//    private SwitchOffReceiver switchOffReceiver;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        musicSwitch = findViewById(R.id.musicSwitch);
//        Button checkButton = findViewById(R.id.checkButton);
//        Button secondButton = findViewById(R.id.secondButton);
//        RelativeLayout mainLayout = findViewById(R.id.mainLayout);
//
//        musicSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                startService(new Intent(MainActivity.this, MusicService.class));
//                isSwitchOn = true;
//            } else {
//                stopService(new Intent(MainActivity.this, MusicService.class));
//                isSwitchOn = false;
//            }
//        });
//
//        checkButton.setOnClickListener(v -> {
//            startService(new Intent(MainActivity.this, ColorCheckingService.class));
//        });
//
//        switchOffReceiver = new SwitchOffReceiver();
//        IntentFilter filter = new IntentFilter("com.example.shop.SWITCH_OFF");
//        registerReceiver(switchOffReceiver, filter);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(switchOffReceiver);
//    }
//}
//
//
//
package com.example.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static boolean isSwitchOn = false;
    private Switch musicSwitch;
    private SwitchOffReceiver switchOffReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicSwitch = findViewById(R.id.musicSwitch);
        Button checkButton = findViewById(R.id.checkButton);
        Button secondButton = findViewById(R.id.secondButton);
        RelativeLayout mainLayout = findViewById(R.id.mainLayout);

        musicSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startService(new Intent(MainActivity.this, MusicService.class));
                isSwitchOn = true;
            } else {
                stopService(new Intent(MainActivity.this, MusicService.class));
                isSwitchOn = false;
            }
        });

        checkButton.setOnClickListener(v -> {
            startService(new Intent(MainActivity.this, ColorCheckingService.class));
        });

        switchOffReceiver = new SwitchOffReceiver();
        IntentFilter filter = new IntentFilter("com.example.shop.SWITCH_OFF");
        registerReceiver(switchOffReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(switchOffReceiver);
    }
}

