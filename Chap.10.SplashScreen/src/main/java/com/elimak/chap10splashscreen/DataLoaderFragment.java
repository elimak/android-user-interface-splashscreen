package com.elimak.chap10splashscreen;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;

/**
 * Created by elimak on 10/7/13.
 */
public class DataLoaderFragment extends Fragment {

    public interface ProgressListener {
        public void onCompletion(Double result);
        public void onProgressUpdate(int value);
    }

    private ProgressListener mProgressListener;
    private Double mResult = Double.NaN;
    private LoadingTask mTask;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(true); // uses a fragment as a background worker, without a UI
    }

    public void setProgressListener(ProgressListener listener) {
        mProgressListener = listener;
    }

    public void removeProgressListener() {
        mProgressListener = null;
    }

    public Double getResult(){
        return mResult;
    }

    public boolean hasResult() {
        return !Double.isNaN(mResult);
    }

    public void startLoading() {
        mTask = new LoadingTask();
        mTask.execute();
    }

    // AsyncTask allows to perform background operations and publish results
    // on the UI thread without having to manipulate threads and/or handlers.
    private class LoadingTask extends AsyncTask<Void, Integer, Double> {

        @Override
        protected Double doInBackground(Void... params) {
            double result = 0;
            for(int i = 0; i<100; i++){
                try{
                    result += Math.sqrt(i);
                    Thread.sleep(50);
                    this.publishProgress(i);
                }
                catch (InterruptedException e){
                    return null;
                }
            }
            return Double.valueOf(result);
        }

        @Override
        protected void onPostExecute(Double result) {
            mResult = result;
            mTask = null;
            if(mProgressListener != null ){
                mProgressListener.onCompletion(mResult);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(mProgressListener != null){
                mProgressListener.onProgressUpdate(values[0]);
            }
        }
    }
}
