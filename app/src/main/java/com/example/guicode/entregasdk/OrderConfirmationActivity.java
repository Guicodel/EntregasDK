package com.example.guicode.entregasdk;

import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guicode.entregasdk.DataSources.CustomAdapterMenuAdd;
import com.example.guicode.entregasdk.DataSources.CustomAdapterOrder;
import com.example.guicode.entregasdk.DataSources.MenuItem;
import com.example.guicode.entregasdk.DataSources.Order;
import com.example.guicode.entregasdk.DataSources.OrderBase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import cz.msebera.android.httpclient.Header;

public class OrderConfirmationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ListView LIST;
    private TextView totalPayment;
    //private ArrayList<Order> LISTINFO;
    private CustomAdapterOrder ADAPTER;
    private MapView map;
    private GoogleMap mMap;
    private Geocoder geo;
    private LatLng position;
    private TextView street;
    private String address;
    private Button makeOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        LIST=(ListView)findViewById(R.id.list_menu_confirmation);
        loadDataFromOrders();
        totalPayment = (TextView)findViewById(R.id.total_payment);
        OrderBase.calculatePayment();
        totalPayment.setText(OrderBase.getTotalPayment().toString());
        map = findViewById(R.id.mapView_customer);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        map.getMapAsync(this);
        geo = new Geocoder(getBaseContext(),Locale.getDefault());
        street=findViewById(R.id.customer_street);
        makeOrder = findViewById(R.id.button_make_order);
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OrderBase.getOrdersTam()>1) {
                    createOrder();
                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(OrderConfirmationActivity.this).create();
                    String msn = "No selecciono ningun producto";
                    alertDialog.setTitle("No se puede realizar el pedido");
                    alertDialog.setMessage(msn);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();goBack();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
    }
    public void goBack()
    {
        this.finish();
    }
    public void loadDataFromOrders(){
        ADAPTER=new CustomAdapterOrder(this,OrderBase.getOrders());
        LIST.setAdapter(ADAPTER);
    }
    public void createOrder()
    {
        String lat,lon;
        lat = String.valueOf(position.latitude);
        lon = String.valueOf(position.longitude);
        OrderBase.setStreet(address);
        OrderBase.setLat(lat);
        OrderBase.setLon(lon);
        OrderBase.setOrderState("waiting");
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jo = new JSONObject();
        try {
            jo.put("customerId", OrderBase.getCustomerId());
            jo.put("restaurantId",OrderBase.getRestaurantId());
            jo.put("totalPayment",OrderBase.getTotalPayment());
            jo.put("street",OrderBase.getStreet());
            jo.put("lat",OrderBase.getLat());
            jo.put("lon",OrderBase.getLon());
            jo.put("restaurantName",OrderBase.getRestaurantName());
            jo.put("state",OrderBase.getOrderState());
            JSONArray ja = new JSONArray();
            for(Order o: OrderBase.getOrders()){
                ja.put(o.getJson());
            }
            jo.put("order", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestParams params = new RequestParams("json", jo.toString());
        client.post(Utils.order, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(OrderConfirmationActivity.this).create();
                try {
                    String msn = response.getString("msn");
                    alertDialog.setTitle("Server Response");
                    alertDialog.setMessage(msn);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();goBack();
                                }
                            });
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //-19.5822502,-65.7662029
        LatLng Potosi = new LatLng(-19.5818913, -65.7656158);
        position = Potosi;
        mMap.addMarker(new MarkerOptions().position(Potosi).title("Home").zIndex(19).draggable(true));
        mMap.setMinZoomPreference(15);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Potosi));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                position = marker.getPosition();
                address = getStreet(marker.getPosition().latitude,marker.getPosition().longitude);
                street.setText(address);
            }
        });

    }
    public String getStreet(double lat,double lon)
    {
        List<Address> streets;String result="";
        try
        {
            streets=geo.getFromLocation(lat,lon,1);

            for(int i=0;i<streets.size();i++)
            {
                result += streets.get(i).getThoroughfare()+" ..";
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
