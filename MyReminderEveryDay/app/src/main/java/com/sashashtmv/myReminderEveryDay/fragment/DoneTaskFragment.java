package com.sashashtmv.myReminderEveryDay.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sashashtmv.myReminderEveryDay.R;
import com.sashashtmv.myReminderEveryDay.adapter.CurrentTasksAdapter;
import com.sashashtmv.myReminderEveryDay.adapter.DoneTaskAdapter;
import com.sashashtmv.myReminderEveryDay.database.DBHelper;
import com.sashashtmv.myReminderEveryDay.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragment extends TaskFragment {

    public DoneTaskFragment() {
        // Required empty public constructor
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
        mAdapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(mMainActivity.mDBHelper.query().getTasks(DBHelper.SELECTION_STATUS,
                new String[]{Integer.toString(ModelTask.STATUS_DONE)}, DBHelper.TASK_DATE_COLUMN ));

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
        //будем удалять все элементы из списка задач
        mAdapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(mMainActivity.mDBHelper.query().getTasks(DBHelper.SELECTION_LIKE_TITLE + " AND " + DBHelper.SELECTION_STATUS +
                        " OR " + DBHelper.SELECTION_STATUS, new String[]{"%" + title + "%", Integer.toString(ModelTask.STATUS_DONE)},
                DBHelper.TASK_DATE_COLUMN ));

        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i),false);
        }
    }

    @Override
    public void addTask(ModelTask newTask, boolean safeToDB) {
        int position = -1;
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
        if(mAdapter == null){
            mAdapter = new DoneTaskAdapter(this);

            addTaskFromDB();
        }
    }
}
