package com.udacity.movies.ui.details;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movies.R;
import com.udacity.movies.db.MoviesContract;
import com.udacity.movies.dto.Movie;
import com.udacity.movies.dto.Review;
import com.udacity.movies.dto.ReviewList;
import com.udacity.movies.dto.Video;
import com.udacity.movies.dto.VideoList;
import com.udacity.movies.io.IOManager;
import com.udacity.movies.ui.ReviewActivity;
import com.udacity.movies.ui.base.BaseFragment;
import com.udacity.movies.utils.DateFormatter;
import com.udacity.movies.utils.IBundleKeys;
import com.udacity.movies.utils.NetworkUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.udacity.movies.utils.Config.IMAGE_BASE_URL;
import static com.udacity.movies.utils.IBundleKeys.SELECTED_MOVIE;

/**
 * Created by VijayaLakshmi.IN on 17/03/2018.
 * <p>
 * Showcases the selected movie details
 */
public class MovieDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final String YOUTUBE_LINK = "vnd.youtube://";
    private static final String FORCE_FULL_SCREEN_INTENT = "force_fullscreen";
    private Movie mSelectedMovie;

    @BindView(R.id.movie_title)
    TextView mTitle;

    @BindView(R.id.synopsis)
    TextView mSynopsis;

    @BindView(R.id.userRating)
    TextView mUserRating;

    @BindView(R.id.releaseDate)
    TextView mReleaseDate;

    @BindView(R.id.movie_poster)
    ImageView mMoviePoster;

    @BindView(R.id.favorite)
    ImageView mFavorite;

    @BindView(R.id.trailer_list_container)
    ViewGroup mTrailerListContainer;

    @BindView(R.id.review_list_container)
    ViewGroup mReviewListContainer;

    @BindView(R.id.trailer_container)
    ViewGroup mTrailerContainer;

    @BindView(R.id.review_container)
    ViewGroup mReviewContainer;

    @BindView(R.id.scrollview)
    NestedScrollView mScrollView;

    @BindView(R.id.reviews_title)
    TextView mReviewsTitle;

    @BindView(R.id.movie_image)
    ImageView mMovieImage;

    private ArrayList<Video> mVideoList = new ArrayList<>();
    private ArrayList<Review> mReviewList = new ArrayList<>();

    public static MovieDetailFragment newInstance(Movie selectedMovie) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SELECTED_MOVIE, selectedMovie);
        movieDetailFragment.setArguments(bundle);

        return movieDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_details, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            if ((mSelectedMovie = bundle.getParcelable(SELECTED_MOVIE)) != null) {
                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(mSelectedMovie.getTitle());
                }
                mTitle.setText(mSelectedMovie.getOriginalTitle());
                mSynopsis.setText(mSelectedMovie.getOverview());
                mUserRating.setText(String.valueOf(mSelectedMovie.getVoteAverage()));
                Picasso.with(getActivity()).load(IMAGE_BASE_URL + mSelectedMovie.getBackdropPath())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.image_error)
                        .into(mMovieImage);
                Picasso.with(getActivity()).load(IMAGE_BASE_URL + mSelectedMovie.getPosterPath())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.image_error)
                        .into(mMoviePoster);
                mReleaseDate.setText(DateFormatter.getDateFormat(mSelectedMovie.getReleaseDate()));
                if (mSelectedMovie.isFavorite()) {
                    mFavorite.setImageResource(R.drawable.favorite_selected);
                } else {
                    mFavorite.setImageResource(R.drawable.favorite_unselected);
                }
                fetchMovieReviews();
                fetchTrailerVideos();
            }
        }
    }

    private void fetchTrailerVideos() {
        if (getActivity() != null &&
                NetworkUtility.isInternetConnected(getActivity()) && !isDetached()) {
            IOManager.requestTrailerVideos(mSelectedMovie.getId(), new Callback<VideoList>() {
                @Override
                public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                    if (!isDetached() && isAdded() && response != null && response.body() != null) {
                        List videoList = response.body().getVideos();
                        if (videoList != null && !videoList.isEmpty()) {
                            mVideoList.addAll(videoList);
                            mTrailerContainer.setVisibility(View.VISIBLE);
                            showVideoList();
                        } else {
                            mTrailerContainer.setVisibility(View.GONE);
                        }
                    } else {
                        mTrailerContainer.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<VideoList> call, Throwable t) {
                    mTrailerContainer.setVisibility(View.GONE);
                }
            });
        }
    }

    private void showVideoList() {
        if (getActivity() != null) {
            mTrailerListContainer.removeAllViews();

            for (int i = 0; i < mVideoList.size(); i++) {
                Video video = mVideoList.get(i);

                View trailerView = LayoutInflater.from(getActivity()).inflate(R.layout.video_row, null);
                trailerView.setTag(i);
                TextView title = trailerView.findViewById(R.id.video_title);
                title.setText(video.getName());
                trailerView.setOnClickListener(this);
                mTrailerListContainer.addView(trailerView);
            }
        }
    }

    private void showReviewList() {
        mReviewListContainer.removeAllViews();

        for (int index = 0; index < mReviewList.size(); index++) {
            Review review = mReviewList.get(index);
            View reviewView = LayoutInflater.from(getActivity()).inflate(R.layout.review_row, null);
            TextView title = reviewView.findViewById(R.id.review);
            TextView author = reviewView.findViewById(R.id.review_author);
            Button showMoreBtn = reviewView.findViewById(R.id.show_more);
            author.setText(review.getAuthor());
            String reviewContent = review.getContent();
            int contentLength = reviewContent.length();
            int maxReviewCharacters = getResources().getInteger(R.integer.max_review_characters);
            if (contentLength > maxReviewCharacters) {
                title.setText(getString(R.string.review_text, reviewContent.substring(0, maxReviewCharacters)));
            } else {
                title.setText(reviewContent);
            }
            showMoreBtn.setTag(index);
            showMoreBtn.setVisibility(contentLength > maxReviewCharacters ? View.VISIBLE : View.GONE);
            showMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    Review review = mReviewList.get(position);
                    Intent intent = new Intent(getActivity(), ReviewActivity.class);
                    intent.putExtra(IBundleKeys.SELECTED_REVIEW, review);
                    startActivity(intent);
                }
            });
            mReviewListContainer.addView(reviewView);
        }
    }

    private void fetchMovieReviews() {
        if (getActivity() != null &&
                NetworkUtility.isInternetConnected(getActivity()) && !isDetached()) {
            IOManager.requestMovieReviews(mSelectedMovie.getId(), new Callback<ReviewList>() {
                @Override
                public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                    if (getActivity() != null && isAdded() && response != null && response.body() != null) {
                        List reviewList = response.body().getReviews();
                        if (reviewList != null && !reviewList.isEmpty()) {
                            mReviewList.addAll(reviewList);
                            mReviewsTitle.setText(getString(R.string.reviews, reviewList.size()));
                            mReviewContainer.setVisibility(View.VISIBLE);
                            showReviewList();
                        } else {
                            mReviewContainer.setVisibility(View.GONE);
                        }
                    } else {
                        mReviewContainer.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ReviewList> call, Throwable t) {
                    if (getActivity() != null && isAdded()) {
                        mReviewContainer.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @OnClick(R.id.favorite)
    public void onFavClick(View view) {
        if (!mSelectedMovie.isFavorite()) {
            // Add the item to DB
            mSelectedMovie.setmIsFavorite(true);

            insertIntoDB(mSelectedMovie);
        } else {
            // delete the item from DB and update the current list if already in favorites view
            deleteFromDB(mSelectedMovie.getId());
            mSelectedMovie.setmIsFavorite(false);
        }
        if (mSelectedMovie.isFavorite()) {
            mFavorite.setImageResource(R.drawable.favorite_selected);
        } else {
            mFavorite.setImageResource(R.drawable.favorite_unselected);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.video_row:
                int position = (int) view.getTag();
                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_LINK + mVideoList.get(position).getKey()));
                intent.putExtra(FORCE_FULL_SCREEN_INTENT, true);
                // Verify that the intent will resolve to an activity
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().startActivity(intent);
                }
                break;
        }
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

    public int getScrollX() {
        int x = 0;
        if (getActivity() != null) {
            x = mScrollView.getScrollX();
        }

        return x;
    }

    public int getScrollY() {
        int y = 0;

        if (getActivity() != null) {
            y = mScrollView.getScrollY();
        }

        return y;
    }

    public void scroll(final int x, final int y) {
        if (getActivity() != null) {
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.smoothScrollTo(x, y);
                }
            });
        }
    }
}
