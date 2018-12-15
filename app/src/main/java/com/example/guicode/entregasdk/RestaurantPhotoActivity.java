package com.example.guicode.entregasdk;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.guicode.entregasdk.DataSources.BitmapStruct;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class RestaurantPhotoActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton takePhoto;
    //private ImageButton gallery;
    private Button sendImage;
    private ImageView image;
    private int Code=100;
    private int CodePermission=101;
    private BitmapStruct DataImage;
    private String restaurantId="";
    Context root = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_photo);
        loadComponents();

    }
    public void loadComponents()
    {
        image = findViewById(R.id.imageView_restaurant_photo_logo);
        takePhoto = findViewById(R.id.imageButton_restaurant_photo);
        takePhoto.setVisibility(View.INVISIBLE);
        sendImage = findViewById(R.id.button_send_restaurantImage);
        restaurantId = this.getIntent().getExtras().getString("restaurantId");
        if(checkPermissionTocamera())
        {
            takePhoto.setVisibility(View.VISIBLE);
        }
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                RestaurantPhotoActivity.this.startActivityForResult(camera,Code);
            }
        });
        sendImage.setOnClickListener(this);
    }
    private boolean checkPermissionTocamera()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if(this.checkCallingPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},CodePermission);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(CodePermission == requestCode){
            if(permissions.length == 3)
                takePhoto.setVisibility(View.VISIBLE);
        }
    }
    private BitmapStruct saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String path = directory.getAbsolutePath() + "/profile.jpg";
        BitmapStruct p = new BitmapStruct();
        p.image = BitmapFactory.decodeFile(path);
        p.path = path;
        //
        return p;
        //return directory.getAbsolutePath();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Code)
        {
            Bitmap photo = (Bitmap)data.getExtras().get("data");

            DataImage = saveToInternalStorage(photo);
            image.setImageBitmap(DataImage.image);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_send_restaurantImage:
                if(DataImage!=null)
                {
                    Toast.makeText(RestaurantPhotoActivity.this, "foto valida", Toast.LENGTH_LONG).show();
                    AsyncHttpClient client = new AsyncHttpClient();
                    File img = new File(DataImage.path);
                    //client.addHeader("authorization", Data.TOKEN);
                    RequestParams params = new RequestParams();
                    try {
                        params.put("img", img);
                        //use this id for test = 5c0d8fed7b65f32a1429e05f
                        //String id="?id=5c0da5b17b65f32a1429e06a";
                        if(restaurantId.equals(null)) {
                            AlertDialog alertDialog = new AlertDialog.Builder(RestaurantPhotoActivity.this).create();
                            String msn = "Lo Sentimos hubo un problema intente nuevamente";
                            alertDialog.setTitle("Server Response");
                            alertDialog.setMessage(msn);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }

                        client.post(Utils.restaurantPhoto + restaurantId, params, new JsonHttpResponseHandler() {
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                AlertDialog alertDialog = new AlertDialog.Builder(RestaurantPhotoActivity.this).create();
                                String msn = null;
                                try {
                                    msn = response.getString("msn");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                alertDialog.setTitle("Server Response");
                                alertDialog.setMessage(msn);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();backRestaurantList();
                                            }
                                        });
                                alertDialog.show();
                                //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
                            }
                        });

                    } catch(FileNotFoundException e) {}
                }
                break;
        }
    }
    public void backRestaurantList()
    {
        Intent intent = new Intent(RestaurantPhotoActivity.this, OwnerActivity.class );
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity( intent );

    }
}
