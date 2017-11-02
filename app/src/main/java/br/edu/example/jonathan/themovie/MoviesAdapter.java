package br.edu.example.jonathan.themovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<TmdbMovie> mMovies;

    private List<Bitmap> mBitmaps;

    private Context mContext;

    public MoviesAdapter(Context context, List<TmdbMovie> movies, List<Bitmap> bitmaps) {
        if (context == null || movies == null || bitmaps == null) {
            throw new IllegalArgumentException();
        }
        mContext = context;
        mMovies = movies;
        mBitmaps = bitmaps;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.mImageViewCover.setImageBitmap(mBitmaps.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewCover;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mImageViewCover = itemView.findViewById(R.id.movie_item_thumbnail);
        }

    }

}
