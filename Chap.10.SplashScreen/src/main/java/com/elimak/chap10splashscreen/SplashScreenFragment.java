package com.elimak.chap10splashscreen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by elimak on 10/7/13.
 */
public class SplashScreenFragment extends Fragment{

    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.splash_screen, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        return view;
    }

    public void setProgress(int progress){
        mProgressBar.setProgress(progress);
    }
}
