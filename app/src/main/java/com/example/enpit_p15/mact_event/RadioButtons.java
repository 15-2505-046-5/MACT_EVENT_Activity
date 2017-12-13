package com.example.enpit_p15.mact_event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RadioButtons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_buttons);



        //public void onRadioButtonClicked(View v){
        //ラジオボタンの実装
        //    TextView tv = (TextView)findViewById(R.id.TextView_test);
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
                                switch (checkedId){
                                    case R.id.radioButton:
                                        tv.setText("フォーマット１");
                                        break;
                                    case R.id.radioButton2:
                                        tv.setText("フォーマット２");
                                        break;
                                    case R.id.radioButton3:
                                        tv.setText("フォーマット３");
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
    }
}
