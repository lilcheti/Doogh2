package com.milcrypto.doogh;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

public class LinkListActivity extends AppCompatActivity {
    RecyclerView linklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linklist);
        ((kirtuin) this.getApplication()).setSubtitle(null);
        Bundle b = this.getIntent().getExtras();
        final ArrayList<String> Urls = b.getStringArrayList("links");
        String imdbid = b.getString("imdbid");
        int s = b.getInt("season");
        int e = b.getInt("episode");
        b.clear();
        String subtitle = "/subtitle/"+imdbid+"/s"+s+"/e"+e+"/";
        createRadioButton(subtitle);
        linklist = findViewById(R.id.linkList);
        linklistadapter linklistadapter = new linklistadapter(Urls,this);
        linklist.setAdapter(linklistadapter);
        linklist.setLayoutManager(new GridLayoutManager(this,1));
        ImageView img = findViewById(R.id.clos);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void onDestroy () {
//do your stuff here
        ((kirtuin) this.getApplication()).setSubtitle(null);
        super.onDestroy();
    }

    private void createRadioButton(String subtitle) {
        RequestQueue mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://1milcrypto.com/scan.php?d=."+subtitle, response -> {
            System.out.println("XXXXXXXXXXXXXXXXXX"+response+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            if (!response.equals("nah") && !response.equals(" ") && !response.equals(null)){
            RadioGroup rg = findViewById(R.id.radioGroup); //create the RadioGroup
            //rg.setOrientation(RadioGroup.HORIZONTAL);
            System.out.println(response);
            String[] sub = response.split("[|]");
            final RadioButton[] rb = new RadioButton[sub.length];
            for(int i=0; i<sub.length-1; i++){
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(5,0,0,0);
                rb[i]  = new RadioButton(this);
                rb[i].setLayoutParams(params);
                rb[i].setText(sub[i]);
                rb[i].setId(i+1);
                rg.addView(rb[i],0);
            }}

        }, error -> error.printStackTrace());
        mQueue.add(stringRequest);
        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb= findViewById(checkedId);
            String radioText=rb.getText().toString();
            String url = "http://1milcrypto.com"+subtitle+radioText;
            if(checkedId==R.id.no){
                url=null;
            }
            System.out.println(url);
            ((kirtuin) this.getApplication()).setSubtitle(url);
        });
        //or RadioGroup.VERTICAL

    }

}
