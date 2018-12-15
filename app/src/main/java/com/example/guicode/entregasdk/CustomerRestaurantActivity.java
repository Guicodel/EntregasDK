package com.example.guicode.entregasdk;

import android.content.Context;
import android.content.Intent;
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

public class CustomerRestaurantActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView LIST;
    private ArrayList<RestaurantList> LISTINFO;
    private CustomAdapter ADAPTER;
    private Context root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LISTINFO = new ArrayList<RestaurantList>();
        root=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_restaurant);
        loadComponents();
        loadDataFromApi();
    }
    private void loadDataFromApi()
    {
        AsyncHttpClient client= new AsyncHttpClient();
        client.get(Utils.restaurant,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response)
                    {

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
                        LIST.setAdapter(ADAPTER);
                    }
                }
        );

    }
    private void loadComponents()
    {
        LIST=(ListView)findViewById(R.id.customer_restaurant_list);
        LIST.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String idItem = this.LISTINFO.get(position).getId();
        String restaurantName = this.LISTINFO.get(position).getName();
        //use this customerID to make test 5bf2c48a424bc7001118dfc4
        String customerId = "5bf2c48a424bc7001118dfc4";
        Intent object = new Intent(CustomerRestaurantActivity.this,CustomerMenusActivity.class);
        object.putExtra("restaurantId",idItem);
        object.putExtra("restaurantName",restaurantName);
        object.putExtra("customerId",customerId);
        this.startActivity(object);
    }
}
