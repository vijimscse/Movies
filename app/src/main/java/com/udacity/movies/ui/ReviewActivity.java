package com.udacity.movies.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.udacity.movies.R;
import com.udacity.movies.dto.Review;
import com.udacity.movies.ui.base.BaseActivity;
import com.udacity.movies.utils.IBundleKeys;

import butterknife.BindView;

/**
 * Created by VijayaLakshmi.IN on 21-03-2018.
 */

public class ReviewActivity extends BaseActivity {

    @BindView(R.id.review_author)
    TextView mReviewAuthor;

    @BindView(R.id.review_text)
    TextView mReviewText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review);
        setPageTitle(R.string.page_title_review);
        Review review = getIntent().getParcelableExtra(IBundleKeys.SELECTED_REVIEW);
        if (review != null) {
            mReviewText.setText(review.getContent());
            mReviewAuthor.setText(review.getAuthor());
        }
    }
}
