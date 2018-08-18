package com.sashashtmv.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodActivity extends Activity {

    private ImageView imageFood;
    private TextView nameFood;
    private TextView descriptionFood;

    public static final String EXTRA_FOOD = "extra food";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent intent = getIntent();
        int idFood = intent.getIntExtra(EXTRA_FOOD, 0);
        Food food = Food.foods[idFood];

        imageFood = findViewById(R.id.image_food);
        nameFood = findViewById(R.id.name_foods);
        descriptionFood = findViewById(R.id.description_food);

        imageFood.setImageResource(food.getImageResourceID());
        imageFood.setContentDescription(food.getName());
        nameFood.setText(food.getName());
        descriptionFood.setText(food.getDescription());
    }
}
