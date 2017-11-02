package br.edu.example.jonathan.themovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String MOVIES_FRAGMENT_TAG = "MOVIES_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment moviesFragment = getSupportFragmentManager().findFragmentByTag(MOVIES_FRAGMENT_TAG);
        if (moviesFragment == null) {
            moviesFragment = MoviesFragment.newInstance(true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main_content, moviesFragment, MOVIES_FRAGMENT_TAG)
                    .commit();
        }
    }

}
