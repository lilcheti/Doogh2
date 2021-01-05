package com.milcrypto.doogh;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Mylist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mylist extends Fragment {
    SharedPreferences preff;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Mylist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mylist.
     */
    // TODO: Rename and change types and number of parameters
    public static Mylist newInstance(String param1, String param2) {
        Mylist fragment = new Mylist();
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
        preff = this.getActivity().getSharedPreferences("id",0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_mylist, container, false);
        RecyclerView rec = view.findViewById(R.id.list);
        SeriesRecViewAdapterlist adapter = new SeriesRecViewAdapterlist(getContext());
        rec.setAdapter(adapter);
        RequestQueue mQueuee = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest stringRequestt = new StringRequest(Request.Method.GET, "http://1milcrypto.com/getlist.php?id="+preff.getInt("id",0), response -> {
            System.out.println(response);
            adapter.setNew(response);
            rec.setLayoutManager(new GridLayoutManager(getContext(),4));
        }, error -> error.printStackTrace());
        mQueuee.add(stringRequestt);

        return view;
    }
}