package com.example.restaurantrater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class RateDish extends AppCompatActivity {
    private TextView restaurantFillView;
    private EditText dishNameEditText;
    private EditText dishTypeEditText;
    private RatingBar ratingBar;
    private Button finishButton;
    private Button backButton;
    private String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_dish);
        restaurantFillView = findViewById(R.id.restaurantfillView);
        dishNameEditText = findViewById(R.id.dishnameEdit);
        dishTypeEditText = findViewById(R.id.dishtypeEdit);
        ratingBar = findViewById(R.id.ratingBar);
        finishButton = findViewById(R.id.finishButton);
        backButton = findViewById(R.id.backButton);
        String restaurantName = getIntent().getStringExtra("restaurantName");
        restaurantFillView.setText(restaurantName);
        restaurantName = getIntent().getStringExtra("RESTAURANT_NAME");
        restaurantFillView.setText(restaurantName);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRating();
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
    }

    private void saveRating() {
        String dishName = dishNameEditText.getText().toString();
        String dishType = dishTypeEditText.getText().toString();
        float rating = ratingBar.getRating();
        long restaurantId = getIntent().getLongExtra("restaurantId", -1);

        if (restaurantId != -1) {

            RestaurantDataSource dataSource = new RestaurantDataSource(this);
            dataSource.open();
            dataSource.insertRating(restaurantId, dishName, dishType, rating);
            dataSource.close();

            // Show the finish dialog
            showFinishDialog(restaurantName, String.valueOf(rating));
        } else {
            // Handle the case where restaurantId is not valid
        }
    }

    private void showFinishDialog(String restaurantName, String rating) {
        FinishDialog finishDialog = new FinishDialog(this);
        finishDialog.setRestaurantName(restaurantName);
        finishDialog.setRating(rating);

        finishDialog.setOnDoneClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishDialog.dismiss();

                Intent intent = new Intent(RateDish.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        finishDialog.show();
    }
}
