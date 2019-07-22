package testcom.com.net2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import testcom.com.net2.R;
import testcom.com.net2.adapter.RecyclerAdapter;
import testcom.com.net2.model.Mod;
import testcom.com.net2.model.ServerResponse;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RecyclerFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ServerResponse serverResponse;
    private ArrayList<Mod> data;

    public RecyclerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        Bundle extras = getArguments();
        if (extras != null) {
            serverResponse = extras.getParcelable(ServerResponse.class.getName());
            data = serverResponse.banks;
        } else data = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recycler);
        recyclerAdapter = new RecyclerAdapter(new WeakReference<>(getContext()), data);
        recyclerView.setAdapter(recyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onDestroy() {
        if (getArguments() != null)
            getArguments().clear();
        super.onDestroy();
    }
}
