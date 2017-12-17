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
import android.widget.Toast;

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
        setTitle("詳細入力");

        //activity間のデータの受け取り部分、テスト用
        Intent intent = getIntent();
        int formatID = intent.getIntExtra("FormatData", 0);
        //Toast.makeText(Toukou.this, formatID, Toast.LENGTH_SHORT).show();


        Button save_button = (Button) findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(Toukou.this, getResources().getString(R.string.save_fragment), Toast.LENGTH_SHORT).show();

                Intent intent_MainActivity = new Intent(Toukou.this, MainActivity.class);  //ToukouからMainActivityに移動
                startActivity(intent_MainActivity);
            }
        });

        //TextView textView = (TextView) findViewById(R.id.textView_test2);
       // textView.setText(String.valueOf(formatID));



        // ShowInputEvent(); 一時的に逃がす


        //これより上のものは不要であるが、念のために残す。解説は上のものを参照
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
                InputEventFragment.newInstance(nextId);  //インスタンスを作成してフラグメントの表示処理を開始

        FragmentManager manager = getSupportFragmentManager();


        Bundle args = new Bundle();
        args.putInt("FormatID",formatID);
        //inputEventFragment.setArguments(args);

        FragmentTransaction transaction = manager.beginTransaction();


        transaction.replace(R.id.content, inputEventFragment, "InputEventFragment");  //アクティビティにフラグメントの追加
        transaction.addToBackStack(null);  //戻るボタンを押した時に戻る機能の実装？　p322
        transaction.commit();


        // Fragment fragment = manager.findFragmentByTag("InputEventFragment");
        //if(fragment == null) {
            //Fragmentへ変数の受け渡し
            //Bundle args = new Bundle();

            //args.putInt("FormatID", formatID);

            //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //InputEventFragment fragment1 = new InputEventFragment();
            //inputEventFragment.setArguments(args);
            //transaction.replace(R.id.content, inputEventFragment, "InputEventFragment");  //アクティビティにフラグメントの追加
            //transaction.addToBackStack(null);  //戻るボタンを押した時に戻る機能の実装？　p322
            //transaction.commit();


        //}
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

            //getMenuInflater().inflate(R.menu.menu_input_event, menu);
            getMenuInflater().inflate(R.menu.menu_event_format, menu);


           final MenuItem search_button = menu.findItem(R.id.menu_item_search_format);
           search_button.setOnMenuItemClickListener(
                   new MenuItem.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem menuItem) {
                           Intent intent_SearchActivity = new Intent(Toukou.this, SearchActivity.class);  //ToukouからSearchActivityに移動
                           startActivity(intent_SearchActivity);
                           return true;
                       }
                   });

           final  MenuItem return_button = menu.findItem(R.id.menu_item_return_format);
           return_button.setOnMenuItemClickListener(
                   new MenuItem.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem menuItem) {
                           getFragmentManager().popBackStack();
                           finish();
                           return true;
                       }
                   });

            /*final  MenuItem save_button = menu.findItem(R.id.menu_item_save_event);
            save_button.setOnMenuItemClickListener(
                    new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            Toast.makeText(Toukou.this, getResources().getString(R.string.save_fragment), Toast.LENGTH_SHORT).show();

                            Intent intent_MainActivity = new Intent(Toukou.this, MainActivity.class);  //ToukouからMainActivityに移動
                            startActivity(intent_MainActivity);
                            return true;
                        }
                    });*/

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