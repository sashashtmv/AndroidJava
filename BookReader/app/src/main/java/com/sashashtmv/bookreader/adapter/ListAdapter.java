package com.sashashtmv.bookreader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.bookreader.R;
import com.sashashtmv.bookreader.model.BookModel;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.BookViewHolder> {
    List<BookModel> mItemList;
    protected static ItemListener mListener;
    private Context context;
    BookViewHolder mHolder;
    BookModel book;

    public ListAdapter(Context context, List<BookModel> itemList, ItemListener listener) {

        this.mItemList = itemList;
        this.mListener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mHolder = new BookViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_book, null));
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        book = (BookModel) mItemList.get(position);
        Log.i(TAG, "onBindViewHolder: position - " + position + "items - " + book.getImageName());
        if (book != null) {
            holder.setData(book, position, context);

        }

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: count - " + mItemList.size());
        return mItemList.size();
    }

    public interface ItemListener {
        void onItemClick(BookModel item);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView baseImage;
        protected TextView bookName;
        protected BookModel item;

        public BookViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            baseImage = itemView.findViewById(R.id.iv_base_image);

            bookName = itemView.findViewById(R.id.tv_title_book);
        }

        public void setData(BookModel item, int position, Context context) {
            this.item = item;
            this.baseImage.setImageBitmap(item.getImageName());


            this.bookName.setText(item.getNameBook());
            this.bookName.setTextSize(22);
            this.bookName.setTextColor(Color.parseColor("#6D9DE2"));
            Log.i(TAG, "onPostExecute: result - " + position + "items - " + item.toString());


        }


        @Override
        public void onClick(View view) {
            if (item != null && ListAdapter.mListener != null) {
                ListAdapter.mListener.onItemClick(item);
            }
        }
    }


}
