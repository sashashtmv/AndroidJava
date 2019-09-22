package com.sashashtmv.taskmanager.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.sashashtmv.taskmanager.fragments.CurrentTaskFragment;
import com.sashashtmv.taskmanager.fragments.DoneTaskFragment;


public class TabAdapter extends FragmentStatePagerAdapter {
    private int numberOfTabs;
    //для получения экземпляра уже существующего фрагмента из тейбллейаут создадим две стат.константы
    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;
    //для того, чтобы объекты не создавались при каждом вызове метода getItem, нужно инициализировать классы CurrentTaskFragment и DoneTaskFragment
    CurrentTaskFragment mCurrentTaskFragment;
    DoneTaskFragment mDoneTaskFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs, int typeTask) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        mCurrentTaskFragment = CurrentTaskFragment.newInstance(typeTask);
        mDoneTaskFragment = DoneTaskFragment.newInstance(typeTask);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mCurrentTaskFragment;
            case 1:
                return mDoneTaskFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
