package com.sashashtmv.fastcredit.cards;

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

public class CardListFragment extends Fragment {
    private RecyclerView mLoansRecyclerView;
    private CardAdapter mAdapter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list_card, container, false);

        mLoansRecyclerView = (RecyclerView) view.findViewById(R.id.list_cards);
        mLoansRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private void updateUI() {
        CardLab cardLab = CardLab.get();
        List<Card> crimes = cardLab.getCards();
        mAdapter = new CardAdapter(crimes);
        mLoansRecyclerView.setAdapter(mAdapter);
    }

    private class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Card mCard;
        private ImageView mIcon;
        private TextView mApply;
        private TextView mTitle;
        private TextView mDescription;

        public CardHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.twTitle);
            mDescription = itemView.findViewById(R.id.twDescription);
            mIcon = itemView.findViewById(R.id.icon_card);

            itemView.setOnClickListener(this);
        }

        public void bindCard(Card card) {
            mCard = card;
            mDescription.setText(mCard.getDescription());
            mTitle.setText(mCard.getTitle());
            mIcon.setImageResource(R.drawable.moneyman);
            Picasso.with(getContext()).load("http://drawall.ru/" + card.getAdressPicture()).into(mIcon);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Uri address = CardLab.get().getCards().get(position).getAdressBank();
            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openlinkIntent);
        }
    }

    private class CardAdapter extends RecyclerView.Adapter<CardHolder> {
        private List<Card> mCards;

        public CardAdapter(List<Card> cards) {
            mCards = cards;
        }

        @NonNull
        @Override
        public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_item_card, parent, false);
            return new CardHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CardHolder holder, int position) {
            Card card = mCards.get(position);
            holder.bindCard(card);
        }

        @Override
        public int getItemCount() {
            return mCards.size();
        }
    }
}
