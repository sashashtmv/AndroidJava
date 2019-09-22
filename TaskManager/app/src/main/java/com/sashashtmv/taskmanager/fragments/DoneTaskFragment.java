package com.sashashtmv.taskmanager.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sashashtmv.taskmanager.R;
import com.sashashtmv.taskmanager.adapter.DoneTaskAdapter;
import com.sashashtmv.taskmanager.database.DBHelper;
import com.sashashtmv.taskmanager.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragment extends TaskFragment {

    private int mTypeTask;

    public DoneTaskFragment() {
        // Required empty public constructor
    }

    public static DoneTaskFragment newInstance(int typeTask) {
        DoneTaskFragment fragment = new DoneTaskFragment();
        Bundle args = new Bundle();
        fragment.mTypeTask = typeTask;
        fragment.setArguments(args);

        return fragment;
    }
    OnTaskRestoreListener mOnTaskRestoreListener;
    public interface OnTaskRestoreListener{
        void onTaskRestore(ModelTask task);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mOnTaskRestoreListener = (OnTaskRestoreListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnTaskRestoreListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_done_task, container, false);
        mRecyclerView = view.findViewById(R.id.rvDoneTasks);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DoneTaskAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void addTaskFromDB() {
        checkAdapter();
        mAdapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(mMainActivity.mDBHelper.query().getTasks(DBHelper.SELECTION_TYPE_TASK + " AND " + DBHelper.SELECTION_STATUS,
                new String[]{Integer.toString(mTypeTask), Integer.toString(ModelTask.STATUS_DONE)}, DBHelper.TASK_DATE_COLUMN ));

        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i),false);
        }
    }

    @Override
    public void moveTask(ModelTask task) {
        if(task.getDate() != 0){
            mAlarmHelper.setAlarm(task);
        }
        mOnTaskRestoreListener.onTaskRestore(task);
    }

    @Override
    public void findTask(String title) {
        checkAdapter();
        //будем удалять все элементы из списка задач
        mAdapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(mMainActivity.mDBHelper.query().getTasks(DBHelper.SELECTION_TYPE_TASK + " AND " + DBHelper.SELECTION_LIKE_TITLE + " AND " + DBHelper.SELECTION_STATUS +
                        " OR " + DBHelper.SELECTION_STATUS, new String[]{Integer.toString(mTypeTask), "%" + title + "%", Integer.toString(ModelTask.STATUS_DONE)},
                DBHelper.TASK_DATE_COLUMN ));

        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i),false);
        }
    }

    @Override
    public void addTask(ModelTask newTask, boolean safeToDB) {
        int position = -1;
        checkAdapter();
        //чтобы элементы добавлялись в определённой последовательности
        for(int i = 0; i < mAdapter.getItemCount(); i++){
            if(mAdapter.getItem(i).isTask()){
                ModelTask task = (ModelTask) mAdapter.getItem(i);
                if(newTask.getDate() < task.getDate()){
                    position = i;
                    break;
                }
            }
        }
        if(position != -1){
            mAdapter.addItem(position, newTask);
        }else mAdapter.addItem(newTask);

        if(safeToDB){
            mMainActivity.mDBHelper.saveTask(newTask);
        }
    }
    @Override
    public void checkAdapter() {
        if (mAdapter == null) {
            mAdapter = new DoneTaskAdapter(this);
            addTaskFromDB();
        }
    }
}
