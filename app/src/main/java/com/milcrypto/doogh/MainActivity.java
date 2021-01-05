package com.milcrypto.doogh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("Doogh", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.botnav);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            RequestQueue mQueue = VolleySingleton.getInstance(this).getRequestQueue();
            String url = "http://1milcrypto.com/user.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                if(!response.isEmpty()) {
                    SharedPreferences sett = getSharedPreferences("id", 0);
                    sett.edit().putInt("id", Integer.parseInt(response)).apply();
                    prefs.edit().putBoolean("firstrun", false).commit();
                }
                System.out.println(response);
            }, error -> error.printStackTrace());
            mQueue.add(stringRequest);

        }


        SharedPreferences preff = getSharedPreferences("id",0);
        RequestQueue mQueuee = VolleySingleton.getInstance(this).getRequestQueue();
        StringRequest stringRequestt = new StringRequest(Request.Method.GET, "http://1milcrypto.com/getusr.php?id="+preff.getInt("id",0), response -> {
            ((kirtuin) this.getApplication()).setMyList(response);

        }, error -> error.printStackTrace());
        mQueuee.add(stringRequestt);
    }

}