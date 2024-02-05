package com.example.restaurantrater;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateDish extends AppCompatActivity {
    private TextView restaurantFillView;
    private EditText dishNameEditText;
    private EditText dishTypeEditText;
    private RatingBar ratingBar;
    private Button finishButton;
    private Button backButton;
    private String restaurantName;
    private TextView finishedRateView;
    private Rate currentRate;
    private RestaurantDataSource dataSource;
    private long restaurantId = -1;
    private long ratingId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_dish);
        currentRate = new Rate();
        dataSource = new RestaurantDataSource(this);

        restaurantFillView = findViewById(R.id.restaurantfillView);
        dishNameEditText = findViewById(R.id.dishnameEdit);
        dishTypeEditText = findViewById(R.id.dishtypeEdit);
        ratingBar = findViewById(R.id.ratingBar);
        finishButton = findViewById(R.id.finishButton);
        backButton = findViewById(R.id.backButton);

        // Extract restaurantId from the intent
        restaurantId = getIntent().getLongExtra("RESTAURANT_ID", -1);
        restaurantName = getIntent().getStringExtra("restaurantName");

        restaurantFillView.setText(restaurantName);

        finishedRateView = findViewById(R.id.finishedRateView); // Initialize finishedRateView

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float rate = ratingBar.getRating();
                finishedRateView.setText("Your rating is " + rating + " stars");
                Log.d("RatingBar", "onRatingChanged:" + rating);
            }
        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean wasSuccessful;
                RestaurantDataSource ds = new RestaurantDataSource(RateDish.this);
                saveRatingAndNavigateToMain();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RateDish.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initTextChangeEvents();
    }

    private void initTextChangeEvents() {
        final EditText etDishName = findViewById(R.id.dishnameEdit);
        etDishName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                currentRate.setDishname(etDishName.getText().toString());
            }
        });

        final EditText etDishType = findViewById(R.id.dishtypeEdit);
        etDishType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                currentRate.setDishtype(etDishType.getText().toString());
            }
        });
    }

    private void saveRatingAndNavigateToMain() {
        String dishName = dishNameEditText.getText().toString();
        String dishType = dishTypeEditText.getText().toString();
        float rating = ratingBar.getRating();

        if (restaurantId != -1) {
            RestaurantDataSource dataSource = new RestaurantDataSource(this);
            dataSource.open();

                long newRatingId = dataSource.insertRating(restaurantId, dishName, dishType, rating);

                if (newRatingId != -1) {
                    ratingId = newRatingId;
                    showMessage("Dish rating added successfully");
                    navigateToMainActivity();
                } else {
                    showMessage("Failed to add dish rating");
                }

                dataSource.close();
            } else {
                showMessage("Invalid restaurant ID");
            }

            dishNameEditText.getText().clear();
            dishTypeEditText.getText().clear();
            ratingBar.setRating(0.0f);
        }
    private void navigateToMainActivity() {
        Intent intent = new Intent(RateDish.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}
