package com.sashashtmv.game4in1.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.fragments.StartFragment;
import com.sashashtmv.game4in1.model.Item;
import com.sashashtmv.game4in1.model.ModelLevel;

import java.util.ArrayList;
import java.util.List;

public class AdapterForLevels extends RecyclerView.Adapter<AdapterForLevels.LevelViewHolder> {

    List<ModelLevel> mItemList;
    protected static ItemListener mListener;
    protected ModelLevel item;
    private Context context;

    private static final int TYPE_TASK = 0;
    private static final int TYPE_SEPARATOR = 1;


    public AdapterForLevels(Context context, List<ModelLevel> itemList, ItemListener listener) {
        //setHasStableIds(true);
        this.mItemList = itemList;
        this.mListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

//        switch (viewType) {
//            case TYPE_TASK:
//                View v = LayoutInflater.from(viewGroup.getContext())
//                        .inflate(R.layout.item_level_list, null);
//
//                return new LevelViewHolder(v);
//
//            default:
                return new LevelViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_level_list, null));
//                return null;
        }
//    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder viewHolder, int position) {
        ModelLevel level = (ModelLevel) mItemList.get(position);
        //Bitmap bitmap = level.getBitmap1();
        if(level!=null) {
            if (level.getStatus() == ModelLevel.STATUS_DONE) {
                viewHolder.setData(level, position, context);
            } else if (level.getStatus() == ModelLevel.STATUS_AVALABLE) {
                viewHolder.setAvalable(level, position, context);

            } else {
                viewHolder.setNotAvalable(level, position,context);
            }
        }

    }


    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView baseImage;
        protected ImageView done;
        protected TextView numberLevel;
        protected ModelLevel item;

        public LevelViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            baseImage = itemView.findViewById(R.id.iv_base_image);
            done = itemView.findViewById(R.id.iv_done);
            numberLevel = itemView.findViewById(R.id.tv_number_level);
        }

        public void setData(ModelLevel item, int position, Context context) {
            this.item = item;
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier(item.getBitmap1(), "drawable", context.getPackageName()));

            this.baseImage.setImageBitmap(bitmap);
            this.baseImage.setColorFilter(Color.parseColor("#6D9DE2"), PorterDuff.Mode.MULTIPLY);
            this.done.setImageResource(R.drawable.icon_done);
            String str = "" + (position + 1);
            this.numberLevel.setText(str);
            this.numberLevel.setTextSize(32);
            this.numberLevel.setTextColor(Color.parseColor("#FFFFFF"));

        }

        public void setAvalable(ModelLevel item, int position, Context context) {
            this.item = item;
            this.done.setVisibility(View.GONE);
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier(item.getBitmap1(), "drawable", context.getPackageName()));

            this.baseImage.setImageBitmap(bitmap);
            String str = "" + (position + 1);
            this.numberLevel.setText(str);
            this.numberLevel.setTextSize(32);
            this.numberLevel.setTextColor(Color.parseColor("#133B6B"));
        }

        public void setNotAvalable(ModelLevel item, int position, Context context) {
            this.item = item;
            this.done.setVisibility(View.GONE);
            this.baseImage.setColorFilter(Color.parseColor("#6D9DE2"));
            String str = "" + (position + 1);
            this.numberLevel.setText(str);
            this.numberLevel.setTextSize(32);
            this.numberLevel.setTextColor(Color.parseColor("#153351"));
        }

        @Override
        public void onClick(View view) {
            if (item != null && AdapterForLevels.mListener != null && item.getStatus() == ModelLevel.STATUS_AVALABLE || item.getStatus() == ModelLevel.STATUS_DONE) {
                AdapterForLevels.mListener.onItemClick(item);
            }
            //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    public interface ItemListener {
        void onItemClick(ModelLevel item);
    }

}
