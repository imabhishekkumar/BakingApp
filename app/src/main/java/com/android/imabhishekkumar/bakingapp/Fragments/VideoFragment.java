package com.android.imabhishekkumar.bakingapp.Fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.imabhishekkumar.bakingapp.R;
import com.android.imabhishekkumar.bakingapp.Utils.Constants;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


public class VideoFragment extends Fragment implements ExoPlayer.EventListener {
    public static final String TAG = VideoFragment.class.getSimpleName();
    TextView stepDescTV;
    ImageView placeholderIV;
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    String description;
    String url;
    String thumbnailImage;
    long positionPlayer = 0;
    boolean mTwoPane;
    boolean playWhenReady;
    Uri videoUri;
    private MediaSessionCompat mediaSessionCompat;
    private PlaybackStateCompat.Builder playbackBuilder;


    public VideoFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            description = bundle.getString(Constants.BUNDLE_STEPS_DESC);
            url = bundle.getString(Constants.BUNDLE_STEPS_VIDEO_URL);
            thumbnailImage = bundle.getString(Constants.BUNDLE_STEPS_THUMB_URL);
            mTwoPane = bundle.getBoolean(Constants.KEY_TWO_PANE);
        }
        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(Constants.KEY_PLAY_WHEN_READY);
            positionPlayer = savedInstanceState.getLong(Constants.PLAYER_POS);
            url = savedInstanceState.getString(Constants.BUNDLE_STEPS_VIDEO_URL);
        }
        Log.d("OnCreate", " ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_row, container, false);
        stepDescTV = rootView.findViewById(R.id.step_descriptionTV);
        placeholderIV = rootView.findViewById(R.id.no_video_image);
        simpleExoPlayerView = rootView.findViewById(R.id.exo_playerView);
        playWhenReady = true;

        if (url != null) {
            if (url.equals("")) {
                simpleExoPlayerView.setVisibility(View.GONE);
                placeholderIV.setVisibility(View.VISIBLE);
                if (!thumbnailImage.equals("")) {
                    Picasso.get()
                            .load(thumbnailImage)
                            .into(placeholderIV);
                }
                checkAndSetLayout();
            } else {
                placeholderIV.setVisibility(View.GONE);
                initializeMedia();
                videoUri = Uri.parse(url);
                initializePlayer(videoUri);
                checkAndSetLayout();
            }
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
            checkAndSetLayout();


        }
        Log.d("Player OnCreateView", " ");
        Log.d("Player Position", String.valueOf(positionPlayer));
        Log.d("PlayerState CreateView", String.valueOf(playWhenReady));


        return rootView;
    }

    private void initializeMedia() {
        mediaSessionCompat = new MediaSessionCompat(getActivity(), TAG);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);
        playbackBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSessionCompat.setPlaybackState(playbackBuilder.build());
        mediaSessionCompat.setCallback(new SessionCallBacks());
        mediaSessionCompat.setActive(true);
    }

    private void checkAndSetLayout() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            stepDescTV.setVisibility(View.VISIBLE);
            stepDescTV.setText(description);
//            positionPlayer= simpleExoPlayer.getCurrentPosition();
            //simpleExoPlayer.seekTo(positionPlayer);
        } else if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            stepDescTV.setVisibility(View.GONE);
            hideSystemUI();
            //        positionPlayer= simpleExoPlayer.getCurrentPosition();
            //         simpleExoPlayer.seekTo(positionPlayer);
            simpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            simpleExoPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
    }

    /*  @Override
       public void onConfigurationChanged(Configuration newConfig) {
           super.onConfigurationChanged(newConfig);
          Log.d("PlayerOnConfig,", "");
           // Checks the orientation of the screen
          checkAndSetLayout();
        /* if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
               positionPlayer= simpleExoPlayer.getCurrentPosition();
               stepDescTV.setVisibility(View.GONE);
               hideSystemUI();
               simpleExoPlayer.seekTo(positionPlayer);
               simpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
               simpleExoPlayerView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
           } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
              positionPlayer= simpleExoPlayer.getCurrentPosition();
              simpleExoPlayer.seekTo(positionPlayer);
               stepDescTV.setVisibility(View.VISIBLE);
               stepDescTV.setText(description);
           }
       }
   */
    private void hideSystemUI() {
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            // Enables regular immersive mode.
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // Hide the nav bar and status bar
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_READY && playWhenReady) {
            playbackBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        } else if (playbackState == ExoPlayer.STATE_READY) {
            playbackBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        }
        mediaSessionCompat.setPlaybackState(playbackBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }


    private void initializePlayer(Uri mediaUri) {
        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getActivity(),
                    getActivity().getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.seekTo(positionPlayer);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (mediaSessionCompat != null) {
            mediaSessionCompat.setActive(false);
        }
        Log.d("Player OnDestroy", " ");
        Log.d("Player Position", String.valueOf(positionPlayer));
        Log.d("PlayerState Destroy", String.valueOf(playWhenReady));
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            //positionPlayer=simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    private class SessionCallBacks extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
            simpleExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            super.onPause();
            simpleExoPlayer.setPlayWhenReady(false);
            positionPlayer = simpleExoPlayer.getCurrentPosition();

        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            simpleExoPlayer.seekTo(0);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("Player OnPause", " ");
        if (simpleExoPlayer != null) {
            positionPlayer = simpleExoPlayer.getCurrentPosition();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            Log.d("PlayerPosition Pause", String.valueOf(positionPlayer));
            Log.d("PlayerState Pause", String.valueOf(playWhenReady));
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Player OnResume", " ");
        if (simpleExoPlayer != null) {
            //checkAndSetLayout();
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(positionPlayer);

        } else {
            initializeMedia();
            initializePlayer(videoUri);
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(positionPlayer);
        }
        Log.d("Player Position", String.valueOf(positionPlayer));
        Log.d("PlayerState Resume", String.valueOf(playWhenReady));


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Constants.PLAYER_POS, positionPlayer);
        outState.putBoolean(Constants.KEY_PLAY_WHEN_READY, playWhenReady);
        outState.putString(Constants.BUNDLE_STEPS_VIDEO_URL, url);
    }


}
