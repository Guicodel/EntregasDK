package com.example.guicode.entregasdk;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guicode.entregasdk.DataSources.CustomAdapter;
import com.example.guicode.entregasdk.DataSources.RestaurantList;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DashBoard extends AppCompatActivity implements View.OnClickListener{

    private ListView LIST;
    private ArrayList<RestaurantList> LISTINFO;
    private CustomAdapter ADAPTER;
    private Context root;

    Button OwnerOption;
    Button CustomerOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LISTINFO = new ArrayList<RestaurantList>();
        root=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        loadComponents();
    }

    private void loadComponents()
    {
        OwnerOption=(Button)findViewById(R.id.Owner_button_layout);
        OwnerOption.setOnClickListener(this);
        CustomerOption=(Button)findViewById(R.id.Customer_button_layout);
        CustomerOption.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.Owner_button_layout:
                Intent objet = new Intent(DashBoard.this, OwnerActivity.class);
                startActivity(objet);
                break;
            case R.id.Customer_button_layout:
                //use this customerId to make tests 5bf2c48a424bc7001118dfc4
                String customerId = "5bf2c48a424bc7001118dfc4";
                Intent object = new Intent(DashBoard.this,CustomerRestaurantActivity.class);
                object.putExtra("id",customerId);
                this.startActivity(object);
                break;

        }
    }
}
