package com.example.guicode.entregasdk;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class OwnerActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{

    private ListView LIST;
    private ArrayList<RestaurantList> LISTINFO;
    private CustomAdapter ADAPTER;
    private Context root;
    private Button restaurantRegistration;
    private boolean load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        load = false;
        LISTINFO = new ArrayList<RestaurantList>();
        root=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        ADAPTER=new CustomAdapter(root,LISTINFO);
    }

    @Override
    protected void onResume()
    {

        super.onResume();
        LISTINFO.clear();
        ADAPTER.notifyDataSetChanged();
        loadDataFromApi();
        loadComponents();
        //loadComponents();
    }
    private void loadDataFromApi()
    {
        AsyncHttpClient client= new AsyncHttpClient();
        client.get(Utils.restaurant,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response)
                    {
                        //Toast.makeText(getApplicationContext(),"Cargando datos ",Toast.LENGTH_SHORT).show();
                        JSONArray List=(JSONArray) response;
                        JSONObject itemJson;

                        for(int i=0;i<List.length();i++)
                        {
                            try {
                                //Toast.makeText(getApplicationContext(),"Entrado al for "+i,Toast.LENGTH_SHORT).show();
                                itemJson=List.getJSONObject(i);

                                String name=itemJson.getString("name");
                                String phone=itemJson.getString("phone");
                                String nit=itemJson.getString("nit");
                                String street=itemJson.getString("street");
                                String image=itemJson.getString("restaurantPhoto");
                                String aux = Utils.imageService+image;
                                String id = itemJson.getString("_id");
                                RestaurantList A=new RestaurantList(name,phone,nit,street,aux,id);
                                LISTINFO.add(A);


                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }

                        ADAPTER=new CustomAdapter(root,LISTINFO);
                        LIST.setAdapter(ADAPTER);load =true;
                    }
                }
        );

    }
    private void loadComponents()
    {
        LIST=(ListView)findViewById(R.id.OwnerRestaurant_list_layout);
        LIST.setOnItemClickListener(this);
        restaurantRegistration=(Button)findViewById(R.id.button_to_restaurant_registration);
        restaurantRegistration.setOnClickListener(this);
        //Toast.makeText(getApplicationContext(),"cargando componentes ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_to_restaurant_registration:
                Intent object = new Intent(OwnerActivity.this,RestaurantRegisterActivity.class);
                startActivity(object);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(load) {
            String idItem = this.LISTINFO.get(position).getId();
            String restaurantName = this.LISTINFO.get(position).getName();
            Intent object = new Intent(OwnerActivity.this, RestaurantMenuActivity.class);
            object.putExtra("restaurantId", idItem);
            object.putExtra("restaurantName", restaurantName);
            this.startActivity(object);
        }

    }
}
