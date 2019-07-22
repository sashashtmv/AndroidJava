package com.sashashtmv.myReminderEveryDay.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sashashtmv.myReminderEveryDay.R;
import com.sashashtmv.myReminderEveryDay.adapter.CurrentTasksAdapter;
import com.sashashtmv.myReminderEveryDay.database.DBHelper;
import com.sashashtmv.myReminderEveryDay.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends TaskFragment {


    public CurrentTaskFragment() {
        // Required empty public constructor
    }

    OnTaskDoneListener mOnTaskDoneListener;
    public interface OnTaskDoneListener{
        void onTaskDone(ModelTask task);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
                mOnTaskDoneListener = (OnTaskDoneListener)activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnTaskDoneListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_task, container, false);
        mRecyclerView = view.findViewById(R.id.rvCurrentTasks);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CurrentTasksAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void addTaskFromDB() {
        //будем удалять все элементы из списка задач
        mAdapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(mMainActivity.mDBHelper.query().getTasks(DBHelper.SELECTION_STATUS + " OR " + DBHelper.SELECTION_STATUS,
                new String[]{Integer.toString(ModelTask.STATUS_CURRENT), Integer.toString(ModelTask.STATUS_OVERDUE)}, DBHelper.TASK_DATE_COLUMN ));

        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i),false);
        }
    }

    @Override
    public void moveTask(ModelTask task) {
        mOnTaskDoneListener.onTaskDone(task);
    }

    @Override
    public void findTask(String title) {
        //будем удалять все элементы из списка задач
        mAdapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(mMainActivity.mDBHelper.query().getTasks(DBHelper.SELECTION_LIKE_TITLE + " AND " + DBHelper.SELECTION_STATUS +
                        " OR " + DBHelper.SELECTION_STATUS, new String[]{"%" + title + "%", Integer.toString(ModelTask.STATUS_CURRENT), Integer.toString(ModelTask.STATUS_OVERDUE)},
                        DBHelper.TASK_DATE_COLUMN ));

        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i),false);
        }
    }
}
