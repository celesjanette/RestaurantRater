package com.example.restaurantrater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RatedMealsAdapter extends RecyclerView.Adapter<RatedMealsAdapter.RatedMealsViewHolder> {
    private ArrayList<Rate> ratedMeals;
    private Context context;
    private static boolean delete = false;
    private OnItemClickListener listener;
    public RatedMealsAdapter(ArrayList<Rate> ratedMeals, Context context) {
        this.ratedMeals = ratedMeals;
        this.context = context;
    }
    public interface OnItemClickListener {
        void onItemClick( int position);
    }

    // Method to set the item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    @NonNull
    @Override
    public RatedMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rated_meal, parent, false);
        return new RatedMealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatedMealsViewHolder holder, int position) {
        Rate rate = ratedMeals.get(position);
        holder.bind(rate);
    }

    @Override
    public int getItemCount() {
        return ratedMeals.size();
    }

    public static class RatedMealsViewHolder extends RecyclerView.ViewHolder {
        TextView dishNameTextView;
        TextView dishTypeTextView;
        TextView ratingTextView;

        public RatedMealsViewHolder(@NonNull View itemView) {
            super(itemView);
            dishNameTextView = itemView.findViewById(R.id.textDishName);
            dishTypeTextView = itemView.findViewById(R.id.textDishType);
            ratingTextView = itemView.findViewById(R.id.textRating);
        }

        public void bind(Rate rate) {
            dishNameTextView.setText(rate.getDishname());
            dishTypeTextView.setText(rate.getDishtype());
            ratingTextView.setText(String.valueOf(rate.getRating()));

            // Set visibility based on the delete status
            if (delete) {
                dishNameTextView.setVisibility(View.GONE);
                dishTypeTextView.setVisibility(View.GONE);
                ratingTextView.setVisibility(View.GONE);
            } else {
                dishNameTextView.setVisibility(View.VISIBLE);
                dishTypeTextView.setVisibility(View.VISIBLE);
                ratingTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
