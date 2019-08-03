package com.sashashtmv.game4in1.fragments;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sashashtmv.game4in1.MainActivity;
import com.sashashtmv.game4in1.R;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {
    public MainActivity mMainActivity;
    Toolbar toolbar;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mMainActivity = (MainActivity) getActivity();
        }
    }


    public SplashFragment() {
        // Required empty public constructor
    }
    public static SplashFragment newInstance() {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        SplashTask splashTask = new SplashTask();
        splashTask.execute();
        // Inflate the layout for this fragment
        return view;
    }

    class SplashTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //реализуем задержку отображения сплешскрина
                TimeUnit.SECONDS.sleep(2);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // обращаемся к активности и привязываемся к бекстеку
            if(getActivity() != null) {
                getActivity().getFragmentManager().popBackStack();
            }
            return null;
        }
    }

}
