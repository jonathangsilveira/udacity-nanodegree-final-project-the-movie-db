package br.edu.example.jonathan.themovie;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.edu.example.jonathan.themovie.util.QueryMoviesAsyncTask;
import br.edu.example.jonathan.themovie.util.TMDBUtil;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment implements QueryMoviesAsyncTask.Callback {

    private static final String RETAIN_INSTANCE_PARAM = "RETAIN_INSTANCE";

    private List<TmdbMovie> mMovies = new ArrayList<>();

    private RecyclerView mMoviesList;

    private AlertDialog mDialog;

    private QueryMoviesAsyncTask mQueryMoviesTask;

    private List<Bitmap> mPosterImages = new ArrayList<>();

    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(boolean retainInstance) {
        MoviesFragment fragment = new MoviesFragment();
        fragment.setRetainInstance(retainInstance);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(getArguments().getBoolean(RETAIN_INSTANCE_PARAM, false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        mMoviesList = view.findViewById(R.id.fragment_movies_thumbnails);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2,
                GridLayoutManager.VERTICAL, false);
        mMoviesList.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mQueryMoviesTask = new QueryMoviesAsyncTask();
        mQueryMoviesTask.registerCallback(this);
        mQueryMoviesTask.execute();
    }

    @Override
    public void onStartTask() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.search);
        dialogBuilder.setMessage(R.string.searching_for_the_most_popular_movies);
        dialogBuilder.setCancelable(true);
        dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mQueryMoviesTask.cancel(true);
                dialog.dismiss();
            }
        });
        mDialog = dialogBuilder.show();
    }

    @Override
    public List<TmdbMovie> onExecuteTask() throws Exception {
        String json = TMDBUtil.queryTheMostPopularMovies(getActivity());
        List<TmdbMovie> movies = TMDBUtil.convertJsonToMovies(json, 20);
        mPosterImages.clear();
        for (TmdbMovie movie : movies) {
            Bitmap bitmap = TMDBUtil.queryPosterImage(getActivity(), movie.getPosterPath());
            mPosterImages.add(bitmap);
        }
        return movies;
    }

    @Override
    public void onFinishTaskSuccessfully(List<TmdbMovie> movies) {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mMovies.clear();
        mMovies.addAll(movies);
        MoviesAdapter adapter = new MoviesAdapter(getActivity(), mMovies, mPosterImages);
        mMoviesList.setAdapter(adapter);
    }

    @Override
    public void onFinishTaskWithError(String message) {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.error);
        dialogBuilder.setIcon(R.drawable.ic_error_black);
        dialogBuilder.setMessage(getString(R.string.fail_to_search_for_the_most_popular_movies, message));
    }

}
