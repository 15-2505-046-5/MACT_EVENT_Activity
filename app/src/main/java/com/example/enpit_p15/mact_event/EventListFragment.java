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


public class EventListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Realm mRealm;
    private String temp;
    private String CalText;
    private String CateText;
    private String CostText;
    private String PrefectureText;
    private String KeyWord = "def";
    private String Keytxt;



    public EventListFragment() {
        // Required empty public constructor
    }

    public static EventListFragment newInstance(){
        EventListFragment fragment = new EventListFragment();
        return fragment;
    }

/*Realmの取得*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
    }
/*ここまで*/

/*Realmを閉じる処理*/
    @Override
    public void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }
/*ここまで*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  receive from MainActivity
        PrefectureText = getArguments().getString("PREF");
        CateText = getArguments().getString("CATE");
        CostText = getArguments().getString("COST");
        KeyWord = getArguments().getString("KEY");
        CalText= getArguments().getString("DATE");
        Keytxt = getArguments().getString("CONT");

        //temp = String.valueOf("きららファンタジア");     //titleの検索条件　完全一致

        View v = inflater.inflate(R.layout.fragment_event_list,container,false); //xmlファイルを適応して画面の作成
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());  //リスト表示するために必要なクラスのインスタンスを生成
        llm.setOrientation(LinearLayoutManager.VERTICAL);  //スクロールを縦に設定

        recyclerView.setLayoutManager(llm);  //リスト表示とスクロールを紐づけする
        //検索条件　.equalTo("id:schedule","**Text")
        //RealmResults<Schedule> diaries = mRealm.where(Schedule.class).findAll();  //データベースからリストを取得 検索条件の設定
        //EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
        //ここでデータが更新されるとアダプターも更新されるため、最新の状態が表示される

        //test
        //検索条件　.equalTo("id:schedule","**Text")

        if((KeyWord==null||KeyWord.length()==0)&&(CalText==null||CalText.length()==0||CalText.length()==6||CalText=="0/1/0")&&(Keytxt==null||Keytxt.length()==0)&&(CateText==null||CateText.length()==0)) {
            //検索ボックスと日付、キーワード、ジャンルが空
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord!=null||KeyWord.length()!=0)&&(CalText==null|| CalText.length()==6||CalText=="0/1/0")&&(Keytxt==null||Keytxt.length()==0)&&(CateText==null||CateText.length()==0)){
            //検索ボックスのみ入っている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("title", KeyWord).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord.length()==0)&&(CalText.length()!=0)&&(Keytxt==null|| Keytxt.length()==0)&&(CateText==null||CateText.length()==0)){
            //日付のみ入っている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).equalTo("date", CalText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord!=null||KeyWord.length()!=0)&&(CalText.length()!=0)&&(Keytxt==null||Keytxt.length()==0)&&(CateText==null||CateText.length()==0)){
            //検索ボックスと日付が入っている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("title", KeyWord).equalTo("date", CalText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord==null||KeyWord.length()==0)&&(CalText==null||CalText.length()==0||CalText.length()==6||CalText=="0/1/0")&&(Keytxt!=null||Keytxt.length()!=0)&&(CateText==null||CateText.length()==0)){
            //キーワードのみ入っている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("bodyText", Keytxt).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord!=null||KeyWord.length()!=0)&&(CalText==null||CalText.length()==0||CalText.length()==6||CalText=="0/1/0")&&(Keytxt!=null||Keytxt.length()!=0)&&(CateText==null||CateText.length()==0)){
            //検索ボックスとキーワードがはいっている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("bodyText", Keytxt).contains("title",KeyWord).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord==null||KeyWord.length()==0)&&(CalText!=null||CalText.length()!=0||CalText.length()!=6||CalText!="0/1/0")&&(Keytxt!=null||Keytxt.length()!=0)&&(CateText==null||CateText.length()==0)){
            //日付とキーワードがはいっている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("bodyText", Keytxt).equalTo("date",CalText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord==null||KeyWord.length()==0)&&(CalText!=null||CalText.length()!=0||CalText.length()!=6||CalText!="0/1/0")&&(Keytxt!=null||Keytxt.length()!=0)&&(CateText==null||CateText.length()==0)){
            //検索ボックスと日付、キーワードがはいっている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("bodyText", Keytxt).equalTo("date",CalText).contains("title",KeyWord).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord==null||KeyWord.length()==0)&&(CalText==null||CalText.length()==0||CalText.length()==6||CalText=="0/1/0")&&(Keytxt==null||Keytxt.length()==0)&&(CateText!=null||CateText.length()!=0)) {
            //ジャンルが入っている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).equalTo("category",CateText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord!=null||KeyWord.length()!=0)&&(CalText==null|| CalText.length()==6||CalText=="0/1/0")&&(Keytxt==null||Keytxt.length()==0)&&(CateText!=null||CateText.length()!=0)){
            //検索ボックスとジャンル入っている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("title", KeyWord).equalTo("category",CateText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord.length()==0)&&(CalText.length()!=0)&&(Keytxt==null|| Keytxt.length()==0)&&(CateText!=null||CateText.length()!=0)){
            //日付とジャンル入っている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).equalTo("date", CalText).equalTo("category",CateText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord!=null||KeyWord.length()!=0)&&(CalText.length()!=0)&&(Keytxt==null||Keytxt.length()==0)&&(CateText!=null||CateText.length()!=0)){
            //検索ボックスと日付とジャンルが入っている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("title", KeyWord).equalTo("date", CalText).equalTo("category",CateText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord==null||KeyWord.length()==0)&&(CalText==null||CalText.length()==0||CalText.length()==6||CalText=="0/1/0")&&(Keytxt!=null||Keytxt.length()!=0)&&(CateText!=null||CateText.length()!=0)){
            //キーワードとジャンル入っている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("bodyText", Keytxt).equalTo("category",CateText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord!=null||KeyWord.length()!=0)&&(CalText==null||CalText.length()==0||CalText.length()==6||CalText=="0/1/0")&&(Keytxt!=null||Keytxt.length()!=0)&&(CateText!=null||CateText.length()!=0)){
            //検索ボックスとキーワードとジャンルはいっている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("bodyText", Keytxt).contains("title",KeyWord).equalTo("category",CateText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord==null||KeyWord.length()==0)&&(CalText!=null||CalText.length()!=0||CalText.length()!=6||CalText!="0/1/0")&&(Keytxt!=null||Keytxt.length()!=0)&&(CateText!=null||CateText.length()!=0)){
            //日付とキーワードジャンルはいっている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("bodyText", Keytxt).equalTo("date",CalText).equalTo("category",CateText).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }else if((KeyWord==null||KeyWord.length()==0)&&(CalText!=null||CalText.length()!=0||CalText.length()!=6||CalText!="0/1/0")&&(Keytxt!=null||Keytxt.length()!=0)&&(CateText!=null||CateText.length()!=0)){
            //検索ボックスと日付、キーワードとジャンルがはいっている
            RealmResults<Schedule> diaries = mRealm.where(Schedule.class).contains("bodyText", Keytxt).equalTo("date",CalText).equalTo("category",CateText).contains("title",KeyWord).findAll();  //データベースからリストを取得 検索条件の設定
            EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);  //アダプターの生成、引数にはデータベースから取得したものを使う
            recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        }


        //ここでデータが更新されるとアダプターも更新されるため、最新の状態が表示される


        //recyclerView.setAdapter(adapter);  //作成したアダプターの設定
        return v;  //作成した画面を返す
    }

    /*フラグメントのライフサイクルメソッド*/
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
/*ここまで*/

/*フラグメントのライフサイクルメソッド*/
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
/*ここまで*/

    public interface OnFragmentInteractionListener {  //onAttach内で使用しているインターフェイスの定義
        void onAddEventSelected();  //日記の新規作成を行うメソッドの定義
    }

/*フラグメントのライフサイクルメソッド*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);  //オプションメニューの準備。これでonCreateOptionMenuが呼ばれる

    }
/*ここまで*/

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
/*ここまで*/

}
