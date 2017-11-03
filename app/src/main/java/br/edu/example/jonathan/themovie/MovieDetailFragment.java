package br.edu.example.jonathan.themovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {

    private static final String MOVIE_PARAMETER = "MOVIE";
    private TmdbMovie mMovie;
    private TextView mTextViewReleaseDate;
    private TextView mTextViewVoteAverage;
    private TextView mTextViewOverview;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(TmdbMovie movie, boolean retainInstance) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_PARAMETER, movie);
        fragment.setRetainInstance(retainInstance);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mMovie = args.getParcelable(MOVIE_PARAMETER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        Toolbar toolbar = view.findViewById(R.id.movie_details_toolbar);
        toolbar.setTitle(mMovie.getTitle());
        mTextViewReleaseDate = view.findViewById(R.id.movie_details_release_date);
        mTextViewVoteAverage = view.findViewById(R.id.movie_details_vote_average);
        mTextViewOverview = view.findViewById(R.id.movie_details_overview);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setViewValues();
    }

    void setViewValues() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMinimumIntegerDigits(1);
        mTextViewReleaseDate.setText(dateFormat.format(mMovie.getReleaseDate()));
        mTextViewReleaseDate.setText(decimalFormat.format(mMovie.getVoteAverage()));
        mTextViewReleaseDate.setText(mMovie.getOverview());
    }

}
