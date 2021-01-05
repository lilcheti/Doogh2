package com.milcrypto.doogh;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SeriesRecViewAdapterlist extends RecyclerView.Adapter<SeriesRecViewAdapterlist.ViewHolder> {

    private List<New> newtv = new ArrayList<>();
    private Context context;

    public SeriesRecViewAdapterlist(Context context) {
        this.context = context;
    }

    public SeriesRecViewAdapterlist(List<New> newtv, Context context) {
        this.newtv = newtv;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

      try {



            final Gson gson = new Gson();
            holder.parent.setOnClickListener(view -> {
                    //Intent intent = new Intent(view.getContext(), SeriesActivity.class);
                    //intent.putExtra("imgurl", Series.get(position).getPicurl());
                   // intent.putExtra("seriesName", Series.get(position).getName());
                    //intent.putExtra("seriesObject" , gson.toJson(Series.get(position)) );
                    //view.getContext().startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putString("imdbid",newtv.get(position).getImdbid());
                Navigation.findNavController(view).navigate(R.id.action_mylist2_to_show, bundle);


            });
        if (newtv.get(position).getPicurl() !=null){
        holder.txtName.setText(newtv.get(position).getName());
        Picasso.get()
                .load(newtv.get(position).getPicurl())
                .resize(dpToPx(133), dpToPx(186))
                .centerCrop()
                .into(holder.image);

            }
      } catch (Exception e) {
          e.printStackTrace();
      }

    }



    private int dpToPx(int dp) {
        float density = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
    @Override
    public int getItemCount() {
        return newtv.size();
    }

    public void setNew(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<New>>() {
        }.getType();
        Collection<New> seriezz = gson.fromJson(json, collectionType);
        if (seriezz!=null){
        newtv.addAll(seriezz);}
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;

        private CardView parent;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.imagee);

        }
    }


}
