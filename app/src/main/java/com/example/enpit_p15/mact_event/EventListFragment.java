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

    public EventListFragment() {
        // Required empty public constructor
    }

    public static EventListFragment newInstance(){
        EventListFragment fragment = new EventListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_list,container,false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        RealmResults<Schedule> diaries = mRealm.where(Schedule.class).findAll();
        EventRealmAdapter adapter = new EventRealmAdapter(getActivity(), diaries, true);

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
        void onAddEventSelected();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_event_list,menu);
        MenuItem addEvent = menu.findItem(R.id.menu_item_add_event);
        MenuItem deleteAll = menu.findItem(R.id.menu_item_delete_all);
        MenuItem searchEvent = menu.findItem(R.id.menu_item_search);
        MenuItem addEvent2 = menu.findItem(R.id.menu_item_add2_even2);


        MyUtils.tintMenuIcon(getContext(),addEvent,android.R.color.white);
        MyUtils.tintMenuIcon(getContext(),deleteAll,android.R.color.white);
        MyUtils.tintMenuIcon(getContext(),searchEvent,android.R.color.white);
        MyUtils.tintMenuIcon(getContext(),addEvent2,android.R.color.white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_item_add_event:
                if (mListener != null){
                    Intent intent_toukou = new Intent(this.getActivity(), Toukou.class);
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
            case R.id.menu_item_search:
                if(mListener != null){
                    Intent intent_search = new Intent(this.getActivity(),SearchActivity.class);
                    //Intent intent = new Intent(EventListFragment.this, Toukou.class);
                    startActivity(intent_search);
                }
                return  true;
            case R.id.menu_item_add2_even2:
                if(mListener != null){
                    mListener.onAddEventSelected();
                }
                return true;
        }
        return  false;
    }


}
