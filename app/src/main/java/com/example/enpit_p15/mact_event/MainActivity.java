package com.example.enpit_p15.mact_event;

import android.content.Intent;
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
        implements EventListFragment.OnFragmentInteractionListener {

    private Realm mRealm;
    private String DateTxt;
    private String CostTxt;
    private String CateTxt;
    private String PrefTxt;
    private String KeyTxt;
    private String ContTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("いぽかつ");

        mRealm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        //変数の受け取り　searchActivity
        //CostTxt = intent.getStringExtra("COST");
        //CateTxt = intent.getStringExtra("CATE");
        //PrefTxt = intent.getStringExtra("PREF");
        KeyTxt = intent.getStringExtra("KEY");
        DateTxt = intent.getStringExtra("DATE");
        ContTxt = intent.getStringExtra("CONT");

        //createTestDate();
        showEventList();  //EventListFragmentを表示する処理
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

/*テストのために用意してたやつ？いらないと思われる*/
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
/*ここまで*/

/*EventListFragmentを呼び出す。　なんかいろいろやってる p293*/
    private void showEventList(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("EventListFragment");
        if(fragment == null){
            //Fragmentへ変数の受け渡し
            Bundle args = new Bundle();
            //args.putString("CATE",CateTxt);
            //args.putString("PREF",PrefTxt);
            //args.putString("COST",CostTxt);
            args.putString("KEY",KeyTxt);
            args.putString("DATE",DateTxt);
            args.putString("CONT",ContTxt);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            EventListFragment fragment1 = new EventListFragment();
            fragment1.setArguments(args);
            transaction.add(R.id.content,fragment1,"EventListFragment");
            transaction.commit();

            //本来の形
            //fragment = new EventListFragment();
            //FragmentTransaction transaction = manager.beginTransaction();
            //transaction.add(R.id.content, fragment, "EventListFragment");
            //transaction.commit();
        }
    }
/*ここまで*/


/*日記追加の処理*/
    @Override
    public void onAddEventSelected(){
        //新規イベント追加処理をここに
        /*新しい日記のIDを使ってScheduleオブジェクトを作成してデータベースに保存*/
        mRealm.beginTransaction();
        Number maxId = mRealm.where(Schedule.class).max("id");
        long nextId = 0;
        if(maxId != null){
            nextId = maxId.longValue() + 1;
        }
        Schedule event = mRealm.createObject(Schedule.class, new Long(nextId));
        event.date = new SimpleDateFormat("MMM d", Locale.US).format(new Date());
        mRealm.commitTransaction();
        /*ここまで*/

        InputEventFragment inputEventFragment =
                InputEventFragment.newInstance(nextId);  //インスタンスを作成してフラグメントの表示処理を開始
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, inputEventFragment, "InputEventFragment");  //アクティビティにフラグメントの追加
        transaction.addToBackStack(null);  //戻るボタンを押した時に戻る機能の実装？　p322
        transaction.commit();
    }
/*ここまで*/

}

///