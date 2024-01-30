package com.example.restaurantrater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText restaurantNameEditText;
    private EditText streetNameEditText;
    private EditText cityEditText;
    private EditText stateEditText;
    private EditText zipCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantNameEditText = findViewById(R.id.restaurantEdit);
        streetNameEditText = findViewById(R.id.streetnameEdit);
        cityEditText = findViewById(R.id.cityEdit);
        stateEditText = findViewById(R.id.stateEdit);
        zipCodeEditText = findViewById(R.id.zipcodeEdit);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRestaurantInfo();
            }
        });

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String restaurantName = restaurantNameEditText.getText().toString();
                Intent intent = new Intent(MainActivity.this, RateDish.class);
                intent.putExtra("RESTAURANT_NAME", restaurantName);
                startActivity(intent);
                startActivity(intent);
            }
        });
    }

    private long saveRestaurantInfo() {
        String restaurantName = restaurantNameEditText.getText().toString();
        String streetName = streetNameEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String state = stateEditText.getText().toString();
        String zipCode = zipCodeEditText.getText().toString();

        RestaurantDataSource dataSource = new RestaurantDataSource(this);
        dataSource.open();

        long restaurantId = dataSource.insertRestaurant(restaurantName, streetName, city, state, zipCode);
        dataSource.close();
        Intent intent = new Intent(MainActivity.this, RateDish.class);
        intent.putExtra("RESTAURANT_NAME", restaurantName);
        startActivity(intent);

        return restaurantId;
    }
}
