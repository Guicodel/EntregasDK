package com.example.guicode.entregasdk.DataSources;

import java.util.ArrayList;

public class ShowingOrders
{
    private String CustomerId;
    private String ResturantId;
    private String RestaurantName;
    private String TotalPayment;
    private ArrayList<Order> Orders;

    public ShowingOrders(String customerId, String resturantId, String restaurantName, String totalPayment, ArrayList<Order> orders) {
        CustomerId = customerId;
        ResturantId = resturantId;
        RestaurantName = restaurantName;
        TotalPayment = totalPayment;
        Orders = orders;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getResturantId() {
        return ResturantId;
    }

    public void setResturantId(String resturantId) {
        ResturantId = resturantId;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getTotalPayment() {
        return TotalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        TotalPayment = totalPayment;
    }

    public ArrayList<Order> getOrders() {
        return Orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        Orders = orders;
    }
}
