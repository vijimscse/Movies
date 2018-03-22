package com.udacity.movies.ui.about;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.udacity.movies.R;
import com.udacity.movies.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by VijayaLakshmi.IN on 27-02-2018.
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.version_name)
    TextView mAppVersionName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        setPageTitle(R.string.page_title_about);

        displayAppVersionName();
    }

    private void displayAppVersionName() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            mAppVersionName.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
