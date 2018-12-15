package com.example.guicode.entregasdk.DataSources;

public class MenuItem
{
    private String name;
    private String price;
    private String description;
    private String menuPhoto;
    private String restaurantID;
    private String ID;
    public MenuItem(String name,String price,String description,String menuPhoto,String restaurantID,String ID)
    {
        this.name = name;
        this.price = price;
        this.description = description;
        this.menuPhoto = menuPhoto;
        this.restaurantID = restaurantID;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getMenuPhoto() {
        return menuPhoto;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public String getID() {
        return ID;
    }
}