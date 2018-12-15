package com.example.guicode.entregasdk.DataSources;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrderBase
{
    private static OrderBase instance;
    private static String CustomerId;
    private static String RestaurantId;
    private static Double TotalPayment;
    private static String Street;
    private static String Lat;
    private static String Lon;
    private static ArrayList<Order> Orders = new ArrayList<Order>();
    private OrderBase(){

    }
    public static Order getOrdersByPosition(int pos)
    {
        return Orders.get(pos);
    }
    public static OrderBase getInstance()
    {
        if(instance == null){
            instance = new OrderBase();
        }
        return instance;
    }
    public static int getOrdersTam()
    {
        return Orders.size();
    }
    public static void calculatePayment()
    {
        double resul=0.0;double dato;
        String aux;
        for(int i=0;i<Orders.size();i++)
        {
            aux=Orders.get(i).getPayment();
            dato = Double.valueOf(aux);
            resul = resul + dato;
        }
        TotalPayment=resul;
    }
    public static void clearOrders(){
        Orders.clear();
    }
    public static void addOrder(Order order)
    {
        Orders.add(order);
    }
    public static String getCustomerId() {
        return CustomerId;
    }

    public static void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public static String getRestaurantId() {
        return RestaurantId;
    }

    public  static void setRestaurantId(String restaurantId) {
        RestaurantId = restaurantId;
    }

    public static Double getTotalPayment() {
        return TotalPayment;
    }

    public static void setTotalPayment(Double totalPayment) {
        TotalPayment = totalPayment;
    }

    public static String getStreet() {
        return Street;
    }

    public static void setStreet(String street) {
        Street = street;
    }

    public static String getLat() {
        return Lat;
    }

    public static void setLat(String lat) {
        Lat = lat;
    }

    public static String getLon() {
        return Lon;
    }

    public static void setLon(String lon) {
        Lon = lon;
    }

    public static  ArrayList<Order> getOrders() {
        return Orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        Orders = orders;
    }
}
