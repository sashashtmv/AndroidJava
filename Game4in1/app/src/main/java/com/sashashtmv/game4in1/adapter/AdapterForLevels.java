package com.sashashtmv.game4in1.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.fragments.StartFragment;
import com.sashashtmv.game4in1.model.Item;
import com.sashashtmv.game4in1.model.ModelLevel;

import java.util.ArrayList;
import java.util.List;

public class AdapterForLevels extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> mItemList;
    protected ItemListener mListener;
    protected ModelLevel item;

    StartFragment mStartFragment;
    private static final int TYPE_TASK = 0;
    private static final int TYPE_SEPARATOR = 1;


    public AdapterForLevels(StartFragment startFragment, ItemListener listener) {
        this.mStartFragment = startFragment;
        this.mItemList = new ArrayList<>();
        this.mListener = listener;
    }

    public Item getItem(int position) {
        return mItemList.get(position);
    }

    public void addItems(ArrayList<Item> list) {
        mItemList.addAll(list);
        notifyDataSetChanged();
        //сообщаем о добавлении нового элемента в список
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case TYPE_TASK:
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_level_list, viewGroup, false);

                return new LevelViewHolder(v);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final LevelViewHolder levelViewHolder = (LevelViewHolder) viewHolder;
        levelViewHolder.setData((ModelLevel)mItemList.get(position));

        }


    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

    {
        protected ImageView baseImage;
        protected ImageView done;
        protected ModelLevel item;

        public LevelViewHolder(View itemView) {
            super(itemView);
            baseImage = itemView.findViewById(R.id.iv_base_image);
            //baseImage.setClipToOutline(true);
            done = itemView.findViewById(R.id.iv_done);
            itemView.setOnClickListener(this);
        }

        public void setData(ModelLevel item) {
            this.item = item;
            Bitmap bitmap =  item.getBitmap1();
//            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(Resources.getSystem(), bitmap);
//            dr.setCornerRadius(10);
//            this.baseImage.setImageDrawable(dr);
            this.baseImage.setImageBitmap(bitmap);
            this.done.setImageResource(R.drawable.icon_done);


        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }
    public interface ItemListener {
        void onItemClick(ModelLevel item);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public StartFragment getStartFragment() {
        return mStartFragment;
    }
}
