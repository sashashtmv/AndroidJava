package com.sashashtmv.workout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutDetailFragment extends Fragment {

    private long workoutID;
    public WorkoutDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    //получаем ID выбранного комплекса упражнений
    public void setWorkoutID(long workoutID) {
        this.workoutID = workoutID;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view!=null){
            TextView name = view.findViewById(R.id.workout_title);
            Workout workoutSelected = Workout.listworkout[(int)workoutID];
            name.setText(workoutSelected.getName());
            TextView description = view.findViewById(R.id.workout_description);
            description.setText(workoutSelected.getDescription());
        }
    }
}
