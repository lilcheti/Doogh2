package com.milcrypto.doogh;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Home extends Fragment implements reclistview2.ItemClickListener{
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
    List<String> data ;
    RecyclerView seriesrecview;
    int nobat = 0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
         seriesrecview = view.findViewById(R.id.seriesrecview);
         data=new ArrayList<>();
        reclistview2 adapterr = new reclistview2(this.getContext(),data);
        seriesrecview.setAdapter(adapterr);
        seriesrecview.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        for (int i = 0 ;i<seriesGenre.length;i++) {
            RequestQueue mQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
            String url = "http://1milcrypto.com/newtv.php?g=" + seriesGenre[i].replaceAll(" ", "+");
            String ll = seriesGenre[i];
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                data.add(ll+"__"+response);
                adapterr.notifyItemInserted(data.size()-1);
            }, error -> error.printStackTrace());
            mQueue.add(stringRequest);
        }
        TextView mylist = view.findViewById(R.id.mylist);
        mylist.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_mylist2);
        });
       return view;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}