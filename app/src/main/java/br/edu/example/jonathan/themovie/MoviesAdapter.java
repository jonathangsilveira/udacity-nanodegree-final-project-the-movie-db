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

    private OnItemClickListener mOnItemClickListener;

    MoviesAdapter(Context context, List<TmdbMovie> movies, List<Bitmap> bitmaps) {
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
        final TmdbMovie movie = mMovies.get(position);
        holder.mImageViewCover.setImageBitmap(mBitmaps.get(position));
        if (mOnItemClickListener != null) {
            holder.mImageViewCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(movie);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewCover;

        private TmdbMovie mMovie;

        MovieViewHolder(View itemView) {
            super(itemView);
            mImageViewCover = itemView.findViewById(R.id.movie_item_thumbnail);
        }

    }

    interface OnItemClickListener {

        void onItemClick(TmdbMovie movie);

    }

}
