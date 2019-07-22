package com.sashashtmv.game4in1.fragments;


import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
//import android.support.annotation.RequiresApi;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.sashashtmv.game4in1.MainActivity;
import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.adapter.AdapterForLevels;
import com.sashashtmv.game4in1.adapter.GridSpacingItemDecoration;
import com.sashashtmv.game4in1.model.Item;
import com.sashashtmv.game4in1.model.ModelLevel;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment implements AdapterForLevels.ItemListener {

    Toolbar toolbar;
    FragmentManager mFragmentManager;

    protected RecyclerView mRecyclerView;
    protected GridLayoutManager mLayoutManager;
    protected AdapterForLevels mAdapter;
    private ArrayList<Item> items;

    public MainActivity mMainActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            mMainActivity = (MainActivity)getActivity();
        }
//        addTaskFromDB();
    }

    public StartFragment() {
        // Required empty public constructor
    }

    public interface callback{
        void onCreatLevel(ArrayList<Item> items, ModelLevel item);
    }
    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_PARAM1, fragment.total);
//        args.putInt(ARG_PARAM2, fragment.pompa);
//        args.putInt(ARG_PARAM3, fragment.tara);
//        args.putInt(ARG_PARAM4, fragment.count1);
//        args.putInt(ARG_PARAM5, fragment.count2);
//        args.putInt(ARG_PARAM6, fragment.count3);
        fragment.setArguments(args);

        return fragment;
    }


    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar_fragment); //находишь тулбар
        //toolbar.setNavigationIcon(R.drawable.ic_launcher_background); //помещаешь иконку слева
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        //mFragmentManager = activity.getFragmentManager();

        int spanCount = 4; // 3 columns
        int spacing = 40; // 50px
        boolean includeEdge = true;
        mLayoutManager = new GridLayoutManager(getActivity(), spanCount, GridLayoutManager.VERTICAL, false);
        mRecyclerView = view.findViewById(R.id.rvLevels);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mRecyclerView.setLayoutManager(mLayoutManager);

        items = getItems();
        mAdapter = new AdapterForLevels(this, this);
        mAdapter.addItems(items);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    private ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        String[] mas = {"хлеб", "диван", "Дерево", "ручка", "колесо", "стол", "ветер", "топливо", "мясо", "провод", "бутылка", "песок", "подушка", "бабочка",
                "знание", "шланг"};
        for(int i = 0; i < mas.length; i++) {
            Bitmap image1 = null;
            Bitmap image2 = null;
            Bitmap image3 = null;
            Bitmap image4 = null;
            for(int j = 1; j < 5; j++) {
                String name = "image" + (i+1) + "_" + j;
                Log.i(TAG, "getItems:  - " +  getActivity().getPackageName());
                try {
                    if (j == 1) {
                        image1 = BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(name, "drawable", getActivity().getPackageName()));
                       // Bitmap.createScaledBitmap(image1, 100, 100, false);
                    }
                    if (j == 2) {
                        image2 = BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(name, "drawable", getActivity().getPackageName()));
                        //Bitmap.createScaledBitmap(image2, 100, 100, false);
                    }
                    if (j == 3) {
                        image3 = BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(name, "drawable", getActivity().getPackageName()));
                        //Bitmap.createScaledBitmap(image3, 100, 100, false);
                    }
                    if (j == 4) {
                        image4 = BitmapFactory.decodeResource(getActivity().getResources(), getActivity().getResources().getIdentifier(name, "drawable", getActivity().getPackageName()));
                       // Bitmap.createScaledBitmap(image4, 100, 100, false);
                    }
                }catch (Exception e){

                }
            }
                items.add(new ModelLevel(mas[i], image1, image2, image3, image4));
        }
        return items;
    }

    @Override
    public void onItemClick(ModelLevel item) {
        mMainActivity.onCreatLevel(items, item);
        Toast.makeText(getActivity(), item.getWord() + " is clicked", Toast.LENGTH_SHORT).show();

    }


}
