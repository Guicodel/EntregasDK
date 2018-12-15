package com.example.guicode.entregasdk.DataSources;

import java.util.ArrayList;

public class TotalOrder
{
    private String customerId;
    private String restaurantId;
    private Double totalPayment;
    private String street;
    private String lat;
    private String lon;
    private ArrayList<Order> Orders;
    private Order order;
    public TotalOrder(String customerId,String restaurantId)
    {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.totalPayment = 0.7;
    }
    public void addOrder(Order order)
    {
        this.Orders.add(order);
    }
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public ArrayList<Order> getOrders() {
        return Orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        Orders = orders;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
