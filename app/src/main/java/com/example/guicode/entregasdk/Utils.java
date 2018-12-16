package com.example.guicode.entregasdk;

public class Utils {
    static public String servicesAddress="http://192.168.0.100:7777/api/v1/";
    static public String restaurant = servicesAddress+"restaurant/";
    static public String restaurantPhoto = servicesAddress+"restaurantimage/?id=";
    static public String imageService = "http://192.168.0.100:7777/";
    static public String restaurantMenus = servicesAddress+"restaurantMenus/?id=";
    static public String menu = servicesAddress+"menu/";
    static public String menuPhoto =servicesAddress+"menuimage/?id=";
    static public String order = servicesAddress+"order/";
    static public String token;
    static public String customerLogin = servicesAddress+"customerLogin/";
    static public String ownerLogin = servicesAddress+"ownerLogin/";
}
