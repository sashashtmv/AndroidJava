package com.sashashtmv.taskmanager;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.sashashtmv.taskmanager.adapter.TabAdapter;
import com.sashashtmv.taskmanager.alarm.AlarmHelper;
import com.sashashtmv.taskmanager.database.DBHelper;
import com.sashashtmv.taskmanager.dialog.AddingTaskDialogFragment;
import com.sashashtmv.taskmanager.dialog.EditTaskDialogFragment;
import com.sashashtmv.taskmanager.fragments.CurrentTaskFragment;
import com.sashashtmv.taskmanager.fragments.DayFragment;
import com.sashashtmv.taskmanager.fragments.DoneTaskFragment;
import com.sashashtmv.taskmanager.fragments.FifeYearsFragment;
import com.sashashtmv.taskmanager.fragments.MonthFragment;
import com.sashashtmv.taskmanager.fragments.SplashFragment;
import com.sashashtmv.taskmanager.fragments.StartFragment;
import com.sashashtmv.taskmanager.fragments.TaskFragment;
import com.sashashtmv.taskmanager.fragments.WeekFragment;
import com.sashashtmv.taskmanager.fragments.YearFragment;
import com.sashashtmv.taskmanager.model.ModelTask;

public class MainActivity extends AppCompatActivity implements AddingTaskDialogFragment.AddingTaskListener, CurrentTaskFragment.OnTaskDoneListener,
        DoneTaskFragment.OnTaskRestoreListener, EditTaskDialogFragment.EditingTaskListener, DayFragment.CallbackFragments, WeekFragment.CallbackFragments,
        MonthFragment.CallbackFragments, YearFragment.CallbackFragments, FifeYearsFragment.CallbackFragments {
    FragmentManager mFragmentManager;

    //PreferenceHelper mPreferenceHelper;

    TabAdapter mTabAdapter;

    TaskFragment mCurrentTaskFragment;
    TaskFragment mDoneTaskFragment;
    public DBHelper mDBHelper;
    SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        AlarmHelper.getInstance().init(getApplicationContext());

        mDBHelper = new DBHelper(getApplicationContext());
        mFragmentManager = getFragmentManager();

        runStartFragment();
        runSplash();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }

    public void runSplash() {
        SplashFragment splashFragment = SplashFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, splashFragment, "dd")
                .addToBackStack(null)
                .commit();

    }

    public void runStartFragment() {
        StartFragment startFragment = StartFragment.newInstance();
        //StartFragment startFragment = new StartFragment();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, startFragment, "start fragment")
                .addToBackStack(null)
                .commit();

    }
    @Override
    public void onTaskAdded(ModelTask newTask) {
        mCurrentTaskFragment.addTask(newTask, true);
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Task adding cancel", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onTaskDone(ModelTask task) {
        mDoneTaskFragment.addTask(task, false);
    }

    @Override
    public void onTaskRestore(ModelTask task) {
        mCurrentTaskFragment.addTask(task, false);
    }

    @Override
    public void onTaskEditor(ModelTask updatedTask) {
        mCurrentTaskFragment.updateTask(updatedTask);
        mDBHelper.update().task(updatedTask);
    }

    @Override
    public void onCallbackFragments(TaskFragment currentTaskFragment, TaskFragment doneTaskFragment) {
        mCurrentTaskFragment = currentTaskFragment;
        mDoneTaskFragment = doneTaskFragment;
    }
}
