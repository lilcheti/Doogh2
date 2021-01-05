package com.milcrypto.doogh;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    ArrayList<ArrayList<String>> episodes=new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    String img;
    String imdbid;
    int s;
    public SeriesAdapter(Context context, Season season, String img,String imdbid,int s ) {
        this.inflater = LayoutInflater.from(context) ;
        this.context = context;
        season.getUrl().removeAll(new ArrayList<>());
        this.episodes.addAll(season.getUrl());
        this.img=img;
        this.imdbid=imdbid;
        this.s=s;
    }

    @NonNull
    @Override
    public SeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeriesAdapter.ViewHolder(inflater.inflate(R.layout.series_row , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesAdapter.ViewHolder holder, final int position) {
        if(!episodes.get(position).isEmpty()) {
            holder.lin.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(img)
                .into(holder.imageView);
        holder.play.setOnClickListener(view -> {
            Bundle b = new Bundle();
            b.putStringArrayList("links",episodes.get(position));
            b.putString("imdbid",imdbid);
            b.putInt("season",s);
            b.putInt("episode",position+1);
            Intent intent = new Intent(view.getContext(), LinkListActivity.class);
            intent.putExtras(b);
            view.getContext().startActivity(intent);
        });
        int e = position+1;
        holder.description.setText("Episode "+e);

        }
    }


    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView description;
        ImageButton imageButton, play;
        LinearLayout lin ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView4);
            play = itemView.findViewById(R.id.playy);
            description =  itemView.findViewById(R.id.textView4);
            imageButton = itemView.findViewById(R.id.dlSeriesButton);
            lin = itemView.findViewById(R.id.lin);
        }


    }


}
