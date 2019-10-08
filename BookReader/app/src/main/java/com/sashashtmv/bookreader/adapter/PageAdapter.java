package com.sashashtmv.bookreader.adapter;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sashashtmv.bookreader.R;
import com.sashashtmv.bookreader.model.PageModel;
import com.sashashtmv.bookreader.model.PreferenceHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.ViewHolder> {

    private List<PageModel> mPageModels;
    private String mNameBook = "";
    private PreferenceHelper mPreferenceHelper;
    private int proc = 0;

    public PageAdapter(List<PageModel> pageModels, String nameBook, PreferenceHelper preferenceHelper) {
        this.mPageModels = pageModels;
        this.mNameBook = nameBook;
        this.mPreferenceHelper = preferenceHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
        Log.i(TAG, "doBindViewHolder: position - " + position );
    }

    @Override
    public int getItemCount() {
        return mPageModels == null ? 0 : mPageModels.size();
    }

    public void addItems(List<PageModel> pageModels) {
        mPageModels.addAll(pageModels);
        notifyDataSetChanged();
    }

    PageModel getItem(int position) {
        return mPageModels.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_number_page)
        TextView textViewTitle;
        @BindView(R.id.tv_procent)
        TextView textViewProcent;
        @BindView(R.id.tv_description)
        TextView textViewDescription;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(int position) {
            mPreferenceHelper.putInt(mNameBook + "index", position);
            PageModel item = mPageModels.get(position);

            proc = mPreferenceHelper.getInt(mNameBook + "proc");

            if((position+1) * 100 / mPageModels.size() > proc){
                Log.i(TAG, "doBind: proc - " + (position+1) * 100 / mPageModels.size() + "  " + proc );
                proc = (position+1) * 100 / mPageModels.size();
                mPreferenceHelper.putInt(mNameBook + "proc", proc);
            }
            Log.i(TAG, "doBind: position - "  + position );

            textViewTitle.setText(item.getNumberPage());
            textViewDescription.setMovementMethod(new ScrollingMovementMethod());
            textViewDescription.setText(item.getDescription());
            textViewProcent.setText("Прочитано: " + proc + "%");

        }
    }

}
