/*このアダプターはcard_layoutの表示内容を設定する */
package com.example.enpit_p15.mact_event;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


/**
 * Created by enPiT-P15 on 2017/11/15.
 */

public class EventRealmAdapter extends RealmRecyclerViewAdapter<Schedule,EventRealmAdapter.EventViewHolder> {
    Context context;


/*card_layoutを定義。recyclerを継承*/
    public static class EventViewHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        protected TextView bodyText;
        protected TextView date;
        protected ImageView photo;
        protected TextView category;
        protected TextView prefecture;
        protected TextView cost;
        protected TextView eventId; //ID表示用　試験段階

        public EventViewHolder(View itemView){
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            bodyText = (TextView)itemView.findViewById(R.id.body);
            date = (TextView)itemView.findViewById(R.id.date);
            photo = (ImageView)itemView.findViewById(R.id.format_photo);
            eventId = (TextView)itemView.findViewById(R.id.EventId);//ID表示用　試験段階
        }
    }
/*ここまで*/

    public EventRealmAdapter(@NonNull Context context,
                             @Nullable OrderedRealmCollection<Schedule> date,boolean autoUpdate){
        super(date, autoUpdate);
        this.context = context;
    }

/*card_layoutの作成の時に呼ばれる*/
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);  //card_layoutのビューを作成
        final EventViewHolder holder = new EventViewHolder(itemView);  //上のビューを引数にしてインスタンスを作成

        /*一覧からタップした時の処理  わかりにくいから教科書見たほうが良い  p334*/
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int position = holder.getAdapterPosition();  //リストの何番目を指定しているかを取得
                Schedule event = getData().get(position);  //getメソッドで指定した位置のデータを取り出す
                long eventId = event.id;

                /*ShowEventActivityを開く->IDを取得してShowEventActivityに渡す*/
                Intent intent = new Intent(context,ShowEventActivity.class);
                intent.putExtra(ShowEventActivity.EVENT_ID,eventId);
                context.startActivity(intent);
                /*ここまで*/
            }
        });
        /*ここまで*/
        return holder;  //作成したインスタンスを返す
    }
/*ここまで*/

/*card_layoutの内容を表示するときに呼ばれる*/
    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Schedule event = getData().get(position);  //アダプター内のデータ一覧を取得
        holder.title.setText(event.title);  //取得したデータをセットする
        holder.bodyText.setText(event.bodyText);
        holder.date.setText(event.date);
        /*画像のやつ*/
        if (event.image != null && event.image.length != 0){
            Bitmap bmp = MyUtils.getImageFromByte(event.image);
            holder.photo.setImageBitmap(bmp);
        }
        /*ここまで*/

    }
/*ここまで*/

}