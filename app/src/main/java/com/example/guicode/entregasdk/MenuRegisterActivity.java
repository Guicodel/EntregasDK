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
import android.widget.TextView;
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

public class MenuRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton toGalery;
    private ImageButton takePhoto;
    private ImageView menuPhoto;
    private TextView name,price,description;
    private String restaurantId;
    private Button saveData;
    private Context root;
    private boolean bandera;
    private int Code=100;
    private int CodePermission=101;
    private BitmapStruct DataImage;
    private String menuId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        restaurantId = this.getIntent().getExtras().getString("restaurantId");
        root = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_register);

        loadComponents();
        if(checkPermissionTocamera())
        {
            takePhoto.setVisibility(View.VISIBLE);
        }

    }

    private void loadComponents() {
        takePhoto=findViewById(R.id.imageButton_photo_menu);
        takePhoto.setOnClickListener(this);
        menuPhoto = findViewById(R.id.image_menu_register);
        toGalery=findViewById(R.id.imageButton_menu_gallery);
        toGalery.setOnClickListener(this);

        saveData = findViewById(R.id.button_save_menu);
        saveData.setOnClickListener(this);
        bandera = false;
    }
    private boolean createMenu()
    {
        name = findViewById(R.id.editText_name_menu);
        price = findViewById(R.id.editText_price_menu);
        description = findViewById(R.id.editText_description_menu);
        AsyncHttpClient client = new AsyncHttpClient();
        String nameApi = name.getText().toString();
        String priceApi = price.getText().toString();
        String descriptionApi = description.getText().toString();
        String menuPhotoApi = "";
        String restaurantIdApi = restaurantId;
        if(nameApi.equals("")||priceApi.equals("")||descriptionApi.equals(""))
        {
            /*AlertDialog alertDialog = new AlertDialog.Builder(MenuRegisterActivity.this).create();
            String msn = "Primero llene los campos de texto";
            alertDialog.setTitle("Server Response");
            alertDialog.setMessage(msn);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();*/
            return false;
        }
        RequestParams params = new RequestParams();
        params.put("name",nameApi);
        params.put("price",priceApi);
        params.put("description",descriptionApi);
        params.put("menuPhoto",menuPhotoApi);
        params.put("restaurantId",restaurantIdApi);
        client.post(Utils.menu,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                /*AlertDialog alertDialog = new AlertDialog.Builder(MenuRegisterActivity.this).create();
                try {
                    String msn = response.getString("msn");
                    alertDialog.setTitle("Server Response");
                    alertDialog.setMessage(msn);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();goPrevios();
                                }
                            });
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                try {
                    menuId = response.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bandera = true;
            }
        });
        return true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton_menu_gallery:
                Intent files =new Intent(Intent.ACTION_PICK);
                files.setType("image/");
                this.startActivityForResult(files,101);
                break;
            case R.id.imageButton_photo_menu:
                if(createMenu()){
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    MenuRegisterActivity.this.startActivityForResult(camera,Code);
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MenuRegisterActivity.this).create();
                    String msn = "Primero llene los campos de texto";
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
                break;
            case R.id.button_save_menu:
                if(bandera==false){
                    AlertDialog alertDialog = new AlertDialog.Builder(MenuRegisterActivity.this).create();
                    String msn = "Primero llene los campos de texto y tome una foto";
                    alertDialog.setTitle("Server Response");
                    alertDialog.setMessage(msn);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    break;
                }
                if(DataImage!=null)
                {

                    AsyncHttpClient client2 = new AsyncHttpClient();
                    File img = new File(DataImage.path);
                    //client.addHeader("authorization", Data.TOKEN);
                    RequestParams params = new RequestParams();
                    try {
                        params.put("img", img);
                        //use this id for test = 5c0d8fed7b65f32a1429e05f
                        String id=menuId;
                        client2.post(Utils.menuPhoto+id, params, new JsonHttpResponseHandler(){
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                AlertDialog alertDialog = new AlertDialog.Builder(MenuRegisterActivity.this).create();
                                Toast.makeText(MenuRegisterActivity.this, "foto valida", Toast.LENGTH_LONG).show();
                                try {
                                    String msn = response.getString("msn");
                                    alertDialog.setTitle("Server Response");
                                    alertDialog.setMessage(msn);

                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();goPrevios();
                                                }
                                            });
                                    alertDialog.show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(RestaurantPhotoActivity.this, "EXITO", Toast.LENGTH_LONG).show();
                                //AsyncHttpClient.log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
                            }
                        });

                    } catch(FileNotFoundException e) {}
                }
                    //createMenu();


                //Intent object = new Intent(MenuRegisterActivity.this,RestaurantMenuActivity.class);
                //startActivity(object);
                break;

        }
    }
    public void goPrevios()
    {
        this.finish();
    }
    public void takePhoto()
    {


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
        //Toast.makeText(this, "guarda foto", Toast.LENGTH_LONG).show();
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
            menuPhoto.setImageBitmap(DataImage.image);
        }
    }
}
