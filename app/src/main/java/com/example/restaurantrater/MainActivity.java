package com.example.restaurantrater;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RestaurantDataSource dataSource;
    private EditText restaurantNameEditText;
    private EditText streetNameEditText;
    private EditText cityEditText;
    private EditText stateEditText;
    private EditText zipCodeEditText;

    private Restaurant currentRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new RestaurantDataSource(this);

        currentRestaurant = new Restaurant();

        initTextChangeEvents();

        restaurantNameEditText = findViewById(R.id.restaurantEdit);
        streetNameEditText = findViewById(R.id.streetnameEdit);
        cityEditText = findViewById(R.id.cityEdit);
        stateEditText = findViewById(R.id.stateEdit);
        zipCodeEditText = findViewById(R.id.zipcodeEdit);

        initSaveButton();
        Button viewListButton = findViewById(R.id.viewListButton);
        viewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, RatedMealsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initTextChangeEvents() {
        final EditText etRestaurantName = findViewById(R.id.restaurantEdit);
        etRestaurantName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setRestaurantName(s.toString());
            }
        });

        final EditText etStreetName = findViewById(R.id.streetnameEdit);
        etStreetName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setStreetName(s.toString());
            }
        });

        final EditText etCity = findViewById(R.id.cityEdit);
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setCity(s.toString());
            }
        });

        final EditText etState = findViewById(R.id.stateEdit);
        etState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setState(s.toString());
            }
        });

        final EditText etZipCode = findViewById(R.id.zipcodeEdit);
        etZipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setZipCode(s.toString());
            }
        });
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean wasSuccessful;
                RestaurantDataSource ds = new RestaurantDataSource(MainActivity.this);
                saveRestaurant();
                // Get the list of rated meals for the current restaurant
                ArrayList<Rate> ratedMeals = getAllRatedMeals();
                // Proceed with your logic using the rated meals ArrayList
            }
        });
    }

    private void saveRestaurant() {
        String restaurantName = restaurantNameEditText.getText().toString();
        String streetName = streetNameEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String state = stateEditText.getText().toString();
        String zipCode = zipCodeEditText.getText().toString();

        try {
            dataSource.open();
            long restaurantId = dataSource.insertRestaurant(restaurantName, streetName, city, state, zipCode);
            dataSource.close();

            Intent intent = new Intent(MainActivity.this, RateDish.class);
            intent.putExtra("RESTAURANT_NAME", restaurantName);
            intent.putExtra("RESTAURANT_ID", restaurantId); // Pass the restaurant ID to RateDish activity
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Rate> getAllRatedMeals() {
        ArrayList<Rate> ratedMeals = new ArrayList<>();

        // Get the restaurant ID from the currentRestaurant object or any other source
        long restaurantId = -1; // Set
        try {
            dataSource.open();
            ratedMeals = dataSource.getRatedMeals(restaurantId);
            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ratedMeals;
    }
    }

