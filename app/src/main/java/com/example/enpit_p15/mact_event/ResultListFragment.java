package com.example.enpit_p15.mact_event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;


public class ResultListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Realm mRealm;
    private String CateText;
    private String CostText;
    private String PrefectureText;
    private String KeyWord;

    public ResultListFragment() {
        // Required empty public constructor
    }

    public static ResultListFragment newInstance() {
        ResultListFragment fragment = new ResultListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm  = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

/*ここまで*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  receive from ResultActivity
        PrefectureText = getArguments().getString("PREF");
        CateText = getArguments().getString("CATEGORY");
        CostText = getArguments().getString("COST");
        KeyWord = getArguments().getString("KEY");

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_result_list,container,false);
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recycler);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        //equalTo()で検索条件追加
        RealmResults<Schedule> results = mRealm.where(Schedule.class).findAll();

        EventRealmAdapter adapter =
                new EventRealmAdapter(getActivity(),results,true);

        recyclerView.setAdapter(adapter);


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        //void onAddDiarySelected();
    }

    /*フラグメントのライフサイクルメソッド*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);  //オプションメニューの準備。これでonCreateOptionMenuが呼ばれる
        //ResultActivity から　値受け取り

    }

    /*オプションメニューのやつ　p318、p319　　メニューの項目をインスタンス化して設定した*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_event_list,menu);
        MenuItem addEvent = menu.findItem(R.id.menu_item_add_event);
        MenuItem deleteAll = menu.findItem(R.id.menu_item_delete_all);
        MenuItem searchEvent = menu.findItem(R.id.menu_item_search);



        MyUtils.tintMenuIcon(getContext(),addEvent,android.R.color.white);
        MyUtils.tintMenuIcon(getContext(),deleteAll,android.R.color.white);
        MyUtils.tintMenuIcon(getContext(),searchEvent,android.R.color.white);

    }
/*ここまで*/

    /*オプションメニューのやつ　p318、p319　　メニューがタップされたときに呼び出される*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){  //メニュー項目のIDを取得してswitch文に使用
            case R.id.menu_item_add_event:  //追加がタップされた時の処理-1
                if (mListener != null){
                    Intent intent_toukou = new Intent(this.getActivity(), RadioButtons.class);
                    startActivity(intent_toukou);
                    // mListener.onAddEventSelected();
                }
                return true;

            case R.id.menu_item_delete_all:
                final RealmResults<Schedule> diaries =
                        mRealm.where(Schedule.class).findAll();
                mRealm.executeTransaction(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        diaries.deleteAllFromRealm();
                    }
                });
                return true;
            case R.id.menu_item_search:  //検索がタップされた時の処理
                if(mListener != null){
                    Intent intent_search = new Intent(this.getActivity(),SearchActivity.class);
                    //Intent intent = new Intent(EventListFragment.this, Toukou.class);
                    startActivity(intent_search);
                }
                return  true;

        }
        return  false;
    }

}
