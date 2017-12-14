package com.example.enpit_p15.mact_event;

/**
 * Created by enPiT-P15 on 2017/12/13.
 */

import android.app.Application;

public class RadioID extends Application {

    private String testString = "default";

    @Override
    public void onCreate() {

    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String str) {
        testString = str;
    }
}
