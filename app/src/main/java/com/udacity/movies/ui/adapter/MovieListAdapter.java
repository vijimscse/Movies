package com.udacity.movies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movies.R;
import com.udacity.movies.dto.Movie;
import com.udacity.movies.utils.DateFormatter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.udacity.movies.utils.Config.IMAGE_BASE_URL;


/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private final Context mContext;
    private final List<Movie> mMovieList;
    private final MovieItemClickListener mMovieItemClickListener;

    public interface MovieItemClickListener {
        void onItemClick(int position);
        void onFavSelect(int position);
    }

    public MovieListAdapter(Context context, List<Movie> movieList, MovieItemClickListener movieItemClickListener) {
        mContext = context;
        mMovieList = movieList;
        mMovieItemClickListener = movieItemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        Movie movie = mMovieList.get(position);

        movieViewHolder.mTitle.setText(movie.getTitle());
        movieViewHolder.mReleaseDate.setText(DateFormatter.getDateFormat(movie.getReleaseDate()));
        movieViewHolder.mUserRating.setText(String.valueOf(movie.getVoteAverage()));
        Picasso.with(mContext)
                .load(IMAGE_BASE_URL + movie.getBackdropPath())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.image_error)
                .into(movieViewHolder.mPosterPath);

        if (movie.isFavorite()) {
            movieViewHolder.mFavorite.setImageResource(R.drawable.favorite_selected);
        } else {
            movieViewHolder.mFavorite.setImageResource(R.drawable.favorite_unselected);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (mMovieList != null) {
            count = mMovieList.size();
        }

        return count;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_title)
        TextView mTitle;

        @BindView(R.id.poster_path)
        ImageView mPosterPath;

        @BindView(R.id.userRating)
        TextView mUserRating;

        @BindView(R.id.releaseDate)
        TextView mReleaseDate;

        @BindView(R.id.favorite)
        ImageView mFavorite;

        public MovieViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.movie_row)
        public void onMovieClick(View view) {
            mMovieItemClickListener.onItemClick(getAdapterPosition());
        }

        @OnClick(R.id.favorite)
        public void onFavClick(View view) {
            mMovieItemClickListener.onFavSelect(getAdapterPosition());
        }
    }
}
