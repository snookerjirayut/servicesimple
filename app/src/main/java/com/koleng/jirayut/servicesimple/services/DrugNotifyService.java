package com.koleng.jirayut.servicesimple.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.koleng.jirayut.servicesimple.receiver.NotifyReceiver;

import java.util.Calendar;

/**
 * Created by jirayut.k on 2016-12-27.
 */

public class DrugNotifyService extends IntentService {

    private static String SCHEDULE = "schedule";

    public DrugNotifyService() {
        this(DrugNotifyService.class.getName());
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DrugNotifyService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String time = intent.getExtras().getString(SCHEDULE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        String[] arr = time.split(":");

        String hour = arr[0];
        String min = arr[1];

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(min));

        Intent aIntent = new Intent(this.getApplicationContext(), NotifyReceiver.class);
        aIntent.putExtra(SCHEDULE , time);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, aIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 0, alarmIntent);

    }
}
