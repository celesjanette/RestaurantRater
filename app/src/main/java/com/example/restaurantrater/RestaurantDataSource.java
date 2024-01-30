package com.example.restaurantrater;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class RestaurantDataSource {
    private SQLiteDatabase database;
    private RestaurantDBHelper dbHelper;
    public RestaurantDataSource(Context context) {
        dbHelper = new RestaurantDBHelper(context);
    }
    public void open() throws SQLException {
     database = dbHelper.getWritableDatabase();
}
    public void close() {
        dbHelper.close();
    }
    public long insertRestaurant(String restaurantName, String streetName, String city, String state, String zipCode) {
        ContentValues values = new ContentValues();
        values.put("restaurantname", restaurantName);
        values.put("streetname", streetName);
        values.put("city", city);
        values.put("state", state);
        values.put("zipcode", zipCode);

        return database.insert("restaurant", null, values);
    }
}
