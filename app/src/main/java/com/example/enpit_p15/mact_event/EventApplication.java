package com.example.enpit_p15.mact_event;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by enPiT-P15 on 2017/11/15.
 */

public class EventApplication extends Application {

    //private String testString = "default";

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig
                = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    //public String getTestString() {
      //  return testString;
   // }

    //public void setTestString(String str) {
      //  testString = str;
    //}
}

