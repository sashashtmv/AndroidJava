package com.sashashtmv.shoppinglist.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.shoppinglist.fragment.PurchaseFragment;
import com.sashashtmv.shoppinglist.model.ModelPurchase;

import java.util.ArrayList;
import java.util.List;

public abstract class PurchaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    List<ModelPurchase> mItemList;

    PurchaseFragment mPurchaseFragment;


    public PurchaseAdapter(PurchaseFragment purchaseFragment) {
        this.mPurchaseFragment = purchaseFragment;
        mItemList = new ArrayList<>();
    }

    public List<ModelPurchase> getItemList() {
        return mItemList;
    }
    public ModelPurchase getItem(int position){
        return mItemList.get(position);
    }

    public void addItem(ModelPurchase item){
        mItemList.add(item);
        //сообщаем о добавлении нового элемента в список
        notifyItemInserted(getItemCount()-1);
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

    protected class PurchaseViewHolder extends RecyclerView.ViewHolder{
        protected ImageView image;
        protected TextView title;
        protected CheckBox mCheckBox;


        public PurchaseViewHolder(View itemView, ImageView image, TextView title, CheckBox checkBox) {
            super(itemView);
            this.image = image;
            this.title = title;
            this.mCheckBox = checkBox;

        }
    }

    public PurchaseFragment getPurchaseFragment() {
        return mPurchaseFragment;
    }
}
