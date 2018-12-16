package com.example.guicode.entregasdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrdersListActivity extends AppCompatActivity implements View.OnClickListener{
    private String CustomerId;
    private Button NewOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        loadComponents();
    }

    private void loadComponents() {
        NewOrder = findViewById(R.id.button_new_order_from_orders);
        CustomerId = this.getIntent().getExtras().getString("customerId");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_new_order_from_orders:
                break;
        }
    }
}
