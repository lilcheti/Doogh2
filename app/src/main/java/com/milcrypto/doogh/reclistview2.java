package com.milcrypto.doogh;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class reclistview2 extends RecyclerView.Adapter<reclistview2.ViewHolder> {


    String[] movieGenre = {
            "Action",
            "Adventure",
            "Animation",
            "Biography",
            "Comedy",
            "Crime",
            "Documentary",
            "Drama",
            "Family",
            "Fantasy",
            "Film Noir",
            "History",
            "Horror",
            "Music",
            "Musical",
            "Mystery",
            "Romance",
            "Sci-Fi",
            "Short Film",
            "Sport",
            "Superhero",
            "Thriller",
            "War",
            "Western"
    };
    String[] seriesGenre = {
            "Action",
            "Adventure",
            "Animation",
            "Biography",
            "Comedy",
            "Crime",
            "Documentary",
            "Drama",
            "Family",
            "Fantasy",
            "Game Show",
            "History",
            "Horror",
            "Music",
            "Musical",
            "Mystery",
            "News",
            "Reality-TV",
            "Romance",
            "Sci-Fi",
            "Sport",
            "Superhero",
            "Talk Show",
            "Thriller",
            "War",
            "Western",
    };
    private Context context;
    private ItemClickListener mClickListener;
    List<String> mData;

    public reclistview2(Context context, List<String> data) {
        this.context = context;
        this.mData = data;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String[] kk = mData.get(position).split("__");
        System.out.println(kk[0]+"----------------------");
        System.out.println(kk[1]);
        if (kk[1] != null) {
            if (!kk[1].equals("[]") && !kk[1].equals("null")) {
                holder.textView.setVisibility(View.VISIBLE);
                holder.recyclerView.setVisibility(View.VISIBLE);
                holder.textView.setText(kk[0]);
                SeriesRecViewAdapter adapterr = new SeriesRecViewAdapter(context);
                adapterr.setNew(kk[1]);
                holder.recyclerView.setAdapter(adapterr);
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void downloadJSON(final String urlWebService, final ViewHolder holder, final int position) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                System.out.println(s+"---------------------------------------------------------------------------------------------");


            }


            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           recyclerView = itemView.findViewById(R.id.vertRec);
            textView = itemView.findViewById(R.id.txt);
        }
    }

    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
