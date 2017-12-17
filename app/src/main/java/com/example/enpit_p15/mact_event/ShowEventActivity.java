/*閲覧機能の実装。ほかに記事の共有？と画像から主要職を取得して画像デザインに反映も実装？要らないと思われる。p329-p333
  Realmデータベースからの検索など重要そうなことを結構やっているので要確認*/
/*fabを削除。画像はlayoutからは消したけどimage関連のコードは使うかもしれないからコメントアウト*/
package com.example.enpit_p15.mact_event;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import io.realm.Realm;

public class ShowEventActivity extends AppCompatActivity {
    public static final String EVENT_ID = "EVENT_ID";
    private static final long ERR_CD = -1;

    private String mBodyText;
    private Realm mRealm;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRealm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        final long eventId = intent.getLongExtra(EVENT_ID, ERR_CD);

        TextView body = (TextView)findViewById(R.id.body);
        //ImageView imageView = (ImageView) findViewById(R.id.toolbar_image);
        NestedScrollView scrollView =
                (NestedScrollView)findViewById(R.id.scroll_view);

        Schedule event = mRealm.where(Schedule.class).equalTo("id",eventId).findFirst();  //IDをもとにRealmデータベースを検索してデータの取得

        CollapsingToolbarLayout layout =
                (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        layout.setTitle(event.title);

        mBodyText = event.bodyText;  //変数に取得した本文を格納

        body.setText(event.bodyText);  //本文を表示

        /*画像のやつ*/
        byte[] bytes =  event.image;
        if(bytes != null && bytes.length > 0){
            mBitmap = MyUtils.getImageFromByte(bytes);
            //imageView.setImageBitmap(mBitmap);
        /*ここまで*/

        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }
}