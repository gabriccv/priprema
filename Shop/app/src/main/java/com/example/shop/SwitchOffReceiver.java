package com.example.shop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RelativeLayout;

public class SwitchOffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;
            RelativeLayout mainLayout = activity.findViewById(R.id.mainLayout);
            mainLayout.setBackgroundColor(Color.YELLOW);
        }
    }
}
