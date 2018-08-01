package app.cooking_partner.com.cookingpartner.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import app.cooking_partner.com.cookingpartner.R;
import app.cooking_partner.com.cookingpartner.model.Step;

import static app.cooking_partner.com.cookingpartner.fragments.MasterRecipeApiFragment.PARCELABLE_KEY;

public class IndividualStepFragment extends Fragment {

	private static final String TAG = IndividualStepFragment.class.getSimpleName();

	private SimpleExoPlayerView exoPlayerView;
	private TextView stepDescription;
	private SimpleExoPlayer exoPlayer;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_individual_step, container, false);

		exoPlayerView = rootView.findViewById(R.id.fis_exoplayer_id);
		stepDescription = rootView.findViewById(R.id.fis_step_description_id);

		if (getArguments() != null && getArguments().containsKey(PARCELABLE_KEY)) {
			Step step = getArguments().getParcelable(PARCELABLE_KEY);
			if (step != null) {
				String videoUrl = step.getVideoUrl();
				if (videoUrl != null && !videoUrl.isEmpty()) {
					Log.e(TAG, "Initializing media player with Video Url: " + videoUrl);
					initializePlayer(Uri.parse(videoUrl));
				} else
					Log.e(TAG, "Received null VideoUrl");

				if (stepDescription != null)
					stepDescription.setText(step.getDescription());
			} else
				Log.e(TAG, "Received null step");
		} else
			Log.e(TAG, "Received NULL Step");

		return rootView;
	}

	private void initializePlayer(Uri mediaUri) {

		if (exoPlayer == null) {
			TrackSelector trackSelector = new DefaultTrackSelector();
			LoadControl loadControl = new DefaultLoadControl();
			exoPlayer = ExoPlayerFactory.newSimpleInstance(this.getActivity(), trackSelector, loadControl);
			String userAgent = Util.getUserAgent(this.getActivity(), "CookingPartner");
			MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
					this.getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
			exoPlayer.prepare(mediaSource);
			exoPlayer.setPlayWhenReady(true);
			exoPlayerView.setPlayer(exoPlayer);
		}
	}

	private void releasePlayer() {
		if (exoPlayer != null) {
			exoPlayer.stop();
			exoPlayer.release();
		}
		exoPlayer = null;
	}

	@Override
	public void onStop() {
		releasePlayer();
		super.onStop();
	}
}
