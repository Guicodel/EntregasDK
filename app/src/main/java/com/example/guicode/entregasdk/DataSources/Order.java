package com.example.guicode.entregasdk.DataSources;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Order
{
    private String menuId;
    private String menuName;
    private String quantity;
    private String unityCost;
    private String payment;
    public Order(String menuId,String menuName,String quantity,String unityCost,String payment)
    {
        this.menuId = menuId;
        this.menuName = menuName;
        this.quantity = quantity;
        this.unityCost = unityCost;
        this.payment = payment;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnityCost() {
        return unityCost;
    }

    public String getPayment() {
        return payment;
    }

    public String toString(){
        return this.getJson().toString();
    }

    public JSONObject getJson(){
        JSONObject jo = new JSONObject();
        try {
            jo.put("menuId", menuId);
            jo.put("menuName",menuName);
            jo.put("quantity",quantity);
            jo.put("payment",payment);
            jo.put("unityCost",unityCost);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }
}
