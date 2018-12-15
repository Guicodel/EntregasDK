package com.example.guicode.entregasdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guicode.entregasdk.DataSources.Order;
import com.example.guicode.entregasdk.DataSources.OrderBase;

import static com.example.guicode.entregasdk.DataSources.OrderBase.addOrder;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{

    private Button backMenus;
    private String menuName;
    private TextView menuTitle;
    private TextView unityPrice;
    private TextView orderPayment;
    private String menuId;
    private Integer quantity=0;
    private Double payment=0.0;
    private String unityCost;
    private EditText quantityOrder;
    private TextView quantityOrder2;
    private Boolean bandera;
    private Order newOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        menuName = this.getIntent().getExtras().getString("menuName");
        menuId = this.getIntent().getExtras().getString("menuId");
        unityCost = this.getIntent().getExtras().getString("unityCost");
        super.onCreate(savedInstanceState);bandera = false;
        setContentView(R.layout.activity_order);
        loadComponents();
    }
    public void loadComponents()
    {
        orderPayment = (TextView)findViewById(R.id.payment_order);
        unityPrice = (TextView)findViewById(R.id.unity_cost);
        menuTitle = (TextView)findViewById(R.id.title_order);
        String aux=OrderBase.getCustomerId();
        menuTitle.setText(menuName);
        unityPrice.setText(unityCost);
        backMenus = (Button)findViewById(R.id.button_back_menus);
        backMenus.setOnClickListener(this);
        quantityOrder2 = (TextView)findViewById(R.id.quantity_order2);
        quantityOrder = (EditText) findViewById(R.id.quantity_order);
        quantityOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int tam = s.length();
                if(tam>0) {
                    quantityOrder2.setText(s);
                    Integer pri = Integer.valueOf(unityCost);
                    String quan = quantityOrder.getText().toString();
                    quantity = Integer.valueOf(quan);
                    payment = quantity * pri * 1.0;
                    orderPayment.setText(payment.toString());
                    bandera = true;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_back_menus:
                if(bandera){
                    newOrder = new Order(menuId,menuName,quantity.toString(),unityCost,payment.toString());
                    OrderBase.addOrder(newOrder);
                }
                this.finish();
        }
    }
}
