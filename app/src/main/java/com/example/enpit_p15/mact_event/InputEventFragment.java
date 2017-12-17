package com.example.enpit_p15.mact_event;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;

import io.realm.Realm;

import static android.app.Activity.RESULT_OK;

//画像の表示、教P164,P91,305
public class InputEventFragment extends Fragment{
    private static final String EVENT_ID = "EVENT_ID";


    private static final int REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private static final String TAG = InputEventFragment.class.getSimpleName();

    private long mEventId;
    private Realm mRealm;
    private EditText mTitleEdit;
    private EditText mBodyEdit;
    private EditText mDate;
    private ImageView mEventImage;
    private String str_ymd;
    private  String year;

    public static InputEventFragment newInstance(long eventId) {  //フラグメントのインスタンスを作成する
        /*引数として受け取った日記のIDをフラグメントに保存する*/
        InputEventFragment fragment = new InputEventFragment();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
        /*ここまで*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {  //上で保存した日記のIDを変数に格納
            mEventId = getArguments().getLong(EVENT_ID);
        }
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input_event, container, false);
        mTitleEdit = (EditText) v.findViewById(R.id.title);
        mBodyEdit = (EditText) v.findViewById(R.id.bodyEditText);
        //mDate = (EditText) v.findViewById(R.id.date);
        mEventImage = (ImageView) v.findViewById(R.id.format_photo);

        //スピナーの実装
        //Button btn = (Button) v.findViewById(R.id.Hanei); //spinnerを反映させるボタンの準備

        String[] ctg = getResources().getStringArray(R.array.category_list); //スピナーのリスト（ジャンル）
        String[] prf = getResources().getStringArray(R.array.prefecture_list);//スピナーのリスト（都道府県）
        String[] cost = getResources().getStringArray(R.array.cost_list);//スピナーのリスト(予算)
        String[] year_s = getResources().getStringArray(R.array.year_list); //スピナーのリスト（年）
        String[] month_s = getResources().getStringArray(R.array.month_list);//スピナーのリスト（月）
        String[] day_s = getResources().getStringArray(R.array.day_list);//スピナーのリスト(日)

        final Spinner spinner_c = (Spinner) v.findViewById(R.id.genreSpinner);//投稿用スピナー（ジャンル）
        final Spinner spinner_p = (Spinner)v.findViewById(R.id.PrefectureSpinner);//(都道府県)
        final Spinner spinner_ct = (Spinner)v.findViewById(R.id.costSpinner);//(予算)
        final Spinner spinner_y = v.findViewById(R.id.yearSpinner);//投稿用スピナー（年）
        final Spinner spinner_m = v.findViewById(R.id.monthSpinner);//(月)
        final Spinner spinner_d = v.findViewById(R.id.daySpinner);//(日)

        //String selected_y = spinner_y.getSelectedItem().toString();
        //String selected_m = spinner_m.getSelectedItem().toString();
        //String selected_d = spinner_d.getSelectedItem().toString();

        //str_ymd = selected_y +"/" +selected_m+"/" + selected_d;


        /*<String> adapter_c = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, ctg);//スピナー展開時の表示方法の指定
        ArrayAdapter<String> adapter_p = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, prf);
        ArrayAdapter<String> adapter_ct = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, cost);
        ArrayAdapter<String> adapter_y = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, year_s);
        ArrayAdapter<String> adapter_m = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, month_s);
        ArrayAdapter<String> adapter_d = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, day_s);

        adapter_c.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line); //アダプターをスピナーに設定:ジャンル
        adapter_p.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line); //都道府県
        adapter_ct.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line); //予算
        adapter_y.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line); //アダプターをスピナーに設定:年
        adapter_m.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line); //月
        adapter_d.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line); //日


        spinner_c.setAdapter(adapter_c);//イベントリスナーの登録:ジャンル
        spinner_p.setAdapter(adapter_p);//イベントリスナーの登録:都道府県
        spinner_ct.setAdapter(adapter_ct);//イベントリスナーの登録:予算
        spinner_y.setAdapter(adapter_y);//イベントリスナーの登録:年
        spinner_m.setAdapter(adapter_m);//イベントリスナーの登録:月
        spinner_d.setAdapter(adapter_d);//イベントリスナーの登録:日*/


     /*   String str_c = spinner_c.getSelectedItem().toString();
        String str_p = spinner_p.getSelectedItem().toString();
        String str_ct = spinner_ct.getSelectedItem().toString();
        String str_y = spinner_y.getSelectedItem().toString();
        String str_m = spinner_m.getSelectedItem().toString();
        String str_d = spinner_d.getSelectedItem().toString();

        str_ymd = str_y + str_m + str_d;    */


        Button mButton = v.findViewById(R.id.Hanei);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mRealm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Schedule event = realm.where(Schedule.class).equalTo("id", mEventId).findFirst();

                            String selected_c = spinner_c.getSelectedItem().toString();//spinnerの値取得
                            String selected_p = spinner_p.getSelectedItem().toString();
                            String selected_ct = spinner_ct.getSelectedItem().toString();
                            String selected_y = spinner_y.getSelectedItem().toString();
                            String selected_m = spinner_m.getSelectedItem().toString();
                            String selected_d = spinner_d.getSelectedItem().toString();

                            str_ymd = selected_y +"/" +selected_m+"/" + selected_d;
                            event.date = str_ymd.toString();  //年月日の中身をデータベースに格納
                            event.category_R = selected_c;
                            event.prefecture_R = selected_p;
                            event.cost_R = selected_ct;
                        }
                });
            }
        });

       /* spinner_y.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = (String) parent.getItemAtPosition(position);
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule event = realm.where(Schedule.class).equalTo("id", mEventId).findFirst();
                        event.year = year.toString();  //yearの中身をデータベースに格納
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        mEventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestReadStorage(view);
            }
        });  //タップした時の処理



        mTitleEdit.addTextChangedListener(new TextWatcher() {  //入力内容をデータベースに格納する
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule event = realm.where(Schedule.class).equalTo("id", mEventId).findFirst();
                        event.title = s.toString();  //titleの中身をデータベースに格納
                    }
                });
            }
        });

        mBodyEdit.addTextChangedListener(new TextWatcher() {  //入力内容をデータベースに格納する
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                mRealm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Schedule event = realm.where(Schedule.class).equalTo("id", mEventId).findFirst();
                        event.bodyText = s.toString();  //bodyTextの中身を格納
                    }
                });
            }
        });

        //mDate.addTextChangedListener(new TextWatcher() {  //入力内容をデータベースに格納する
        //    @Override
        //    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //    }

         //   @Override
         //   public void onTextChanged(CharSequence s, int start, int before, int count) {
         //   }

         //   @Override
         //   public void afterTextChanged(final Editable s) {
         //       mRealm.executeTransactionAsync(new Realm.Transaction() {
         //           @Override
         //           public void execute(Realm realm) {
         //              Schedule event = realm.where(Schedule.class).equalTo("id", mEventId).findFirst();
         //               event.date = s.toString();  //dateのデータベースに格納
         //           }
         //      });
         //   }
        //});



        return v;

    }

   // @Override
   // public void onActivityCreated(Bundle savedInstanceState) {
   //     super.onActivityCreated(savedInstanceState);


    //    Button mButton = (Button) getView().findViewById(R.id.Hanei);

    //    mButton.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View v) {

    //            Spinner spinner_c = (Spinner) v.findViewById(R.id.genreSpinner);//投稿用スピナー（ジャンル）
    //            Spinner spinner_p = (Spinner)v.findViewById(R.id.PrefectureSpinner);//(都道府県)
    //            Spinner spinner_ct = (Spinner)v.findViewById(R.id.costSpinner);//(予算)
    //            Spinner spinner_y = (Spinner) v.findViewById(R.id.yearSpinner);//投稿用スピナー（年）
    //            Spinner spinner_m = (Spinner)v.findViewById(R.id.monthSpinner);//(月)
    //            Spinner spinner_d = (Spinner)v.findViewById(R.id.daySpinner);//(日)


     //           String str_c = spinner_c.getSelectedItem().toString();
     //           String str_p = spinner_p.getSelectedItem().toString();
     //           String str_ct = spinner_ct.getSelectedItem().toString();
     //           String str_y = spinner_y.getSelectedItem().toString();
     //           String str_m = spinner_m.getSelectedItem().toString();
     //           String str_d = spinner_d.getSelectedItem().toString();

     //           final String str_ymd = str_y + str_m + str_d;

     //           mRealm.executeTransactionAsync(new Realm.Transaction() {
     //               @Override
     //               public void execute(Realm realm) {
     //                   Schedule event = realm.where(Schedule.class).equalTo("id", mEventId).findFirst();
     //                   event.date = str_ymd.toString();  //dateのデータベースに格納
     //               }
     //           });
      //      }
     //   });
    //}



/*確認ウインドウの表示*/
    private void requestReadStorage(View view){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,R.string.rationale, Snackbar.LENGTH_LONG).show();
            }

            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);

        }else {
            pickImage();
        }
    }
/*ここまで*/

/*端末内の画像を取得する処理*/
    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        getString(R.string.pick_image)

                ),
                REQUEST_CODE);
    }
/*ここまで*/

    @Override
    public void onActivityResult(int requestCode, int resultCode ,Intent date){
        super.onActivityResult(requestCode,resultCode,date);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){

            /*画像関連のやつ　p308、p313*/
            Uri uri = (date == null) ? null : date.getData();
            if (uri != null){
                try{
                    Bitmap img = MyUtils.getImageFromStream(
                            getActivity().getContentResolver(), uri);
                    mEventImage.setImageBitmap(img);
                }catch (IOException e){
                    e.printStackTrace();
                }
                mRealm.executeTransactionAsync(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Schedule event = realm.where(Schedule.class)
                                .equalTo("id",mEventId)
                                .findFirst();
                        BitmapDrawable bitmap =
                                (BitmapDrawable) mEventImage.getDrawable();
                        byte[] bytes = MyUtils.getByteFromImage(bitmap.getBitmap());
                        if (bytes != null && bytes.length > 0){
                            event.image = bytes;
                        }
                    }
                });
            }
            /*ここまで*/
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        /*画像関連のやつ　p308、p314*/
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length != 1 ||
                    grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Snackbar.make(mEventImage, R.string.permission_deny,
                        Snackbar.LENGTH_LONG).show();
            }else {
                pickImage();
            }
        }
        /*ここまで*/

    }

    /*オプションメニューのやつ　p318、p319　　メニューの項目をインスタンス化して設定した*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_input_event,menu);
        MenuItem saveEvent = menu.findItem(R.id.menu_item_save_event);

        MyUtils.tintMenuIcon(getContext(),saveEvent,android.R.color.white);
    }
/*ここまで*/

    /*オプションメニューのやつ　p318、p319　　メニューがタップされたときに呼び出される*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){  //メニュー項目のIDを取得してswitch文に使用
            case R.id.menu_item_save_event:  //保存がタップされた時の処理
                //getActivity().getFragmentManager().beginTransaction().remove().commit();


                getFragmentManager().popBackStack();
             default:
        }

        return false;
    }

  //  @Override
  //  public void onClick(View v) {
  //
    //    Spinner spinner_c = (Spinner) v.findViewById(R.id.genreSpinner);//投稿用スピナー（ジャンル）
    //    Spinner spinner_p = (Spinner)v.findViewById(R.id.PrefectureSpinner);//(都道府県)
    //    Spinner spinner_ct = (Spinner)v.findViewById(R.id.costSpinner);//(予算)
    //    Spinner spinner_y = (Spinner) v.findViewById(R.id.yearSpinner);//投稿用スピナー（年）
    //    Spinner spinner_m = (Spinner)v.findViewById(R.id.monthSpinner);//(月)
    //    Spinner spinner_d = (Spinner)v.findViewById(R.id.daySpinner);//(日)


     //   String str_c = spinner_c.getSelectedItem().toString();
     //   String str_p = spinner_p.getSelectedItem().toString();
     //   String str_ct = spinner_ct.getSelectedItem().toString();
     //   String str_y = spinner_y.getSelectedItem().toString();
     //   String str_m = spinner_m.getSelectedItem().toString();
     //   String str_d = spinner_d.getSelectedItem().toString();

     //   final String str_ymd = str_y + str_m + str_d;

     //   mRealm.executeTransactionAsync(new Realm.Transaction() {
     //                  @Override
     //                  public void execute(Realm realm) {
     //                     Schedule event = realm.where(Schedule.class).equalTo("id", mEventId).findFirst();
     //                      event.date = str_ymd.toString();  //dateのデータベースに格納
     //                  }
     //             });
    //}
}