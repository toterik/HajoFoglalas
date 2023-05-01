package com.example.hajofoglalas;

public class Ship
{
    private String desccription;
    private final int imageResource;
    private String name;
    private String price;

    public Ship(String desccription, int imageResource, String name, String price) {
        this.desccription = desccription;
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
    }

    public String getDesccription() {
        return desccription;
    }

    public void setDesccription(String desccription) {
        this.desccription = desccription;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
