package com.example.mitra.revo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Created by Mitra on 4/4/2015.
 */
    public class Alarm extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            int icon = R.drawable.bell16;
            CharSequence tickerText = "Hello you have a notification";
            long when = System.currentTimeMillis();

            //Notification notification = new Notification(icon, tickerText,when );

            CharSequence contentTitle = "Memory helper";
            CharSequence contentText = "Please check your custom task in memory helper application";


            //notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
            final int NOTIF_ID = 1234;
            NotificationManager notofManager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
            // Notification note = new Notification(R.drawable.face,"NEW ACTIVITY", System.currentTimeMillis());
            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context,0, notificationIntent, 0);
            Notification notification = new Notification(icon, tickerText,when );
            //Notification notification1 = new Notification(R.drawable.icon, "Wake up alarm", System.currentTimeMillis());
            notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
            notification.flags = Notification.FLAG_INSISTENT;
            notification.defaults |= Notification.DEFAULT_SOUND;
            //notification.setLatestEventInfo(context, "My Activity", "This will runs on button click", contentIntent);
            notofManager.notify(NOTIF_ID,notification);
        }
    }
