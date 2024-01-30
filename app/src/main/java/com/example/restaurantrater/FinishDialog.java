package com.example.restaurantrater;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class FinishDialog extends Dialog {

    private TextView nameRestaurantView;
    private TextView showRating;
    private Button doneButton;

    public FinishDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_dialog);

        nameRestaurantView = findViewById(R.id.nameRestaurantView);
        showRating = findViewById(R.id.showRating);
        doneButton = findViewById(R.id.doneButton);

    }

    public void setRestaurantName(String restaurantName) {
        nameRestaurantView.setText(restaurantName);
    }

    public void setRating(String rating) {
        showRating.setText(rating);
    }
    public void setOnDoneClickListener(Button.OnClickListener listener) {
        doneButton.setOnClickListener(listener);
    }
}

