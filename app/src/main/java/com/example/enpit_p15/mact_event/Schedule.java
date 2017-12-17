package com.example.enpit_p15.mact_event;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Schedule extends RealmObject {
    @PrimaryKey
    public long id;
    public String title;
    public String bodyText;
    public String date;
    public byte[] image;
    public String cost;
    public String category;
    public String prefecture;

}
