package com.sashashtmv.workout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_WORKOUT_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WorkoutDetailFragment detailFragment = (WorkoutDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        int workoutId = (int) getIntent().getExtras().get(EXTRA_WORKOUT_ID);
        detailFragment.setWorkoutID(workoutId);
    }
}
