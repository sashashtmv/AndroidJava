package com.sashashtmv.fastcredit.other;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sashashtmv.fastcredit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OtherListFragment extends Fragment {
    private RecyclerView mOthersRecyclerView;
    private OtherAdapter mAdapter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


//    public CreditListFragment() {
//    }

//    public static CreditListFragment newInstance() {
//        CreditListFragment fragment = new CreditListFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list_other, container, false);

        mOthersRecyclerView = (RecyclerView) view.findViewById(R.id.list_others);
        mOthersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private void updateUI() {
        OtherLab otherLab = OtherLab.get();
        List<Other> crimes = otherLab.getOthers();
        mAdapter = new OtherAdapter(crimes);
        mOthersRecyclerView.setAdapter(mAdapter);
    }

    private class OtherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Other mOther;
        private ImageView mIcon;
        private TextView mApply;
        private TextView mTitle;
        private TextView mDescription;

        public OtherHolder(View itemView) {
            super(itemView);
//            mIcon = itemView.findViewById(R.id.icon_bank);
//            mSum = itemView.findViewById(R.id.twSum);
//            mRate = itemView.findViewById(R.id.twRate);
//            mTerm = itemView.findViewById(R.id.twTerm);
            mTitle = itemView.findViewById(R.id.twTitle);
            mDescription = itemView.findViewById(R.id.twDescription);
            mIcon = itemView.findViewById(R.id.icon_other);

            itemView.setOnClickListener(this);
        }

        public void bindOther(Other other) {
            mOther = other;
            mDescription.setText(mOther.getDescription());
            mTitle.setText(mOther.getTitle());
            Picasso.with(getContext()).load("http://drawall.ru/" + other.getAdressPicture()).into(mIcon);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Uri address = OtherLab.get().getOthers().get(position).getAdressOther();
            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openlinkIntent);
            //TODO
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());//запуск активности из фрагмента
//            startActivity(intent);
        }

    }

    private class OtherAdapter extends RecyclerView.Adapter<OtherHolder> {
        private List<Other> mOthers;

        public OtherAdapter(List<Other> others) {
            mOthers = others;
        }

        @NonNull
        @Override
        public OtherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_item_other, parent, false);
            return new OtherHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull OtherHolder holder, int position) {
            Other other = mOthers.get(position);
            holder.bindOther(other);
        }

        @Override
        public int getItemCount() {
            return mOthers.size();
        }
    }


}
