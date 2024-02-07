package com.example.restaurantrater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class RatedMealsActivity extends AppCompatActivity {
    RecyclerView ratedMealsRecyclerView;
    RatedMealsAdapter ratedMealsAdapter;

    ArrayList<Rate> ratedMeals;
    private RatedMealsAdapter.OnItemClickListener onItemClickListener = new RatedMealsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

            Intent intent = new Intent(RatedMealsActivity.this, RateDish.class);

            intent.putExtra("rateId", ratedMeals.get(position).getRateID());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rated_meals);
        initDeleteSwitch();
    }

    @Override
    public void onResume() {
        super.onResume();
        long restaurantId = getRestaurantId();

        RestaurantDataSource ds = new RestaurantDataSource(this);
        try {
            ds.open();
            ratedMeals = ds.getRatedMeals(restaurantId);
            ds.close();

            if (ratedMeals != null && ratedMeals.size() > 0) {
                ratedMealsRecyclerView = findViewById(R.id.RatedMealsRecyclerView);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                ratedMealsRecyclerView.setLayoutManager(layoutManager);
                ratedMealsAdapter = new RatedMealsAdapter(ratedMeals, this); // Pass the context
                ratedMealsAdapter.setOnItemClickListener(onItemClickListener);
                ratedMealsRecyclerView.setAdapter(ratedMealsAdapter);
            } else {
                // Handle the case when there are no rated meals
                Toast.makeText(this, "No rated meals found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving rated meals", Toast.LENGTH_LONG).show();
        }
    }

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean status = compoundButton.isChecked();
                if (ratedMealsAdapter != null) {
                    ratedMealsAdapter.setDelete(status);
                    ratedMealsAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    private long getRestaurantId() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("restaurantId")) {
            return intent.getLongExtra("restaurantId", -1);
        } else {

            return -1;
        }
    }
}