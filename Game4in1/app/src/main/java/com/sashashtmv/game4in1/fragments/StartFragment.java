package com.sashashtmv.game4in1.fragments;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sashashtmv.game4in1.MainActivity;
import com.sashashtmv.game4in1.R;
import com.sashashtmv.game4in1.adapter.AdapterForLevels;

import com.sashashtmv.game4in1.database.DBHelper;
import com.sashashtmv.game4in1.model.ModelLevel;
import com.sashashtmv.game4in1.model.PreferenceHelper;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment implements AdapterForLevels.ItemListener {

    Toolbar toolbar;
    FragmentManager mFragmentManager;

    protected RecyclerView mRecyclerView;
    protected GridLayoutManager mLayoutManager;
    private GridLayoutManager lLayout;

    private List<ModelLevel> items;

    public MainActivity mMainActivity;
    private PreferenceHelper mPreferenceHelper;
    public DBHelper mDBHelper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mMainActivity = (MainActivity) getActivity();
        }
    }

    public StartFragment() {
        // Required empty public constructor
    }

    public interface callback {
        void onCreatLevel(List<ModelLevel> items, ModelLevel item, DBHelper DBHelper);
    }

    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();

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
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        PreferenceHelper.getInstance().init(getActivity());
        mPreferenceHelper = PreferenceHelper.getInstance();
        mDBHelper = new DBHelper(getActivity());

        items = getItems();
        lLayout = new GridLayoutManager(getActivity(), 4);

        RecyclerView rView = (RecyclerView) view.findViewById(R.id.rvLevels);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        AdapterForLevels rcAdapter = new AdapterForLevels(getActivity(), items, this);
        rView.setAdapter(rcAdapter);
        return view;
    }

    private List<ModelLevel> getItems() {
        String[] mas = {"хлеб", "диван", "дерево", "ручка", "колесо", "стол", "ветер", "топливо", "мясо", "провод", "бутылка", "песок", "подушка", "бабочка",
                "знание", "шланг", "игра", "электричество", "остров", "мысли", "рассказ", "стратегия", "велосипед", "испытание", "время", "фрукты",
                "уют", "двигатель", "полет", "урожай", "прибор", "рука", "макияж", "гнев", "симметрия", "писатель", "сестра", "складка", "религия",
                "горшок", "дно", "жидкость", "половина", "реклама", "сахар", "белый", "офис", "ломтик", "номер", "баланс"};
        List<ModelLevel> modelLevels = mDBHelper.query().getLevels();
        //mDBHelper.getWritableDatabase().delete("levels_table", null, null);
        if(modelLevels.size() < mas.length) {
        for (int i = modelLevels.size(); i < mas.length; i++) {
            String image1 = "";
            String image2 = "";
            String image3 = "";
            String image4 = "";
            for (int j = 1; j < 5; j++) {
                String name = "image" + (i + 1) + "_" + j;
                try {
                    if (j == 1) {
                        image1 = name;
                    }
                    if (j == 2) {
                        image2 = name;
                    }
                    if (j == 3) {
                        image3 = name;
                    }
                    if (j == 4) {
                        image4 = name;
                    }
                } catch (Exception e) {
                }
            }
            if (i == 0) {
                mDBHelper.saveLevel(new ModelLevel(mas[i], image1, image2, image3, image4, ModelLevel.STATUS_AVALABLE, new Date().getTime()));
            } else {
                mDBHelper.saveLevel(new ModelLevel(mas[i], image1, image2, image3, image4, ModelLevel.STATUS_NOT_AVALABLE, new Date().getTime()));
            }
        }
        }
        //mDBHelper.update().status(modelLevels.get(0).getTimeStamp(), ModelLevel.STATUS_AVALABLE);

        return mDBHelper.query().getLevels();
    }

    @Override
    public void onItemClick(ModelLevel item) {
        mMainActivity.onCreatLevel(items, item, mDBHelper);
//        Toast.makeText(getActivity(), item.getWord() + " is clicked", Toast.LENGTH_SHORT).show();

    }
}
