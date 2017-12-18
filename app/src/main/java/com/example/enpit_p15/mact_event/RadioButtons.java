package com.example.enpit_p15.mact_event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RadioButtons extends AppCompatActivity {

    private int formatdata;
    //private EventApplication formatdata_grobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_buttons);
        setTitle("フォーマットを選択");



        //public void onRadioButtonClicked(View v){
        //ラジオボタンの実装
         //   TextView tv = (TextView)findViewById(R.id.textView_test);
        //   RadioButton radioButton = (RadioButton)findViewById(R.id.radioButton);
        //    RadioButton radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        //    RadioButton radioButton3 = (RadioButton)findViewById(R.id.radioButton3);
        //    boolean checked = ((RadioButton)v).isChecked();
        //   switch (v.getId()){
        //       case R.id.radioButton:
        //           if(checked){  tv.setText("フォーマット１");  }
        //         break;
        //       case R.id.radioButton2:
        //           if(checked){  tv.setText("フォーマット２");  }
        //           break;
        //      case R.id.radioButton3:
        //          if(checked){  tv.setText("フォーマット３");  }
        //          break;
        //  }
        //以下、マスター（P418）、反応なし
        ((RadioGroup) findViewById(R.id.radioGroup))
                .setOnCheckedChangeListener(
                        new RadioGroup.OnCheckedChangeListener(){


                            @Override
                            public void onCheckedChanged(RadioGroup group,int checkedId){
                                TextView tv = (TextView)findViewById(R.id.textView_test);
                                //TextView tv2 = (TextView)findViewById(R.id.textView_test3);
                                switch (checkedId){
                                    case R.id.radioButton:
                                        //tv.setText("");
                                        formatdata  = 1;
                                        //formatdata_grobal = (EventApplication) this.getApplication();
                                        tv.setText(String.valueOf(formatdata));
                                        break;
                                    case R.id.radioButton2:
                                        //tv.setText("フォーマット２");
                                        formatdata  = 2;
                                        tv.setText(String.valueOf(formatdata));

                                        break;
                                    case R.id.radioButton3:
                                        //tv.setText("フォーマット３");
                                        formatdata = 3;
                                        tv.setText(String.valueOf(formatdata));
                                        break;
                                }
                            }
                       }
                );
        //以下、反応なしバージョン（初めての…P130）、念のために残す
        //RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup);
        //radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
        //    @Override
        //    public void onCheckedChanged(RadioGroup group,int checkedId){
        //        RadioButton radioButton = (RadioButton) findViewById(checkedId);
        //        String value = radioButton.getText().toString();
        //    }
        //});
        //}


                        /*投稿詳細画面に遷移するボタンの設定*/
        Button button_formatSaved = (Button) findViewById(R.id.button_format_saved);
        button_formatSaved.setOnClickListener(new View.OnClickListener() {  //ボタンがクリックされた時の挙動を設定
            @Override
            public void onClick(View view) {
                if(formatdata==1) {
                    Intent intent_formatSelected = new Intent(RadioButtons.this, Toukou.class);  //RadioButtonsからToukou1に移動
                    intent_formatSelected.putExtra("FormatData", formatdata);       //activity間のデータの受け渡し部分
                    startActivity(intent_formatSelected);
                }else if(formatdata==2){
                    Intent intent_formatSelected = new Intent(RadioButtons.this, Toukou2.class);  //RadioButtonsからToukou2に移動
                    intent_formatSelected.putExtra("FormatData", formatdata);       //activity間のデータの受け渡し部分
                    startActivity(intent_formatSelected);
                }else if(formatdata==3){
                    Intent intent_formatSelected = new Intent(RadioButtons.this, MainActivity.class);  //RadioButtonsからToukou3に移動
                    intent_formatSelected.putExtra("FormatData", formatdata);       //activity間のデータの受け渡し部分
                    startActivity(intent_formatSelected);
                }
            }
        });

                /*過去の投稿に遷移するボタンの設定*/
        //Button send_past = (Button) findViewById(R.id.button_past);
        //send_past.setOnClickListener(new View.OnClickListener() {  //ボタンがクリックされた時の挙動を設定
        //    @Override
        //    public void onClick(View view) {
        //        Intent intent_Toukou_past = new Intent(RadioButtons.this, Toukou_past.class);  //RadioButtonsからToukou_pastに移動
        //        intent_Toukou_past.putExtra("FormatData", formatdata);
        //        startActivity(intent_Toukou_past);
        //    }
        //});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_format_select,menu);

        MenuItem addEvent = menu.findItem(R.id.menu_item_search_formatselected);
        addEvent.setOnMenuItemClickListener(
                new MenuItem.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem){
                        Intent intent_Toukou = new Intent(RadioButtons.this, SearchActivity.class);  //RadioButtonstからSearchに移動
                        startActivity(intent_Toukou);
                        return true;
                    }
                });



        MenuItem return_button = menu.findItem(R.id.menu_item_return_formatselected);
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
