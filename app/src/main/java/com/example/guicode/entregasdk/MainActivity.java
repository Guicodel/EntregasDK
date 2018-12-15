package com.example.guicode.entregasdk;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button init,signup;
    TextView username;
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
        init=(Button)findViewById(R.id.init);
        init.setOnClickListener(this);
        username=(TextView)findViewById(R.id.username);
        username.setOnClickListener(this);
        signup=(Button)findViewById(R.id.signup);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.init:
                Intent objeto = new Intent(MainActivity.this, DashBoard.class);
                String cad=username.getText().toString();
                objeto.putExtra("usuario",cad);
                startActivity(objeto);
                break;
            case R.id.signup:
                Intent object = new Intent(MainActivity.this,OwnerView2.class);
                startActivity(object);
        }
    }
}
