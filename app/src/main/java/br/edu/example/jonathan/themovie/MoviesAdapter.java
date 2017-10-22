package br.edu.example.jonathan.themovie;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List mMovies;

    private Context mContext;

    public MoviesAdapter(Context context, List movies) {
        if (context == null || movies == null) {
            throw new IllegalArgumentException();
        }
        mContext = context;
        mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewCover;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mImageViewCover = itemView.findViewById(R.id.movie_item_thumbnail);
        }

    }

}
