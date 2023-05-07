package com.example.hajofoglalas;

public class Ship
{
    private String id;
    private String desccription;
    private int imageResource;
    private String name;
    private int price;
    private boolean foglalt;
    private int position;

    public Ship() {
    }


    public Ship(String desccription, int imageResource, String name, int price, boolean foglalt) {
        this.desccription = desccription;
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
        this.foglalt = foglalt;
    }


    public void setFoglalt(boolean foglalt) {
        this.foglalt = foglalt;
    }

    public boolean isFoglalt() {
        return foglalt;
    }

    public String getDesccription() {
        return desccription;
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

    public int getPrice() {
        return price;
    }

    public String _getId()
    {
        return this.id;
    }
    public void setId(String id)
    {
        this.id=id;
    }
}
