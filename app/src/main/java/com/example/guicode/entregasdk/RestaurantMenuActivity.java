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
import android.widget.TextView;
import android.widget.Toast;

import com.example.guicode.entregasdk.DataSources.CustomAdapter;
import com.example.guicode.entregasdk.DataSources.CustomAdapterMenu;
import com.example.guicode.entregasdk.DataSources.MenuItem;
import com.example.guicode.entregasdk.DataSources.RestaurantList;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RestaurantMenuActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    public String restaurantItemId;
    private String restaurantName;
    private TextView Tittle;
    private ListView LIST;
    private ArrayList<MenuItem> LISTINFO;
    private CustomAdapterMenu ADAPTER;
    private Context root;
    private Button menuRegistration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        restaurantName = this.getIntent().getExtras().getString("restaurantName");
        restaurantItemId = this.getIntent().getExtras().getString("restaurantId");
        LISTINFO = new ArrayList<MenuItem>();
        root=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        //loadDataFromApi();


    }
    @Override
    protected void onResume()
    {
        super.onResume();
        LISTINFO.clear();
        loadDataFromApi();
        loadComponents();
    }
    private void loadDataFromApi() {
        AsyncHttpClient client= new AsyncHttpClient();
        client.get(Utils.restaurantMenus+restaurantItemId,new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response)
                    {
                        //Toast.makeText(getApplicationContext(),"Cargando datos ",Toast.LENGTH_SHORT).show();
                        JSONArray List=(JSONArray) response;
                        JSONObject itemJson;

                        for(int i=List.length();i>=0;i--)
                        {
                            try {
                                //Toast.makeText(getApplicationContext(),"Entrado al for "+i,Toast.LENGTH_SHORT).show();
                                itemJson=List.getJSONObject(i);

                                String name=itemJson.getString("name");

                                String price=itemJson.getString("price");
                                String description=itemJson.getString("description");
                                String restauratId=itemJson.getString("restaurantId");
                                String menuPhoto=itemJson.getString("menuPhoto");
                                String aux = Utils.imageService+menuPhoto;
                                String id = itemJson.getString("_id");
                                MenuItem menuItem=new MenuItem(name,price,description,aux,restauratId,id);
                                //Toast.makeText(getApplicationContext(),"se añadio:  "+LISTINFO.size()+" "+name,Toast.LENGTH_SHORT).show();
                                LISTINFO.add(menuItem);

                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        ADAPTER=new CustomAdapterMenu(root,LISTINFO);
                        LIST.setAdapter(ADAPTER);
                    }
                }
        );
    }

    private void loadComponents() {
        Tittle = findViewById(R.id.restaurantTitle);
        Tittle.setText(restaurantName);
        LIST=(ListView)findViewById(R.id.item_list_menu);

        LIST.setOnItemClickListener(this);
        menuRegistration=(Button)findViewById(R.id.button_form_to_menu);
        menuRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_form_to_menu:
                Intent object = new Intent(RestaurantMenuActivity.this,MenuRegisterActivity.class);
                object.putExtra("restaurantId",restaurantItemId);
                startActivity(object);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
