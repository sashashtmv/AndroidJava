package com.sashashtmv.taskmanager.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sashashtmv.taskmanager.R;
import com.sashashtmv.taskmanager.adapter.CurrentTasksAdapter;
import com.sashashtmv.taskmanager.database.DBHelper;
import com.sashashtmv.taskmanager.model.ModelSeparator;
import com.sashashtmv.taskmanager.model.ModelTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends TaskFragment {

    private int mTypeTask;

    public CurrentTaskFragment() {
        // Required empty public constructor
    }

    public static CurrentTaskFragment newInstance(int typeTask) {
        CurrentTaskFragment fragment = new CurrentTaskFragment();
        Bundle args = new Bundle();
        fragment.mTypeTask = typeTask;
        fragment.setArguments(args);

        return fragment;
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
        // аргументы getTasks - (первый)указываем запрос к типу столбца, (второй)указываем тип данных столбца, которые нужно из него выбрать, (третий)сортировка
        checkAdapter();
        mAdapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(mMainActivity.mDBHelper.query().getTasks(DBHelper.SELECTION_TYPE_TASK + " AND " + DBHelper.SELECTION_STATUS + " OR " + DBHelper.SELECTION_STATUS,
                new String[]{Integer.toString(mTypeTask), Integer.toString(ModelTask.STATUS_CURRENT), Integer.toString(ModelTask.STATUS_OVERDUE)}, DBHelper.TASK_DATE_COLUMN ));

        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i),false);
        }
    }

    @Override
    public void moveTask(ModelTask task) {
        mAlarmHelper.removeAlarm(task.getTimeStamp());
        mOnTaskDoneListener.onTaskDone(task);
    }

    @Override
    public void findTask(String title) {
        //будем удалять все элементы из списка задач
        checkAdapter();
        mAdapter.removeAllItems();
        List<ModelTask> tasks = new ArrayList<>();
        tasks.addAll(mMainActivity.mDBHelper.query().getTasks(DBHelper.SELECTION_TYPE_TASK + " AND "+ DBHelper.SELECTION_LIKE_TITLE + " AND " + DBHelper.SELECTION_STATUS +
                        " OR " + DBHelper.SELECTION_STATUS, new String[]{Integer.toString(mTypeTask), "%" + title + "%", Integer.toString(ModelTask.STATUS_CURRENT), Integer.toString(ModelTask.STATUS_OVERDUE)},
                        DBHelper.TASK_DATE_COLUMN ));

        for (int i = 0; i < tasks.size(); i++) {
            addTask(tasks.get(i),false);
        }
    }

    @Override
    public void addTask(ModelTask newTask, boolean safeToDB) {
        int position = -1;
        ModelSeparator separator = null;
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
        if(newTask.getDate() != 0){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(newTask.getDate());
            int date;
            if(newTask.getTypeTask() == ModelTask.TYPE_DAY){
                date = Calendar.DAY_OF_YEAR;
            }else if (newTask.getTypeTask() == ModelTask.TYPE_WEEK){
                date = Calendar.WEEK_OF_YEAR;
            }else if(newTask.getTypeTask() == ModelTask.TYPE_MONTH){
                date = Calendar.MONTH;
            }else if(newTask.getTypeTask() == ModelTask.TYPE_YEAR){
                date = Calendar.YEAR;
            }else date = 0;

            if(calendar.get(date) < Calendar.getInstance().get(date)){
                newTask.setDateStatus(ModelSeparator.TYPE_OVERDUE);
                if(!mAdapter.containSeparatorOverdue){
                    mAdapter.containSeparatorOverdue = true;
                    separator = new ModelSeparator(ModelSeparator.TYPE_OVERDUE);
                }
            }
            else if(calendar.get(date) == Calendar.getInstance().get(date)){
                newTask.setDateStatus(ModelSeparator.TYPE_TODAY);
                if(!mAdapter.containSeparatorToday){
                    mAdapter.containSeparatorToday = true;
                    separator = new ModelSeparator(ModelSeparator.TYPE_TODAY);
                }
            }
            else if(calendar.get(date) == Calendar.getInstance().get(date + 1)){
                newTask.setDateStatus(ModelSeparator.TYPE_TOMORROW);
                if(!mAdapter.containSeparatorTomorrow){
                    mAdapter.containSeparatorTomorrow = true;
                    separator = new ModelSeparator(ModelSeparator.TYPE_TOMORROW);
                }
            }
            else if(calendar.get(date) > Calendar.getInstance().get(date+1)){
                newTask.setDateStatus(ModelSeparator.TYPE_FUTURE);
                if(!mAdapter.containSeparatorFuture){
                    mAdapter.containSeparatorFuture = true;
                    separator = new ModelSeparator(ModelSeparator.TYPE_FUTURE);
                }
            }
        }

        if(position != -1){
            if(!mAdapter.getItem(position-1).isTask()){
                if(position-2 >= 0 && mAdapter.getItem(position-2).isTask()){
                    ModelTask task = (ModelTask)mAdapter.getItem(position-2);
                    if(task.getDateStatus() == newTask.getDateStatus()){
                        position -= 1;
                    }
                }else  if(position - 2 < 0 && newTask.getDate() == 0){
                    position -= 1;
                }
            }
            if(separator != null){
                mAdapter.addItem(position-1, separator);
            }
            mAdapter.addItem(position, newTask);
        }else {
            if(separator != null){
                mAdapter.addItem(separator);
            }
            mAdapter.addItem(newTask);
        }

        if(safeToDB){
            mMainActivity.mDBHelper.saveTask(newTask);
        }
    }
    @Override
    public void checkAdapter() {
        if (mAdapter == null) {
            mAdapter = new CurrentTasksAdapter(this);
            addTaskFromDB();
        }
    }
}
