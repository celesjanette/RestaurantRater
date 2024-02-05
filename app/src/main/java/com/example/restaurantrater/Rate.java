package com.example.restaurantrater;

public class Rate {

    private int ratingID;
    private long restaurantID;
    private String dishname;
    private String dishtype;
    private float rating;

    public Rate() {
        ratingID = -1;

    }

    public int getRateID() {
        return ratingID;
    }

    public void setRateID(int rateID) {
        this.ratingID = rateID;
    }

    public long getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(long restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public String getDishtype() {
        return dishtype;
    }

    public void setDishtype(String dishtype) {
        this.dishtype = dishtype;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}


