package tn.com.wihraiech.pushnotifapp.pushnotif;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import tn.com.wihraiech.pushnotifapp.MainActivity;
import tn.com.wihraiech.pushnotifapp.R;

/**
 * Created by lenovo on 09/07/2016.
 */
public class AlertReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotification(context, "Times Up", "5 Seconds Has Passed", "Alert");
    }

    public void createNotification(Context context, String msg, String msgText, String msgalert){

        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(msg)
                .setContentText(msgText)
                .setTicker(msgalert)
                .setSmallIcon(R.drawable.alarm);

        mBuilder.setContentIntent(notificIntent);

        mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }

}
