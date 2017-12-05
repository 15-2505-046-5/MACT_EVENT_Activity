package com.example.enpit_p15.mact_event;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        setTitle("検索");
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onDateSet(DatePicker view,int year,int monthOfYear, int dayOfMonth){
        String str = String.format(Locale.US, "%d/%d/%d",year,monthOfYear,dayOfMonth);
        textView.setText(str);
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment  =new DatePick();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem addEvent = menu.findItem(R.id.menu_item_add_event_main);
        addEvent.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem){
                        Intent intent_Toukou = new Intent(SearchActivity.this, Toukou.class);  //SearchActivitytからToukouに移動
                        startActivity(intent_Toukou);
                        return true;
                    }
                });

        final MenuItem search_button = menu.findItem(R.id.menu_item_search_main);
        search_button.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem){
                        return true;
                    }
                });


        MenuItem return_button = menu.findItem(R.id.menu_item_finish2);
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
