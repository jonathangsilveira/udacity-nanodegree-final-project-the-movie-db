package br.edu.example.jonathan.themovie.util;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.edu.example.jonathan.themovie.TmdbMovie;

public class QueryMoviesAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private List<TmdbMovie> mMovies = new ArrayList<>();

    private Callback mCallback;

    private String mErrorMessage;

    public void registerCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if (hasCallbackRegistered()) {
            try {
                mMovies = mCallback.onExecuteTask();
                return true;
            } catch (Exception e) {
                mErrorMessage = e.getMessage();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (hasCallbackRegistered()) {
            mCallback.onStartTask();
        }
    }

    @Override
    protected void onPostExecute(Boolean isSuccessfully) {
        super.onPostExecute(isSuccessfully);
        if (hasCallbackRegistered()) {
            if (isSuccessfully) {
                mCallback.onFinishTaskSuccessfully(mMovies);
            } else {
                mCallback.onFinishTaskWithError(mErrorMessage);
            }
        }
    }

    private boolean hasCallbackRegistered() {
        return mCallback != null;
    }

    public interface Callback {

        void onStartTask();

        List<TmdbMovie> onExecuteTask() throws Exception;

        void onFinishTaskSuccessfully(List<TmdbMovie> movies);

        void onFinishTaskWithError(String message);

    }

}
