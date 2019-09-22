package com.sashashtmv.myReminderEveryDay.fragment;

//с помощью этого класса объединим CurrentTaskFragment и DoneTaskFragment

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sashashtmv.myReminderEveryDay.MainActivity;

import com.sashashtmv.myReminderEveryDay.R;
import com.sashashtmv.myReminderEveryDay.adapter.TaskAdapter;
import com.sashashtmv.myReminderEveryDay.alarm.AlarmHelper;
import com.sashashtmv.myReminderEveryDay.dialog.EditTaskDialogFragment;
import com.sashashtmv.myReminderEveryDay.model.Item;
import com.sashashtmv.myReminderEveryDay.model.ModelTask;

public abstract class TaskFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected TaskAdapter mAdapter;

    public MainActivity mMainActivity;
    public AlarmHelper mAlarmHelper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            mMainActivity = (MainActivity)getActivity();
        }
        mAlarmHelper = AlarmHelper.getInstance();
        addTaskFromDB();
    }

    public abstract void addTask(ModelTask newTask, boolean safeToDB);

    public void updateTask(ModelTask task){
        mAdapter.updateTask(task);
    }

    //реализация вызова диалога удаления задачи
    public void removeTaskDialog(final int location){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setMessage(R.string.dialog_removing_message);

        Item item = mAdapter.getItem(location);
        if(item.isTask()){
            ModelTask removingTask = (ModelTask)item;
            final long timeStamp = removingTask.getTimeStamp();
            final boolean[] isRemoved = {false};

            dialogBuilder.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAdapter.removeItem(location);
                    isRemoved[0] = true;

                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator), R.string.removed, Snackbar.LENGTH_LONG);
                    snackbar.setAction(R.string.Cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //по нажатию на снекбар, наша удаленная задача будет восстанавливаться из базы данных
                            addTask(mMainActivity.mDBHelper.query().getTask(timeStamp), false);
                            isRemoved[0] = false;
                        }
                    });

                    snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View v) {

                        }

                        @Override
                        public void onViewDetachedFromWindow(View v) {
                            //если у нас не было нажатия на кнопку отмена удаления в снекбаре, то наша задача окнчательно удаляется из базы данных
                            if(isRemoved[0]){
                                mAlarmHelper.removeAlarm(timeStamp);
                                mMainActivity.mDBHelper.removeTask(timeStamp);
                            }
                        }
                    });

                    snackbar.show();

                    dialog.dismiss();
                }
            });

            dialogBuilder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            dialogBuilder.show();
        }
    }

    //метод вызова диалога
    public void  showTaskEditDialog(ModelTask task){
        DialogFragment editingTaskDialog = EditTaskDialogFragment.newInstance(task);
        editingTaskDialog.show(getActivity().getFragmentManager(), "EditTaskDialogFragment");
    }
    public abstract void addTaskFromDB();
    public abstract void checkAdapter();
    public abstract void moveTask(ModelTask task);
    public abstract void findTask(String title);
}
