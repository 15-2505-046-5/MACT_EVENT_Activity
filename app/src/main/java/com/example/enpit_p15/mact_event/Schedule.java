package com.example.enpit_p15.mact_event;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Schedule extends RealmObject {
    @PrimaryKey
    public long id;
    public String title;
    public String bodyText;
    public String date;
   // public String category_R;
   // public String prefecture_R;
   // public String cost_R;
    public byte[] image;

}
