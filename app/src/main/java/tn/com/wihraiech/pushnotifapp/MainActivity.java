package tn.com.wihraiech.pushnotifapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.GregorianCalendar;

import tn.com.wihraiech.pushnotifapp.email.EmailActivity;
import tn.com.wihraiech.pushnotifapp.pushnotif.AlertReceiver;
import tn.com.wihraiech.pushnotifapp.pushnotif.InfoNotifActivity;
import tn.com.wihraiech.pushnotifapp.sendtomany.SendMailActivity;

public class MainActivity extends AppCompatActivity {

    private Button bt_envoyer, bt_show, bt_stop, bt_alarme;

    NotificationManager notificationManager;

    boolean isNotifActivated = false;

    int notifID = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        bt_show = (Button) findViewById(R.id.bt_show);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_alarme = (Button) findViewById(R.id.bt_alert);

        /*
        Notification notif = builder.build();
        NotificationManager manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(8, notif);
*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void showNotif(View v){


        //////////RDV Notification
        NotificationCompat.Builder notifBuilder = new
                NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("Message")
                .setContentText("New Message")
                .setTicker("Alert New Message")
                .setSmallIcon(R.drawable.ic_launcher);

        Intent intent = new Intent(this, InfoNotifActivity.class);

        //When you use the click on back of ur device
        TaskStackBuilder tStackBuilder = TaskStackBuilder.create(this);

        tStackBuilder.addParentStack(InfoNotifActivity.class);

        tStackBuilder.addNextIntent(intent);

        //if this intent exist it will be updated
        PendingIntent pendingIntent = tStackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notifBuilder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifID, notifBuilder.build());

        isNotifActivated = true;

    }

    public void stopNotif(View v){
        if(isNotifActivated){
            notificationManager.cancel(notifID);
        }

    }


    public void setAlarme(View v){

        Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;

        Intent alertIntent = new Intent(this, AlertReceiver.class);

        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,
                PendingIntent.getBroadcast(this, 1, alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT));

        Toast.makeText(MainActivity.this, "Alarm!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_email) {
            startActivity(new Intent(this, EmailActivity.class));
        }else if (id == R.id.action_send_to_many) {
            startActivity(new Intent(this, SendMailActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
