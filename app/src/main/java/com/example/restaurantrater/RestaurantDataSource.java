package com.example.restaurantrater;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
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

    public long insertRating(long restaurantId, String dishName, String dishType, float rating) {
        ContentValues values = new ContentValues();
        values.put("restaurantID", restaurantId); // Assuming the column name is "restaurantID"
        values.put("dishname", dishName);
        values.put("dishtype", dishType);
        values.put("rating", rating);

        return database.insert("rate", null, values);
    }

    public ArrayList<Rate> getRatedMeals(long restaurantId) {
        ArrayList<Rate> ratedMeals = new ArrayList<>();
        if (database != null) { // Check if database is not null
            Cursor cursor = database.query("rate", null, "restaurantID=?", new String[]{String.valueOf(restaurantId)}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Rate rate = cursorToRate(cursor);
                ratedMeals.add(rate);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return ratedMeals;
    }

    private Rate cursorToRate(Cursor cursor) {
        Rate rate = new Rate();
        int idIndex = cursor.getColumnIndex("_id");
        int restaurantIdIndex = cursor.getColumnIndex("restaurantID");
        int dishNameIndex = cursor.getColumnIndex("dishname");
        int dishTypeIndex = cursor.getColumnIndex("dishtype");
        int ratingIndex = cursor.getColumnIndex("rating");

        if (idIndex != -1) {
            rate.setRateID(cursor.getInt(idIndex));
        }
        if (restaurantIdIndex != -1) {
            rate.setRestaurantID(cursor.getLong(restaurantIdIndex));
        }
        if (dishNameIndex != -1) {
            rate.setDishname(cursor.getString(dishNameIndex));
        }
        if (dishTypeIndex != -1) {
            rate.setDishtype(cursor.getString(dishTypeIndex));
        }
        if (ratingIndex != -1) {
            rate.setRating(cursor.getFloat(ratingIndex));
        }

        return rate;
    }
}