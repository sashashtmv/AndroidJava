package com.sashashtmv.myReminderEveryDay.adapter;

//с помощью этого класса объединим CurrentTaskAdapter и DoneTaskAdapter

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sashashtmv.myReminderEveryDay.fragment.TaskFragment;
import com.sashashtmv.myReminderEveryDay.model.Item;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Item> mItemList;

    TaskFragment mTaskFragment;


    public TaskAdapter(TaskFragment taskFragment) {
        this.mTaskFragment = taskFragment;
        mItemList = new ArrayList<>();
    }

    public Item getItem(int position){
        return mItemList.get(position);
    }

    public void addItem(Item item){
        mItemList.add(item);
        //сообщаем о добавлении нового элемента в список
        notifyItemInserted(getItemCount()-1);
    }

    public void addItem(int location, Item item){
        mItemList.add(location, item);
        //сообщаем о добавлении нового элемента в определенную позицию списка
        notifyItemInserted(location);
    }

    public void removeItem(int location){
        if(location >= 0 && location <= getItemCount()-1){
            mItemList.remove(location);
            notifyItemRemoved(location);

        }
    }
    public void removeAllItems(){
        if(getItemCount() != 0){
            mItemList = new ArrayList<>();
            notifyDataSetChanged();

        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder{
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

    public TaskFragment getTaskFragment() {
        return mTaskFragment;
    }
}
