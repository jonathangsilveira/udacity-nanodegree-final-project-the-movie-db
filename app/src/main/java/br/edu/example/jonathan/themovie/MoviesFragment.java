package br.edu.example.jonathan.themovie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {

    private static final String RETAIN_INSTANCE_PARAM = "RETAIN_INSTANCE";

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            loadCoverImages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCoverImages() throws IOException {
        Picasso.with(getActivity()).load("http://images6.fanpop.com/image/photos/39900000/-Suicide-Squad-Promotional-Poster-suicide-squad-39974600-333-500.jpg").get();
        Picasso.with(getActivity()).load("https://images-na.ssl-images-amazon.com/images/M/MV5BMTkyMDMwNjQxMl5BMl5BanBnXkFtZTYwNTc3MTQ5._V1_.jpg").get();
        Picasso.with(getActivity()).load("https://images-na.ssl-images-amazon.com/images/M/MV5BODNiZmY2MWUtMjFhMy00ZmM2LTg2MjYtNWY1OTY5NGU2MjdjL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SY1000_CR0,0,689,1000_AL_.jpg").get();
    }

}
