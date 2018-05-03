package com.udacity.movies.ui.dashboard.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.movies.MoviesApplication;
import com.udacity.movies.R;
import com.udacity.movies.dagger.module.DashboardModule;
import com.udacity.movies.db.MoviesContract;
import com.udacity.movies.dto.Movie;
import com.udacity.movies.dto.MovieList;
import com.udacity.movies.ui.adapter.MovieListAdapter;
import com.udacity.movies.ui.base.BaseFragment;
import com.udacity.movies.ui.customviews.RecyclerViewEmptySupport;
import com.udacity.movies.ui.dashboard.DashboardView;
import com.udacity.movies.ui.dashboard.IMovieListFragmentListener;
import com.udacity.movies.ui.dashboard.MovieListPresenter;
import com.udacity.movies.utils.IBundleKeys;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public abstract class MovieListBaseFragment extends BaseFragment implements DashboardView, MovieListAdapter.MovieItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerViewEmptySupport mRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.list_empty)
    TextView mListEmptyTextView;

    @Inject
    MovieListPresenter mMovieListPresenter;

    protected ArrayList<Movie> mMovieList = new ArrayList<>();
    protected MovieListAdapter mMovieListAdapter;
    protected IMovieListFragmentListener mMovieListFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof IMovieListFragmentListener)) {
            throw new IllegalStateException(((FragmentActivity) context).getClass().getSimpleName() + "must implement" + IMovieListFragmentListener.class.getSimpleName());
        }
        mMovieListFragmentListener = (IMovieListFragmentListener) context;
    }

    private void insertIntoDB(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MovieEntry.MOVIE_SHORT_TITLE, movie.getTitle());
        contentValues.put(MoviesContract.MovieEntry.MOVIE_ORIGINAL_TITLE, movie.getOriginalTitle());
        contentValues.put(MoviesContract.MovieEntry.OVERVIEW, movie.getOverview());
        contentValues.put(MoviesContract.MovieEntry.MOVIE_ID, movie.getId());
        contentValues.put(MoviesContract.MovieEntry.RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MoviesContract.MovieEntry.USER_RATING, movie.getVoteAverage());
        contentValues.put(MoviesContract.MovieEntry.POSTER_PATH, movie.getPosterPath());
        contentValues.put(MoviesContract.MovieEntry.BACKDROP_PATH, movie.getBackdropPath());

        getActivity().getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, contentValues);
    }

    private int deleteFromDB(int movieID) {
        return getActivity().getContentResolver().delete(MoviesContract.MovieEntry.buildMoviesUri(movieID),
                null, null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MoviesApplication) getActivity().getApplicationContext()).getAppComponent().plus(
                new DashboardModule(this, this)).inject(this);

        if (savedInstanceState != null) {
            ArrayList<Movie> movieList = savedInstanceState.getParcelableArrayList(IBundleKeys.MOVIE_LIST);
            //mSelectedMoviePosition = savedInstanceState.getInt(IBundleKeys.SELECTED_MOVIE_POSITION);
            mMovieList.clear();
            if (movieList != null && !movieList.isEmpty()) {
                mMovieList.addAll(movieList);
            }
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setEmptyView(mListEmptyTextView);
        mMovieListAdapter = new MovieListAdapter(getActivity(), mMovieList, this);
        mRecyclerView.setAdapter(mMovieListAdapter);
        updateMovieListFavMovies();
        setSelectedPos();
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(IBundleKeys.MOVIE_LIST, mMovieList);
   //     outState.putInt(IBundleKeys.SELECTED_MOVIE_POSITION, mSelectedMoviePosition);
        Log.d("TAG", "Fragment onsave");
    }

    @Override
    public void onFavSelect(int position) {
        Movie movie = mMovieList.get(position);

        if (!movie.isFavorite()) {
            // Add the item to DB
            movie.setmIsFavorite(true);

            insertIntoDB(movie);
            mMovieListAdapter.notifyDataSetChanged();
        } else {
            // delete the item from DB and update the current list if already in favorites view
            int count = deleteFromDB(movie.getId());

            if (count > 0) {
                if (this instanceof FavoriteFragment) {
                    mMovieList.remove(position);
                } else {
                    movie.setmIsFavorite(false);
                }
                mMovieListAdapter.notifyDataSetChanged();
            }
        }
    }

    protected void fetchMovieList(int sortType) {
        if (mMovieList == null || mMovieList.isEmpty()) {
            mMovieListPresenter.requestMovies(getActivity(), sortType);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setMovieList(MovieList movieList) {
        if (getActivity() != null && movieList != null &&
                movieList.getMovies() != null && !movieList.getMovies().isEmpty()) {
            mMovieList.clear();
            mMovieList.addAll(movieList.getMovies());
            updateMovieListFavMovies();
            setSelectedPos();
        }
    }

    protected List<Movie> fetchFavMoviesFromDB() {
        return getCachedFavMovieList();
    }

    private List<Movie> getCachedFavMovieList() {
        List<Movie> movieList = new ArrayList<>();
        if (getActivity() != null) {
            Cursor cursor = getActivity().getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie();

                    movie.setTitle(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.MOVIE_SHORT_TITLE)));
                    movie.setId(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.MOVIE_ID)));
                    movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.USER_RATING)));
                    movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.MOVIE_ORIGINAL_TITLE)));
                    movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.RELEASE_DATE)));
                    movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.POSTER_PATH)));
                    movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.BACKDROP_PATH)));
                    movie.setOverview(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.OVERVIEW)));
                    movie.setmIsFavorite(true);

                    movieList.add(movie);
                } while (cursor.moveToNext());

                cursor.close();
            }
        }

        return movieList;
    }

    protected void updateMovieListFavMovies() {
        if (getActivity() != null) {
            for (Movie movie : mMovieList) {
                movie.setmIsFavorite(false);
            }
            List<Movie> movieList = getCachedFavMovieList();

            if (movieList != null && !movieList.isEmpty()) {
                for (Movie cachedMovie : movieList) {
                    for (Movie movie : mMovieList) {
                        if (movie.getId() == cachedMovie.getId()) {
                            movie.setmIsFavorite(true);
                        }
                    }
                }
            }
            mMovieListAdapter.notifyDataSetChanged();
        }
    }

    public abstract void setSelectedPos();
}
