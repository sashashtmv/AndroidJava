package com.sashashtmv.taskmanager.fragments;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sashashtmv.taskmanager.MainActivity;
import com.sashashtmv.taskmanager.R;
import com.sashashtmv.taskmanager.adapter.TabAdapter;
import com.sashashtmv.taskmanager.dialog.AddingTaskDialogFragment;
import com.sashashtmv.taskmanager.model.ModelTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {

    public MainActivity mMainActivity;
    TabAdapter mTabAdapter;
    FragmentManager mFragmentManager;

    TaskFragment mCurrentTaskFragment;
    TaskFragment mDoneTaskFragment;
    SearchView mSearchView;
    private CallbackFragments mCallbackFragments;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mMainActivity = (MainActivity) getActivity();
        }
    }

    public MonthFragment() {
        // Required empty public constructor
    }

    public static MonthFragment newInstance() {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    public interface CallbackFragments {
        void onCallbackFragments(TaskFragment currentTaskFragment, TaskFragment doneTaskFragment);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.design_default_color_primary_dark));

            activity.setSupportActionBar(toolbar);
        }
        mFragmentManager = activity.getFragmentManager();

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        // создаем вкладки методом addTab
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task));

        final ViewPager viewPager = view.findViewById(R.id.pager);
        mTabAdapter = new TabAdapter(mFragmentManager, 2, ModelTask.TYPE_MONTH);

        viewPager.setAdapter(mTabAdapter);
        mCallbackFragments = (CallbackFragments) activity;
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
        mCallbackFragments.onCallbackFragments(mCurrentTaskFragment, mDoneTaskFragment);
        mSearchView = view.findViewById(R.id.search_view);

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
        FloatingActionButton fab = view.findViewById(R.id.float_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addingTaskDialogFragment = AddingTaskDialogFragment.newInstance(ModelTask.TYPE_MONTH);
                addingTaskDialogFragment.show(mFragmentManager, "AddingTaskDialogFragment");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
