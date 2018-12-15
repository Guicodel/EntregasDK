package com.example.guicode.entregasdk;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class RestaurantRegisterActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener{

    private MapView map;
    private GoogleMap mMap;
    private Geocoder geo;
    private TextView name,street,nit,phone;
    private Button next;
    private LatLng position;
    private String restaurantId;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_register);
        map=findViewById(R.id.mapView_restaurant_registration);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        map.getMapAsync(this);
        geo = new Geocoder(getBaseContext(),Locale.getDefault());
        street=findViewById(R.id.restaurant_street_registration);
        next=findViewById(R.id.button_restaurant_registration_next);
        next.setOnClickListener(this);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        //this.finish();
    }
    private boolean createRestaurant()
    {
        name = findViewById(R.id.restaurant_name_resgistration);
        nit = findViewById(R.id.restaurant_nit_registration);
        phone = findViewById(R.id.restaurant_phone_registration);
        restaurantId="";
        AsyncHttpClient client = new AsyncHttpClient();
        String nameApi=name.getText().toString();
        String phoneApi = phone.getText().toString();
        String nitApi = nit.getText().toString();
        String streetApi = street.getText().toString();
        String lat,lon;
        lat = String.valueOf(position.latitude);
        lon = String.valueOf(position.longitude);
        if(nameApi.equals("")||phoneApi.equals("")||nitApi.equals("")||streetApi.equals(""))
        {

            AlertDialog alertDialog = new AlertDialog.Builder(RestaurantRegisterActivity.this).create();
            String msn = "Todos Los Campos Son Obligatorios";
            alertDialog.setTitle("Server Response");
            alertDialog.setMessage(msn);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return false;
        }
        RequestParams params = new RequestParams();
        //to make test use this owner restaurant id = 5bee58734e1eb5001164d957
        String idTest = "5bee58734e1eb5001164d957";
        params.put("name",nameApi);
        params.put("nit",nitApi);
        params.put("street",streetApi);
        params.put("phone",phoneApi);
        params.put("lat",lat);
        params.put("lon",lon);
        params.put("ownerId",idTest);
        client.post(Utils.restaurant,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                /*AlertDialog alertDialog = new AlertDialog.Builder(RestaurantRegisterActivity.this).create();
                try {
                    String msn = response.getString("msn");
                    alertDialog.setTitle("Server Response");
                    alertDialog.setMessage(msn);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                try{
                    restaurantId = response.getString("restaurantId");
                    Intent object = new Intent(RestaurantRegisterActivity.this,RestaurantPhotoActivity.class);
                    object.putExtra("restaurantId", restaurantId);
                    startActivity(object);
                    //Toast.makeText(RestaurantRegisterActivity.this, restaurantId, Toast.LENGTH_LONG).show();

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    return true;
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
                String address = getStreet(marker.getPosition().latitude,marker.getPosition().longitude);
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_restaurant_registration_next:
                boolean a= createRestaurant();
                break;
        }
    }
}
