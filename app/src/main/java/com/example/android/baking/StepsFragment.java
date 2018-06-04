package com.example.android.baking;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.baking.utilities.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.android.baking.DetailsActivity.mTwoPane;

/**
 * Created by hassa on 4/15/2018.
 */

public class StepsFragment extends Fragment {
    SimpleExoPlayer simpleExoPlayer;
    SimpleExoPlayerView simpleExoPlayerView;
    ImageView mNoVideoImageView;
    TextView mDescriptionTextView;
    ArrayList<Steps> mSteps;
    Button mNextButton;
    Button mPerviousButton;
    int mPosition;
    long position;
    String mVideoUrl;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment, container, false);
        mNoVideoImageView = view.findViewById(R.id.no_video_image);
        mDescriptionTextView = view.findViewById(R.id.exo_description);
        simpleExoPlayerView = view.findViewById(R.id.exo_video_player);
        mNextButton = view.findViewById(R.id.next_button);
        mPerviousButton = view.findViewById(R.id.pervious_button);

        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory(defaultBandwidthMeter);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), new DefaultTrackSelector(factory), new DefaultLoadControl());
        simpleExoPlayerView.setPlayer(simpleExoPlayer);

        mSteps = getArguments().getParcelableArrayList("Steps");
        mPosition = getArguments().getInt("Position");

        mVideoUrl = mSteps.get(mPosition).getVideoURL();
        String mThumbnaiUrl = mSteps.get(mPosition).getThumbnailURL();
        String mDescription = mSteps.get(mPosition).getDescription();

        if (TextUtils.isEmpty(mVideoUrl)) {
            simpleExoPlayerView.setVisibility(View.GONE);
            if (mThumbnaiUrl.equals(""))
                mNoVideoImageView.setVisibility(View.VISIBLE);
            else
                Picasso.with(getActivity()).load(mThumbnaiUrl).into(mNoVideoImageView);

        }
        mDescriptionTextView.setText(mDescription);
        if (mPosition == mSteps.size() - 1)
            mNextButton.setVisibility(View.INVISIBLE);
        else
            mNextButton.setVisibility(View.VISIBLE);

        if (mPosition == 0)
            mPerviousButton.setVisibility(View.INVISIBLE);
        else
            mPerviousButton.setVisibility(View.VISIBLE);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextButton();
            }
        });

        mPerviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousButton();
            }
        });

        if (savedInstanceState!=null)
        {
            simpleExoPlayer.seekTo(savedInstanceState.getLong("VideoPosition"));
            simpleExoPlayer.setPlayWhenReady(savedInstanceState.getBoolean("PlayWhenReady"));

        }

        return view;

    }

    public void nextButton() {
        mPosition++;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Steps", mSteps);
        bundle.putInt("Position", mPosition);
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setArguments(bundle);
        if (mTwoPane) {
            getFragmentManager().beginTransaction().replace(R.id.details_fragment_container, stepsFragment).commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.details_activity, stepsFragment).commit();

        }
    }

    public void previousButton() {
        mPosition--;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Steps", mSteps);
        bundle.putInt("Position", mPosition);
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setArguments(bundle);
        if (mTwoPane) {
            getFragmentManager().beginTransaction().replace(R.id.details_fragment_container, stepsFragment).commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.details_activity, stepsFragment).commit();

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            release();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            release();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || simpleExoPlayer == null)) {
            initializePlayer();
        }
    }

    public void initializePlayer(){
        simpleExoPlayerView.setVisibility(View.VISIBLE);
        mNoVideoImageView.setVisibility(View.INVISIBLE);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "ExoPlayer"));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mVideoUrl), dataSourceFactory, extractorsFactory, null, null);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    public void release() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("VideoPosition",simpleExoPlayer.getCurrentPosition());
        outState.putBoolean("PlayWhenReady",simpleExoPlayer.getPlayWhenReady());
    }
}
