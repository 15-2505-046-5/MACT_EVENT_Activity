package com.example.enpit_p15.mact_event;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import io.realm.Realm;

public class ShowEvent_2 extends AppCompatActivity {

    public static final String EVENT_ID = "EVENT_ID";
    private static final long ERR_CD = -1;

    private String mBodyText;
    private String mTitleText;
    private String mDateText;
    private Realm mRealm;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRealm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        final long eventId = intent.getLongExtra(EVENT_ID, ERR_CD);

        Schedule event = mRealm.where(Schedule.class).equalTo("id",eventId).findFirst();  //IDをもとにRealmデータベースを検索してデータの取得

        TextView body_s = (TextView)findViewById(R.id.body_show);
        mBodyText = event.bodyText;  //変数に取得した本文を格納
        body_s.setText(event.bodyText);  //本文を表示

        TextView title_s = (TextView)findViewById(R.id.title_show);
        mTitleText = event.title;  //変数に取得した本文を格納
        title_s.setText(event.title);  //本文を表示

        TextView date_s = (TextView)findViewById(R.id.date_show);
        mDateText = event.date;  //変数に取得した本文を格納
        date_s.setText(event.date);  //本文を表示
    }
}
