package com.milcrypto.doogh;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static java.lang.System.in;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Show#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Show extends Fragment {
    ImageView add;
    SharedPreferences preff;
    Boolean isMyList = false;
    String imdbid,mylist;
    ArrayList<ArrayList<String>> joj = new ArrayList<>();
    Spinner spinner;
    ArrayList<Season> seasons = new ArrayList<>();
    RecyclerView seriesRecView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Show() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Show.
     */
    // TODO: Rename and change types and number of parameters
    public static Show newInstance(String param1, String param2) {
        Show fragment = new Show();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        imdbid = getArguments().getString("imdbid");
        mylist = ((kirtuin) this.getActivity().getApplication()).getMyList();
        preff = this.getActivity().getSharedPreferences("id",0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show, container, false);
        add = view.findViewById(R.id.add);
        if (mylist.contains(imdbid)){
            add.setImageResource(R.drawable.ic_baseline_check_24);
            isMyList = true;
        }

        spinner = view.findViewById(R.id.spinner1);
        seriesRecView = view.findViewById(R.id.serrr);
        TextView name = view.findViewById(R.id.namee);
        TextView meta = view.findViewById(R.id.meta);
        TextView rate = view.findViewById(R.id.rate);
        TextView plot = view.findViewById(R.id.plot);
        ImageView bigpic = view.findViewById(R.id.imageView3);


        RequestQueue mQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        String url = "http://1milcrypto.com/select.php?i=" + imdbid.replaceAll(" ", "+");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, response -> {
            try {
                name.setText(response.getString("name"));
                meta.setText(response.getString("year")+"  "+response.getString("agerate"));
                rate.setText("IMDB : "+response.getString("imdbrating")+"/10");
                plot.setText(response.getString("description"));
                Picasso.get()
                        .load(response.getString("picurl"))
                        .resize(getResources().getDisplayMetrics().widthPixels, dpToPx(250))
                        .centerCrop()
                        .into(bigpic);
                setSpinner(response);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> error.printStackTrace());
        mQueue.add(jsonObjectRequest);
        add.setOnClickListener(v -> {
            if(isMyList){
                mylist = mylist.replace(","+imdbid,"");
                RequestQueue mQueuee = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
                StringRequest stringRequestt = new StringRequest(Request.Method.GET, "http://1milcrypto.com/setusr.php?id="+preff.getInt("id",0)+"&l="+mylist, response -> {
                    ((kirtuin) this.getActivity().getApplication()).setMyList(mylist);
                }, error -> error.printStackTrace());
                mQueuee.add(stringRequestt);
                isMyList = false;
                add.setImageResource(R.drawable.ic_baseline_add_24);
            }else {
                mylist = mylist + ","+imdbid;
                RequestQueue mQueuee = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
                StringRequest stringRequestt = new StringRequest(Request.Method.GET, "http://1milcrypto.com/setusr.php?id="+preff.getInt("id",0)+"&l="+mylist, response -> {
                    ((kirtuin) this.getActivity().getApplication()).setMyList(mylist);
                }, error -> error.printStackTrace());
                mQueuee.add(stringRequestt);
                isMyList = true;
                add.setImageResource(R.drawable.ic_baseline_check_24);
            }

        });
        return view;
    }
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    private Season fsonparse(String fson){

        ArrayList<ArrayList<String>> ep = new ArrayList<>();
        for (int j = 0 ;j<35;j++){
            ep.add(new ArrayList<>());
        }
        String[] jj = fson.split(",");
        for (int i = 1 ; i<jj.length;i++){
            String[] kk =jj[i].split("[|]");
            System.out.println(kk[0]);
            System.out.println(kk[1]+"---------------------->");
            ep.get(Integer.parseInt(kk[0])).add(kk[1]);
        }
        ep.trimToSize();
        System.out.println(ep.size());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ep.removeIf(e -> e.isEmpty());
        }
        for (Iterator<ArrayList<String>> it = ep.iterator(); it
                .hasNext();) {
            List<String> elem = it.next();
            if (elem.isEmpty()) {
                it.remove();
            }
        }
        Season oo = new Season(ep);
        return oo;
    }
    private void setSpinner(JSONObject json) {
        // final ArrayList<String> items = new ArrayList<>();
        class spinna extends AsyncTask<Void, Void, ArrayList<String>> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected ArrayList<String> doInBackground(Void... voids) {
                ArrayList<String> items = new ArrayList<>();
                for (int i=1;i<=20;i++) {
                    String yo = null;
                    try {
                        yo = json.getString("s"+i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!yo.isEmpty()){
                        seasons.add(fsonparse(yo));
                        items.add("Season "+i);

                    }
                }

                return items;

            }

            @Override
            protected void onPostExecute(ArrayList<String> items) {
                super.onPostExecute(items);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {

                        try {
                            String gg = json.getString("picurl");
                            SeriesAdapter adapter = new SeriesAdapter( getContext(),seasons.get(i),gg,imdbid,i+1);
                            seriesRecView.setAdapter(adapter);
                            seriesRecView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                });
            }
        }
        spinna kkkk = new spinna();
        kkkk.execute();


    }
}