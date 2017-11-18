//過去の投稿を確認するためのactivityを作成、機能は未実装。
//activity_toukou_pastも同時に作成。  三浦
package com.example.enpit_p15.mact_event;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Toukou_past extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toukou_past);  //activity_toukou_pastを呼び出す
        setTitle("投稿履歴");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_show_event,menu);

        MenuItem addEvent = menu.findItem(R.id.menu_item_add_event_sub);
        addEvent.setOnMenuItemClickListener(
          new MenuItem.OnMenuItemClickListener(){
            @Override
             public boolean onMenuItemClick(MenuItem menuItem){
                return true;
            }
        });

        final MenuItem search_button = menu.findItem(R.id.menu_item_search_sub);
        search_button.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem){
                        Intent intent_SeachActivity = new Intent(Toukou_past.this, SearchActivity.class);  //ToukouからToukou_pastに移動
                        startActivity(intent_SeachActivity);
                        return true;
                    }
                });



        return  true;
    }



}








