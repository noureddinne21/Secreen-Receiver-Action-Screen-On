package com.nouroeddinne.trysamoethink;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class SecreenReceiver extends BroadcastReceiver {
    private static boolean state = true;
    private static final String CHANNEL_ID = "ScreenNotificationChannel";
    int n = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
            Log.d("TAG", "onReceive: SCREEN ON ");
            state = true;
            Toast.makeText(context, "Screen ON", Toast.LENGTH_SHORT).show();
//            sendNotification(context);
            n++;
        }
    }

    private void sendNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Screen On Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your notification icon
                .setContentTitle("Screen Status")
                .setContentText("The screen is ON! "+n)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET) // Hide content on the lock screen
                .setSilent(true);

        notificationManager.notify(1, builder.build());
    }

    public static boolean isScreenOn() {
        return state;
    }
}
