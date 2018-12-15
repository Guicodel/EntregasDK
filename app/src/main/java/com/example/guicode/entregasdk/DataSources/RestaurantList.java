package com.example.guicode.entregasdk.DataSources;

public class RestaurantList
{
    private String name;
    private String nit;
    private String street;
    private String image;
    private String phone;
    private String id;
    public RestaurantList(String name,String phone,String nit,String street,String image, String id)
    {
        this.name=name;this.phone=phone;this.nit=nit;this.street=street;this.image=image;this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getNit() {
        return nit;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public String getId() {
        return id;
    }
}
