package com.sashashtmv.fastcredit.credits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sashashtmv.fastcredit.R;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.UUID;

public class CreditListFragment extends Fragment {
    private RecyclerView mCreditsRecyclerView;
    private BankAdapter mAdapter;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private UUID mUUID;


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
        View view = inflater.inflate(R.layout.fragment_item_list_credit, container, false);

        mCreditsRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mCreditsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;

    }

    private void updateUI() {
        BankLab bankLab = BankLab.get();
        List<Bank> crimes = bankLab.getBanks();
        mAdapter = new BankAdapter(crimes);
        mCreditsRecyclerView.setAdapter(mAdapter);
    }

    private class BankHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Bank mBank;
        private ImageView mIcon;
        private TextView mApply;
        private TextView mTitle;
        private TextView mDescription;


        public BankHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.twTitle);
            mDescription = itemView.findViewById(R.id.twDescription);
            mIcon = itemView.findViewById(R.id.icon_bank);
            itemView.setOnClickListener(this);
        }

        public void bindBank(Bank bank) {
            mBank = bank;
            mTitle.setText(mBank.getTitle());
            mDescription.setText(mBank.getDescription());
            mIcon.setImageResource(R.drawable.moneyman);
            Picasso.with(getContext()).load("http://drawall.ru/" + bank.getAdressPicture()).into(mIcon);
            //Picasso.with(getContext()).load("http://tut13.ru/" + bank.getAdressPicture()).into(mIcon);
            //mIcon.setImageBitmap(mBank.getIcon());
            //mIcon.setImageURI(mBank.getAdressPicture());
            //new DownloadImageTask(mIcon).execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
            //Toast.makeText(getContext(),mBank.getAdressPicture().toString(), Toast.LENGTH_LONG).show();

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Uri address = BankLab.get().getBanks().get(position).getAdressBank();
            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openlinkIntent);
            //TODO
//            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());//запуск активности из фрагмента
//            startActivity(intent);
        }

    }

    private class BankAdapter extends RecyclerView.Adapter<BankHolder> {
        private List<Bank> mBanks;


        public BankAdapter(List<Bank> banks) {

            mBanks = banks;
        }

        @NonNull
        @Override
        public BankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.fragment_item_credit, parent, false);
            return new BankHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BankHolder holder, int position) {
            Bank bank = mBanks.get(position);
            holder.bindBank(bank);
        }

        @Override
        public int getItemCount() {
            return mBanks.size();
        }
    }

//
//
//    @Override
//    public void onSaveInstanceState(final Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("list", (Serializable) mAdapter);
//        outState.putSerializable("recicl", (Serializable)mCreditsRecyclerView);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            //probably orientation change
//            mAdapter = (BankAdapter) savedInstanceState.getSerializable("list");
//            mCreditsRecyclerView = (RecyclerView)savedInstanceState.getSerializable("recicl");
//        } else {
//            if (mAdapter != null && mCreditsRecyclerView!=null) {
//                //returning from backstack, data is fine, do nothing
//            } else {
//                //newly created, compute data
////                mAdapter = new BankAdapter();
//            }
//        }
//    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Ошибка передачи изображ", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(DummyItem item);
//    }
}
