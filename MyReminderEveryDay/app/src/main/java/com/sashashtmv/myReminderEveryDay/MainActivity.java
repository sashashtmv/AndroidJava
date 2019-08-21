package com.sashashtmv.myReminderEveryDay;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;


import com.sashashtmv.myReminderEveryDay.adapter.TabAdapter;
import com.sashashtmv.myReminderEveryDay.alarm.AlarmHelper;
import com.sashashtmv.myReminderEveryDay.database.DBHelper;
import com.sashashtmv.myReminderEveryDay.dialog.AddingTaskDialogFragment;
import com.sashashtmv.myReminderEveryDay.dialog.EditTaskDialogFragment;
import com.sashashtmv.myReminderEveryDay.fragment.CurrentTaskFragment;
import com.sashashtmv.myReminderEveryDay.fragment.DoneTaskFragment;
import com.sashashtmv.myReminderEveryDay.fragment.SplashFragment;
import com.sashashtmv.myReminderEveryDay.fragment.TaskFragment;
import com.sashashtmv.myReminderEveryDay.model.ModelTask;

public class MainActivity extends AppCompatActivity implements AddingTaskDialogFragment.AddingTaskListener, CurrentTaskFragment.OnTaskDoneListener,
        DoneTaskFragment.OnTaskRestoreListener, EditTaskDialogFragment.EditingTaskListener {
    FragmentManager mFragmentManager;

    PreferenceHelper mPreferenceHelper;

    TabAdapter mTabAdapter;

    TaskFragment mCurrentTaskFragment;
    TaskFragment mDoneTaskFragment;
    public DBHelper mDBHelper;
    SearchView mSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceHelper.getInstance().init(getApplicationContext());
        mPreferenceHelper = PreferenceHelper.getInstance();
        AlarmHelper.getInstance().init(getApplicationContext());

        mDBHelper = new DBHelper(getApplicationContext());
        mFragmentManager = getFragmentManager();
        runSplash();

        setUI();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // код для восстановления состояния флага переключателя из сохраненных настроек
        MenuItem splashItem = menu.findItem(R.id.action_splash);
        splashItem.setChecked(mPreferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_splash) {
            //меняем флаг пункта меню на противоположный
            item.setChecked(!item.isChecked());
            mPreferenceHelper.putBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE, item.isChecked());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void runSplash() {
        //реализуем зависимость события показа сплешскрина от значения состояния флага, сохраненного в префернсехелпер
        if (!mPreferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)) {
            SplashFragment splashFragment = new SplashFragment();

            mFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void setUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // создаем вкладки методом addTab
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task));

        final ViewPager viewPager = findViewById(R.id.pager);
        mTabAdapter = new TabAdapter(mFragmentManager, 2);

        viewPager.setAdapter(mTabAdapter);
        // установим слушателя на событие смены вкладок
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //добавим слушатель объекту tabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //определяет, что tab выбран
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //передаем view pager-у необходимый для отображения фрагмент
                viewPager.setCurrentItem(tab.getPosition());
            }

            //определяет, что tab более не выбран
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            //определяет, что выбран ранее выбранный tab
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mCurrentTaskFragment = (CurrentTaskFragment) mTabAdapter.getItem(TabAdapter.CURRENT_TASK_FRAGMENT_POSITION);
        mDoneTaskFragment = (DoneTaskFragment) mTabAdapter.getItem(TabAdapter.DONE_TASK_FRAGMENT_POSITION);

        mSearchView = findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCurrentTaskFragment.findTask(newText);
                mDoneTaskFragment.findTask(newText);
                return false;
            }
        });
        //вешаем слушателя на fab
        FloatingActionButton fab = findViewById(R.id.float_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(mFragmentManager, "AddingTaskDialogFragment");
            }
        });
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
}
