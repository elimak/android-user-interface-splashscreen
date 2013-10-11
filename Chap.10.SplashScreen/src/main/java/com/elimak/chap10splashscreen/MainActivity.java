package com.elimak.chap10splashscreen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements DataLoaderFragment.ProgressListener{

    private static final String TAG_DATA_LOADER = "dataLoader";
    private static final String TAG_SPLASH_SCREEN = "splashScreen";

    private DataLoaderFragment mDataLoaderFragment;
    private SplashScreenFragment mSplashScreenFragment;

    @Override
    public void onCompletion(Double result) {
        TextView tv = new TextView(this);
        tv.setText(String.valueOf(result));
        setContentView(tv);
        mDataLoaderFragment = null;
    }

    @Override
    public void onProgressUpdate(int value) {
        mSplashScreenFragment.setProgress(value);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fm = getFragmentManager();
        mDataLoaderFragment = (DataLoaderFragment) fm.findFragmentByTag(TAG_DATA_LOADER);
        if(mDataLoaderFragment == null ){
            mDataLoaderFragment = new DataLoaderFragment();
            mDataLoaderFragment.setProgressListener(this);
            mDataLoaderFragment.startLoading();
            fm.beginTransaction().add(mDataLoaderFragment, TAG_DATA_LOADER).commit();
        }
        else{
            if(checkCompletionStatus()){
                return;
            }
        }

        mSplashScreenFragment = (SplashScreenFragment) fm.findFragmentByTag(TAG_SPLASH_SCREEN);
        if(mSplashScreenFragment == null){
            mSplashScreenFragment = new SplashScreenFragment();
            fm.beginTransaction().add(android.R.id.content, mSplashScreenFragment, TAG_SPLASH_SCREEN).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mDataLoaderFragment != null){
            checkCompletionStatus();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mDataLoaderFragment != null){
            mDataLoaderFragment.removeProgressListener();
        }
    }

    private boolean checkCompletionStatus() {
        if(mDataLoaderFragment.hasResult()){
            onCompletion(mDataLoaderFragment.getResult());
            FragmentManager fm = getFragmentManager();
            mSplashScreenFragment = (SplashScreenFragment) fm.findFragmentByTag(TAG_SPLASH_SCREEN);
            if(mSplashScreenFragment != null){
                fm.beginTransaction().remove(mSplashScreenFragment).commit();
            }
            return true;
        }
        mDataLoaderFragment.setProgressListener(this);
        return false;
    }
}
