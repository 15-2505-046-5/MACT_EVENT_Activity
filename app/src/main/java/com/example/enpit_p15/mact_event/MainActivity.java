package com.example.enpit_p15.mact_event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity
        implements EventListFragment.OnFragmentInteractionListener{

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRealm = Realm.getDefaultInstance();

        //createTestDate();
        showEventList();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

    private void createTestDate(){
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                Number maxId = mRealm.where(Schedule.class).max("id");
                long nextId = 0;
                if(maxId != null){
                    nextId = maxId.longValue() + 1;
                }
                Schedule event = realm.createObject(Schedule.class, new Long(nextId));
                event.title = "テストタイトル";
                event.bodyText = "テスト本文です。";
                event.date = "Feb 22";
            }
        });
    }

    private void showEventList(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("EventListFragment");
        if(fragment == null){
            fragment = new EventListFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.content, fragment, "EventListFragment");
            transaction.commit();
        }
    }

    @Override
    public void onAddEventSelected(){
        //新規イベント追加処理をここに
        mRealm.beginTransaction();
        Number maxId = mRealm.where(Schedule.class).max("id");
        long nextId = 0;
        if(maxId != null){
            nextId = maxId.longValue() + 1;
        }
        Schedule event = mRealm.createObject(Schedule.class, new Long(nextId));
        event.date = new SimpleDateFormat("MMM d", Locale.US).format(new Date());
        mRealm.commitTransaction();

        InputEventFragment inputEventFragment =
                InputEventFragment.newInstance(nextId);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, inputEventFragment,
                "InputEventFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

///