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



    public static class EventViewHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        protected TextView bodyText;
        protected TextView date;
        protected ImageView photo;

        public EventViewHolder(View itemView){
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            bodyText = (TextView)itemView.findViewById(R.id.body);
            date = (TextView)itemView.findViewById(R.id.date);
            photo = (ImageView)itemView.findViewById(R.id.format_photo);
        }
    }

    public EventRealmAdapter(@NonNull Context context,
                             @Nullable OrderedRealmCollection<Schedule> date,boolean autoUpdate){
        super(date, autoUpdate);
        this.context = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        final EventViewHolder holder = new EventViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int position = holder.getAdapterPosition();
                Schedule event = getData().get(position);
                long eventId = event.id;

                Intent intent = new Intent(context,ShowEventActivity.class);
                intent.putExtra(ShowEventActivity.EVENT_ID,eventId);
                context.startActivity(intent);
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Schedule event = getData().get(position);
        holder.title.setText(event.title);
        holder.bodyText.setText(event.bodyText);
        holder.date.setText(event.date);
        if (event.image != null && event.image.length != 0){
            Bitmap bmp = MyUtils.getImageFromByte(event.image);
            holder.photo.setImageBitmap(bmp);
        }

    }

}