package com.example.enpit_p15.mact_event;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import io.realm.Realm;

public class ShowEvent_4 extends AppCompatActivity {

    public static final String EVENT_ID = "EVENT_ID";
    private static final long ERR_CD = -1;

    private String mBodyText;
    private String mTitleText;
    private String mDateText;
    private String mCategory;
    private Realm mRealm;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRealm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        final long eventId = intent.getLongExtra(EVENT_ID, ERR_CD);

        Schedule event = mRealm.where(Schedule.class).equalTo("id",eventId).findFirst();  //IDをもとにRealmデータベースを検索してデータの取得

        TextView body_s = (TextView)findViewById(R.id.body_show4);
        mBodyText = event.bodyText;  //変数に取得した本文を格納
        body_s.setText(event.bodyText);  //本文を表示

        TextView title_s = (TextView)findViewById(R.id.title_show4);
        mTitleText = event.title;  //変数に取得した本文を格納
        title_s.setText(event.title);  //本文を表示
        setTitle(event.title);

        TextView date_s = (TextView)findViewById(R.id.date_show4);
        mDateText = event.date;  //変数に取得した本文を格納
        date_s.setText(event.date);  //本文を表示

        TextView category_s = (TextView)findViewById(R.id.category_show4);
        mCategory = event.category;  //変数に取得した本文を格納
        category_s.setText(event.category);  //本文を表示
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_show_event,menu);

        MenuItem addEvent = menu.findItem(R.id.menu_item_add_event_sub);
        addEvent.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem){
                        Intent intent_Toukou = new Intent(ShowEvent_4.this, RadioButtons.class);  //ShowEvent_4からToukouに移動
                        startActivity(intent_Toukou);
                        return true;
                    }
                });

        final MenuItem search_button = menu.findItem(R.id.menu_item_search_sub);
        search_button.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem){
                        Intent intent_Toukou = new Intent(ShowEvent_4.this, SearchActivity.class);  //ShowEvent_4からToukouに移動
                        startActivity(intent_Toukou);
                        return true;
                    }
                });


        MenuItem return_button = menu.findItem(R.id.menu_item_finish);
        return_button.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem){
                        finish();
                        return true;
                    }
                });

        return  true;
    }
}