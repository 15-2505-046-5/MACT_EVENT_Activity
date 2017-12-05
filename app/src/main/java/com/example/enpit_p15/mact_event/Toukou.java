//フォーマットを選択する画面のactivityを作成、フォーマットの選択は未実装、過去の投稿を閲覧する画面に遷移できるようにした。
// activity_toukou.xmlも同時に作成　　三浦

package com.example.enpit_p15.mact_event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class Toukou extends AppCompatActivity implements EventListFragment.OnFragmentInteractionListener{

    private EventListFragment.OnFragmentInteractionListener mListener;
    private Realm mRealm;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_toukou);  //activity_toukouを呼び出す
        setTitle("フォーマットを選択");

        ShowInputEvent();

        /*過去の投稿に遷移するボタンの設定*/
        Button send_past = (Button) findViewById(R.id.send_past);
        send_past.setOnClickListener(new View.OnClickListener() {  //ボタンがクリックされた時の挙動を設定
            @Override
            public void onClick(View view) {
                Intent intent_Toukou_past = new Intent(Toukou.this, Toukou_past.class);  //ToukouからToukou_pastに移動
                startActivity(intent_Toukou_past);
            }
        });

        //選択後に遷移するボタンの設定
        Button send_ago = (Button) findViewById(R.id.button_ago);
        send_past.setOnClickListener(new View.OnClickListener() {  //ボタンがクリックされた時の挙動を設定
            @Override
            public void onClick(View view) {
                mListener.onAddEventSelected();         //addEventSelectedの実行、フラグメントの呼び出し
            }
        });


    }

    //フラグメントの断続表示を解消
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRealm.close();
        mListener = null;
    }



    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_event_format, menu);


        final MenuItem search_button = menu.findItem(R.id.menu_item_search_format);
        search_button.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent_SeachActivity = new Intent(Toukou.this, SearchActivity.class);  //ToukouからSearchActivityに移動
                        startActivity(intent_SeachActivity);
                        return true;
                    }
                });

        final  MenuItem return_button = menu.findItem(R.id.menu_item_return_format);
        return_button.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            finish();
                            return true;
                        }
                });


        return true;
        ////
    }

    //試しにShowを追加、メイン画面が投稿画面と重なる事態に
    private void ShowInputEvent(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("InputEventFragment");
        if(fragment == null){
            fragment = new EventListFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.content, fragment, "InputEventFragment");
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