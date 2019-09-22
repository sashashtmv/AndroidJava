package com.sashashtmv.myquiz.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sashashtmv.myquiz.R;
import com.sashashtmv.myquiz.listeners.ListItemClickListener;
import com.sashashtmv.myquiz.models.quiz.CategoryModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context mContext;
    private Activity mActivity;
    private ArrayList<CategoryModel> mCategoryModelArrayList;
    private ListItemClickListener mListItemClickListener;

    public CategoryAdapter(Context context, Activity activity, ArrayList<CategoryModel> categoryModelArrayList) {
        mContext = context;
        mActivity = activity;
        mCategoryModelArrayList = categoryModelArrayList;
    }

    public void setListItemClickListener(ListItemClickListener itemClickListener){
        this.mListItemClickListener = itemClickListener;
    }

    //возвращает вьюхолдер списку макета элемента
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_recycler, parent, false);
        return new ViewHolder(view, viewType, mListItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        final CategoryModel model = mCategoryModelArrayList.get(position);

        String categoryName = model.getCategoryName();
        holder.tvCategoryTitle.setText(Html.fromHtml(categoryName));
        holder.tvCategoryId.setText(String.valueOf(position + 1));

        switch (position){
            case 0:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_yellow));
                break;
            case 1:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_green));
                break;
            case 2:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_blue));
                break;
            case 3:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_orange));
                break;
            case 4:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_red));
                break;
            case 5:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_purple));
                break;
            case 6:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_yellow));
                break;
            case 7:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_green));
                break;
            case 8:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_blue));
                break;
            case 9:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_orange));
                break;
            case 10:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_red));
                break;
            case 11:
                holder.lytContainer.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rectangle_purple));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return (null != mCategoryModelArrayList ? mCategoryModelArrayList.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RelativeLayout lytContainer;
        private TextView tvCategoryTitle, tvCategoryId;
        private ListItemClickListener mListItemClickListener;

        public ViewHolder(@NonNull View itemView, int ViewType, ListItemClickListener itemClickListener) {
            super(itemView);
            this.mListItemClickListener = itemClickListener;
            lytContainer = itemView.findViewById(R.id.lytContainer);
            tvCategoryId = itemView.findViewById(R.id.tvCategoryId);
            tvCategoryTitle = itemView.findViewById(R.id.tvCategoryTitle);

            lytContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListItemClickListener != null){
                mListItemClickListener.onItemClick(getLayoutPosition(), v);
            }
        }

    }
}
