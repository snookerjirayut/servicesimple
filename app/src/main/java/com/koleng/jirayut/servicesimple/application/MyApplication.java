package com.koleng.jirayut.servicesimple.application;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;

import com.koleng.jirayut.servicesimple.services.DrugNotifyService;

import io.realm.Realm;

/**
 * Created by jirayut.k on 2016-12-27.
 */

public class MyApplication extends Application {

    private static MyApplication singleton;
    private static Realm database;

    public static MyApplication getInstance() {
        return singleton;
    }

    public static Realm getRealm() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        //init database Realm
        Realm.init(this.getApplicationContext());
        database = Realm.getDefaultInstance();

        //start service
        Intent serviceIntent = new Intent(this.getApplicationContext() , DrugNotifyService.class);
        this.getApplicationContext().startService(serviceIntent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
