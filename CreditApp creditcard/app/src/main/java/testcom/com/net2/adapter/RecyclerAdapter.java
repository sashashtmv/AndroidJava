package testcom.com.net2.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import testcom.com.net2.R;
import testcom.com.net2.model.Mod;
import testcom.com.net2.model.RecyclerClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private WeakReference<Context> contextWeakReference;
    private ArrayList<Mod> data;

    public RecyclerAdapter(WeakReference<Context> contextWeakReference, ArrayList<Mod> data) {
        this.contextWeakReference = contextWeakReference;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view, contextWeakReference);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position, data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        else return 0;
    }

    public void updateData(ArrayList<Mod> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
//        @BindView(R.id.icon)
//        SelectableRoundedImageView icon;
//        @BindView(R.id.viewButton)
//        Button button;
        @BindView(R.id.buttonTXT)
        TextView button;
        @BindView(R.id.logo)
        ImageView logo;
        @BindView(R.id.clickableButton)
        Button button1;

        private RecyclerClickListener recyclerClickListener;
        private WeakReference<Context> contextWeakReference;

        public ViewHolder(View itemView, WeakReference<Context> contextWeakReference) {
            super(itemView);
            this.contextWeakReference = contextWeakReference;
            recyclerClickListener = (RecyclerClickListener) contextWeakReference.get();

            ButterKnife.bind(this, itemView);
        }

        public void bind(int index, final Mod item) {
            title.setText(item.title);
            description.setText(item.description);

            Picasso.with(contextWeakReference.get()).load("http://tut13.ru/" + item.iconUrl).into(logo);

            switch (item.type) {
                case 1:
                    button.setText(R.string.button_text1);
                    break;
            }

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contextWeakReference.get().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.url)));
                }
            };

            button.setOnClickListener(onClickListener);
            button1.setOnClickListener(onClickListener);
        }
    }
}
