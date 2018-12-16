package com.example.guicode.entregasdk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button myOrders;
    private Button newOrder;
    private String customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_customer);
        loadComponents();
    }

    private void loadComponents() {
        myOrders = findViewById(R.id.buttonCustomer_my_orders);
        newOrder = findViewById(R.id.buttonCustomer_new_order);
        newOrder.setOnClickListener(this);
        myOrders.setOnClickListener(this);
        customerId = this.getIntent().getExtras().getString("userId");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonCustomer_new_order:
                Intent object = new Intent(DashboardCustomerActivity.this,CustomerRestaurantActivity.class);
                object.putExtra("customerId",customerId);
                startActivity(object);
                break;
            case R.id.buttonCustomer_my_orders:
                Intent object2 = new Intent(DashboardCustomerActivity.this,OrdersListActivity.class);
                object2.putExtra("customerId",customerId);
                startActivity(object2);
                break;
        }
    }
}
