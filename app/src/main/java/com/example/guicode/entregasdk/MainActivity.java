package com.example.guicode.entregasdk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private RadioGroup option;
    //private String USER;
    Button init,signup;
    private EditText userData,passData;
    AsyncHttpClient client;
    TextView username;
    AlertDialog alertDialog;
    RelativeLayout relay1,relay2;
    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            relay1.setVisibility(View.VISIBLE);
            relay2.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relay1=(RelativeLayout) findViewById(R.id.rellay1);
        relay2=(RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable,2000);
        loadComponents();
    }

    private void loadComponents()
    {
        option = findViewById(R.id.radio_option);

        init=(Button)findViewById(R.id.init);
        init.setOnClickListener(this);
        username=(TextView)findViewById(R.id.username);
        username.setOnClickListener(this);
        signup=(Button)findViewById(R.id.signup);
        signup.setOnClickListener(this);
        userData = (EditText) findViewById(R.id.username_login);
        passData = (EditText) findViewById(R.id.password_login);
        client = new AsyncHttpClient();
        alertDialog = new AlertDialog.Builder(MainActivity.this).create();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.init:

                String user = userData.getText().toString();
                String pass = passData.getText().toString();
                if(user.equals("")||pass.equals("") )
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    String msn = "Llene ambos campos";
                    alertDialog.setTitle("ERROR");
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
                if (option.getCheckedRadioButtonId() == R.id.radioButton_login_client)
                {
                    RequestParams params = new RequestParams();
                    String userApi = userData.getText().toString();
                    String passApi = passData.getText().toString();
                    params.put("user",userApi);
                    params.put("password",passApi);
                    Utils.token = "";
                    client.post(Utils.customerLogin,params, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response)
                        {

                            try {
                                Utils.token = response.getString("token");
                                Intent objeto = new Intent(MainActivity.this, DashboardCustomerActivity.class);
                                startActivity(objeto);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);

                            try {
                                String msn =errorResponse.getString("msn");
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
                            }
                        }
                    });

                }
                else
                {
                    RequestParams params = new RequestParams();
                    String userApi = userData.getText().toString();
                    String passApi = passData.getText().toString();
                    params.put("user",userApi);
                    params.put("password",passApi);
                    Utils.token = "";
                    client.post(Utils.ownerLogin,params, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response)
                        {

                            try {
                                Utils.token = response.getString("token");
                                Intent objeto = new Intent(MainActivity.this, DashBoard.class);
                                startActivity(objeto);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);

                            try {
                                String msn =errorResponse.getString("msn");
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
                            }
                        }
                    });
                }
                //objeto.putExtra("usuario",cad);

                break;
            case R.id.signup:
                Intent object = new Intent(MainActivity.this,OwnerView2.class);
                startActivity(object);
        }
    }
}
