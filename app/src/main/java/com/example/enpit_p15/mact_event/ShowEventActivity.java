/*閲覧機能の実装。ほかに記事の共有？と画像から主要職を取得して画像デザインに反映も実装？要らないと思われる。p329-p333
  Realmデータベースからの検索など重要そうなことを結構やっているので要確認*/
package com.example.enpit_p15.mact_event;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*記事の共有*/
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,mBodyText);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                /*ここまで*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRealm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        final long eventId = intent.getLongExtra(EVENT_ID, ERR_CD);

        TextView body = (TextView)findViewById(R.id.body);
        ImageView imageView = (ImageView) findViewById(R.id.toolbar_image);
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
            imageView.setImageBitmap(mBitmap);
        /*ここまで*/

            /*日記の画像から代表色を取得して画面に反映*/
            Palette palette = Palette.from(mBitmap).generate();

            int titleColor = palette.getLightVibrantColor(Color.WHITE);
            int bodyColor = palette.getDarkMutedColor(Color.BLACK);
            int scrimColor = palette.getMutedColor(Color.DKGRAY);
            int iconColor = palette.getLightMutedColor(Color.LTGRAY);

            layout.setExpandedTitleColor(titleColor);
            layout.setContentScrimColor(scrimColor);
            scrollView.setBackgroundColor(bodyColor);
            body.setTextColor(titleColor);
            fab.setBackgroundTintList(ColorStateList.valueOf(iconColor));
            /*ここまで*/
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }
}