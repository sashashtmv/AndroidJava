package com.sashashtmv.taskmanager.adapter;

//с помощью этого класса объединим CurrentTaskAdapter и DoneTaskAdapter

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.sashashtmv.taskmanager.fragments.TaskFragment;
import com.sashashtmv.taskmanager.model.Item;
import com.sashashtmv.taskmanager.model.ModelSeparator;
import com.sashashtmv.taskmanager.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Item> mItemList;

    TaskFragment mTaskFragment;

    public boolean containSeparatorOverdue;
    public boolean containSeparatorToday;
    public boolean containSeparatorTomorrow;
    public boolean containSeparatorFuture;


    public TaskAdapter(TaskFragment taskFragment) {
        this.mTaskFragment = taskFragment;
        mItemList = new ArrayList<>();
    }

    public Item getItem(int position) {
        return mItemList.get(position);
    }

    public void addItem(Item item) {
        mItemList.add(item);
        //сообщаем о добавлении нового элемента в список
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item) {
        mItemList.add(location, item);
        //сообщаем о добавлении нового элемента в определенную позицию списка
        notifyItemInserted(location);
    }

    public void updateTask(ModelTask newTask) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).isTask()) {
                ModelTask task = (ModelTask) getItem(i);
                if (newTask.getTimeStamp() == task.getTimeStamp()) {
                    removeItem(i);
                    getTaskFragment().addTask(newTask, false);
                }
            }
        }
    }

    //    public void removeItem(int location) {
//        if (location >= 0 && location <= getItemCount() -1) {
//            mItemList.remove(location);
//            notifyItemRemoved(location);
//            if (location - 1 >= 0 && location <= getItemCount() - 1) {
//                if (!getItem(location).isTask() && !getItem(location - 1).isTask()) {
//
//                    ModelSeparator separator = (ModelSeparator) getItem(location - 1);
//
//                    switch (separator.getType()) {
//                        case ModelSeparator.TYPE_OVERDUE:
//                            containSeparatorOverdue = false;
//                            break;
//                        case ModelSeparator.TYPE_TODAY:
//                            containSeparatorToday = false;
//                            break;
//                        case ModelSeparator.TYPE_TOMORROW:
//                            containSeparatorTomorrow = false;
//                            break;
//                        case ModelSeparator.TYPE_FUTURE:
//                            containSeparatorFuture = false;
//                            break;
//                    }
//                    mItemList.remove(location - 1);
//                    notifyItemRemoved(location - 1);
//                }
//
//            } else if (getItemCount() - 1 >= 0 && !getItem(getItemCount() - 1).isTask()) {
//                ModelSeparator separator = (ModelSeparator) getItem(getItemCount() - 1);
//                switch (separator.getType()) {
//                    case ModelSeparator.TYPE_OVERDUE:
//                        containSeparatorOverdue = false;
//                        break;
//                    case ModelSeparator.TYPE_TODAY:
//                        containSeparatorToday = false;
//                        break;
//                    case ModelSeparator.TYPE_TOMORROW:
//                        containSeparatorTomorrow = false;
//                        break;
//                    case ModelSeparator.TYPE_FUTURE:
//                        containSeparatorFuture = false;
//                        break;
//                }
//                int loc = getItemCount() - 1;
//                mItemList.remove(loc);
//                notifyItemRemoved(loc);
//            }
//        }
//    }
    public void removeItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            mItemList.remove(location);
            notifyItemRemoved(location);
            if (location - 1 >= 0 && location <= getItemCount() - 1) {
                if (!getItem(location).isTask() && !getItem(location - 1).isTask()) {
                    ModelSeparator separator = (ModelSeparator) getItem(location - 1);
                    checkSeparators(separator.getType());
                    mItemList.remove(location - 1);
                    notifyItemRemoved(location - 1);
                }

            } else if (getItemCount() - 1 >= 0 && !getItem(getItemCount() - 1).isTask()) {
                ModelSeparator separator = (ModelSeparator) getItem(getItemCount() - 1);
                checkSeparators(separator.getType());
                //сделаем локальную переменную, чтоб она не зависила от getItemCount() после того, как Item удален
                int locationTemp = getItemCount() - 1;
                mItemList.remove(locationTemp);
                notifyItemRemoved(locationTemp);
            }
        }
    }


        public void checkSeparators (int type){
            switch (type) {
                case ModelSeparator.TYPE_OVERDUE:
                    containSeparatorOverdue = false;
                    break;
                case ModelSeparator.TYPE_TODAY:
                    containSeparatorToday = false;
                    break;
                case ModelSeparator.TYPE_TOMORROW:
                    containSeparatorTomorrow = false;
                    break;
                case ModelSeparator.TYPE_FUTURE:
                    containSeparatorFuture = false;
                    break;

            }

        }
        public void removeAllItems () {
            if (getItemCount() != 0) {
                mItemList = new ArrayList<>();
                notifyDataSetChanged();
                containSeparatorOverdue = false;
                containSeparatorToday = false;
                containSeparatorTomorrow = false;
                containSeparatorFuture = false;

            }
        }

        @Override
        public int getItemCount () {
            return mItemList.size();
        }

        protected class TaskViewHolder extends RecyclerView.ViewHolder {
            protected TextView title;
            protected TextView date;
            protected CircleImageView priority;

            public TaskViewHolder(View itemView, TextView title, TextView date, CircleImageView priority) {
                super(itemView);
                this.title = title;
                this.date = date;
                this.priority = priority;
            }
        }
        //чтобы знать, какие сепараторы есть у нас в списке в данный момент
        protected class SeparatorViewHolder extends RecyclerView.ViewHolder {
            protected TextView type;

            public SeparatorViewHolder(@NonNull View itemView, TextView type) {
                super(itemView);
                this.type = type;
            }
        }

        public TaskFragment getTaskFragment () {
            return mTaskFragment;
        }
    }
