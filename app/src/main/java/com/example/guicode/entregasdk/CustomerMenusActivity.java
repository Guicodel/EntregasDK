package com.example.guicode.entregasdk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guicode.entregasdk.DataSources.CustomAdapterMenu;
import com.example.guicode.entregasdk.DataSources.CustomAdapterMenuAdd;
import com.example.guicode.entregasdk.DataSources.MenuItem;
import com.example.guicode.entregasdk.DataSources.OrderBase;
import com.example.guicode.entregasdk.DataSources.TotalOrder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.guicode.entregasdk.DataSources.OrderBase.setCustomerId;
import static com.example.guicode.entregasdk.DataSources.OrderBase.setRestaurantId;

public class CustomerMenusActivity extends AppCompatActivity implements View.OnClickListener {

    public String restaurantItemId;
    private String restaurantName;
    private String customerId;
    private TextView Tittle;
    private ListView LIST;
    private ArrayList<MenuItem> LISTINFO;
    private CustomAdapterMenuAdd ADAPTER;
    private Context root;
    private Button showOrderDetail;
    private TotalOrder ORDER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        restaurantName = this.getIntent().getExtras().getString("restaurantName");
        restaurantItemId = this.getIntent().getExtras().getString("restaurantId");
        customerId = this.getIntent().getExtras().getString("customerId");

        root=this;
        LISTINFO = new ArrayList<MenuItem>();
        //createOrder();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_menus);
        OrderBase.getInstance();
        OrderBase.setRestaurantId(restaurantItemId);
        OrderBase.setCustomerId(customerId);
        loadComponents();
        loadDataFromApi();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        //LISTINFO.clear();
        //loadDataFromApi();
    }
    public void createOrder()
    {
        ORDER = new TotalOrder(customerId,restaurantItemId);
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
                        ADAPTER=new CustomAdapterMenuAdd(root,LISTINFO,ORDER);
                        LIST.setAdapter(ADAPTER);
                    }
                }
        );
    }
    private void loadComponents() {
        //Tittle = findViewById(R.id.restaurantTitle);
        //Tittle.setText(restaurantName);
        LIST=(ListView)findViewById(R.id.customer_menu_list);

        //LIST.setOnItemClickListener(this);
        showOrderDetail=(Button)findViewById(R.id.button_show_order);
        showOrderDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_show_order:
                Intent objetc = new Intent(CustomerMenusActivity.this,OrderConfirmationActivity.class);
                this.startActivity(objetc);
                break;
        }
    }
}
