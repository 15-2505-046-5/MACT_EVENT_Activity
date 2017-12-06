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
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

import io.realm.Realm;

import static android.app.Activity.RESULT_OK;


public class InputEventFragment extends Fragment {
    private static final String EVENT_ID = "EVENT_ID";
    private static final int REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;


    private long mEventId;
    private Realm mRealm;
    private EditText mTitleEdit;
    private EditText mBodyEdit;
    private EditText mDate;
    private ImageView mEventImage;

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
    public void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input_event,container,false);
        mTitleEdit = (EditText) v.findViewById(R.id.title);
        mBodyEdit = (EditText)v.findViewById(R.id.bodyEditText);
        mDate = (EditText)v.findViewById(R.id.date);
        mEventImage = (ImageView)v.findViewById(R.id.format_photo);

        mEventImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                requestReadStorage(view);
            }
        });  //タップした時の処理

        mTitleEdit.addTextChangedListener(new TextWatcher() {  //入力内容をデータベースに格納する
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(final Editable s) {
                mRealm.executeTransactionAsync(new Realm.Transaction(){
                    @Override
                    public void execute(Realm realm){
                        Schedule event = realm.where(Schedule.class).equalTo("id",mEventId).findFirst();
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

        mDate.addTextChangedListener(new TextWatcher() {  //入力内容をデータベースに格納する
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
                        event.date = s.toString();  //dateのデータベースに格納
                    }
                });
            }
        });


        return v;
    }

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

}