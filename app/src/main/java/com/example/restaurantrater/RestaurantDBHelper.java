package com.example.restaurantrater;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RestaurantDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurantrater.db";
    private static final int DATABASE_VERSION = 1;

    //SQL statements
    private static final String CREATE_TABLE_RESTAURANT =
            "create table restaurant (_id integer primary key autoincrement, "
            + "restaurantname text not null, streetname text, "
            + "city text, state text, zipcode text); ";
    private static final String CREATE_TABLE_RATE =
            "create table rate (_id integer primary key autoincrement, "
            + "dishname text not null, dishtype text, rating float, "
            + "restaurantID integer, "
            + "foreign key (restaurantId) references restaurant(_id));";

    public RestaurantDBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_RATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    Log.w(RestaurantDBHelper.class.getName(), "Upgrading database from version " + "to"
             + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS restaurant");
        db.execSQL("DROP TABLE IF EXISTS rate");
            onCreate(db);
    }
}
