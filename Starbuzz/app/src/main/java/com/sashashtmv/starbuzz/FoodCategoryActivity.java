package com.sashashtmv.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FoodCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);

        ArrayAdapter<Food> listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                Food.foods);

        ListView adapterFoods = findViewById(R.id.list_foods);
        adapterFoods.setAdapter(listAdapter);

        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> adapterFoods,
                                            View itemView,
                                            int position,
                                            long id) {
                        Intent intent = new Intent(FoodCategoryActivity.this,
                                FoodActivity.class);
                        intent.putExtra(FoodActivity.EXTRA_FOOD, (int) id);
                        startActivity(intent);

                    }
                };

        //Добавить слушателя к списковому представлению

        adapterFoods.setOnItemClickListener(itemClickListener);
    }
}
