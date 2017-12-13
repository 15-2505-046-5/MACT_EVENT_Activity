package com.example.enpit_p15.mact_event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.realm.Realm;

/**
 * Created by enPiT-P16 on 2017/12/13.
 */

public class ResultActivity extends AppCompatActivity implements ResultListFragment.OnFragmentInteractionListener {

    private Realm mRealm;
    public String Catetxt;
    public String Costtxt;
    public String Preftxt;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRealm = Realm.getDefaultInstance();

        //SearchActivity からの変数受け
        Intent intent = getIntent();
        Preftxt = intent.getStringExtra("PREF");
        Costtxt = intent.getStringExtra("COST");
        Catetxt = intent.getStringExtra("CATE");

        showDiaryList();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

    private void showDiaryList() {
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = manager.findFragmentByTag("ResultListFragment");
        if(fragment == null){
            fragment = new ResultListFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.content,fragment,"ResultListFragment");
            transaction.commit();
        }

    }


}
