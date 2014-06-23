package com.jeffinmadison.calm;

import java.io.IOException;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SceneSelectorActivity extends Activity implements View.OnClickListener {
    @InjectView(R.id.thumb) ImageButton mThumb;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_selector);
        ButterKnife.inject(this);

        mThumb.setOnClickListener(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scene_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(final View view) {
        if (view == mThumb) {
            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.slide_up,R.anim.no_change);
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PlaceholderFragment.newInstance(position+1, getResources().getColor(R.color.AliceBlue), "birds");
                case 1:
                    return PlaceholderFragment.newInstance(position+1, getResources().getColor(R.color.MediumAquamarine), "front");
                default:
                    return PlaceholderFragment.newInstance(position+1, getResources().getColor(R.color.Turquoise), "");
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
        private static final String TAG = PlaceholderFragment.class.getSimpleName();

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_PAGE_COLOR = "page_color";
        private static final String ARG_ASSET_NAME = "asset_name";

        @InjectView(R.id.videoView) VideoView mVideoView;
        @InjectView(R.id.section_label) TextView mSectionLabelTextView;

        private int mPageColor;
        private int mSectionNumber;
        private String mAssetName;

        public static PlaceholderFragment newInstance(int sectionNumber, int pageColor, String assetName) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt(ARG_PAGE_COLOR, pageColor);
            args.putString(ARG_ASSET_NAME, assetName);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mPageColor = getArguments().getInt(ARG_PAGE_COLOR);
                mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
                mAssetName = getArguments().getString(ARG_ASSET_NAME);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_scene_selector, container, false);
            ButterKnife.inject(this, rootView);

            if (mAssetName != null && mAssetName.length() > 0) {
                String fileName = "android.resource://" + getActivity().getPackageName() + "/raw/" + mAssetName;
                mVideoView.setVideoURI(Uri.parse(fileName));
                mVideoView.setOnPreparedListener(this);
                mVideoView.setOnCompletionListener(this);
            }
            mSectionLabelTextView.setText(String.valueOf(mSectionNumber));

            return rootView;
        }

        @Override
        public void setUserVisibleHint(final boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {
                if (mVideoView != null) {
                    mVideoView.start();
                }
            }
        }

        @Override
        public void onCompletion(final MediaPlayer mp) {
//            mp.start();
        }

        @Override
        public void onPrepared(final MediaPlayer mp) {
//            mp.start();
        }
    }
}
