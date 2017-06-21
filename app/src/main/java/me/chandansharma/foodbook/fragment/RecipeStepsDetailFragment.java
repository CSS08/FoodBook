package me.chandansharma.foodbook.fragment;


import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;

import me.chandansharma.foodbook.R;
import me.chandansharma.foodbook.model.RecipeSteps;
import me.chandansharma.foodbook.utils.RecipeDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsDetailFragment extends Fragment {

    private ArrayList<RecipeSteps> mRecipeSteps;
    private int mRecipeStepsIndex;

    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRecipeSteps = getArguments()
                .getParcelableArrayList(RecipeDetails.RECIPE_STEPS_KEY);
        mRecipeStepsIndex = getArguments().getInt(RecipeDetails.RECIPE_STEPS_INDEX);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps_detail, container, false);
        mSimpleExoPlayerView = (SimpleExoPlayerView) rootView
                .findViewById(R.id.epv_recipe_steps_video);

        Uri mediaUri = Uri.parse(mRecipeSteps.get(mRecipeStepsIndex).getRecipeStepsVideoUrl());

        //Initialize the Player
        initializePlayer(mediaUri);

        TextView recipeStepsDescription = (TextView) rootView
                .findViewById(R.id.tv_recipe_steps_detail);

        TextView previousRecipeStepsTextView = (TextView) rootView
                .findViewById(R.id.tv_previous_step);
        TextView nextRecipeStepsTextView = (TextView) rootView.findViewById(R.id.tv_next_step);
        LinearLayout recipeNavigationLinearLayout = (LinearLayout)
                rootView.findViewById(R.id.ll_recipe_navigation);
        if (mRecipeSteps != null)
            recipeStepsDescription.setText(mRecipeSteps.get(mRecipeStepsIndex)
                    .getRecipeStepsDescription());
        float smallestWidth = getSmallestWidth();

        if (smallestWidth >= 600 || getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE)
            recipeNavigationLinearLayout.setVisibility(View.GONE);
        else {

            previousRecipeStepsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRecipeStepsIndex <= 0)
                        Toast.makeText(getActivity(),
                                "You already on first step", Toast.LENGTH_SHORT).show();
                    else {
                        Bundle recipeStepsDataBundle = new Bundle();

                        recipeStepsDataBundle.putParcelableArrayList(RecipeDetails.RECIPE_STEPS_KEY,
                                mRecipeSteps);
                        recipeStepsDataBundle.putInt(RecipeDetails.RECIPE_STEPS_INDEX,
                                mRecipeStepsIndex - 1);

                        Fragment recipeStepsDetailFragment = new RecipeStepsDetailFragment();
                        recipeStepsDetailFragment.setArguments(recipeStepsDataBundle);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fm_recipe_detail, recipeStepsDetailFragment)
                                .commit();
                    }
                }
            });

            nextRecipeStepsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mRecipeStepsIndex >= mRecipeSteps.size() - 1)
                        Toast.makeText(getActivity(),
                                "You already on last step", Toast.LENGTH_SHORT).show();
                    else {
                        Bundle recipeStepsDataBundle = new Bundle();

                        recipeStepsDataBundle.putParcelableArrayList(RecipeDetails.RECIPE_STEPS_KEY,
                                mRecipeSteps);
                        recipeStepsDataBundle.putInt(RecipeDetails.RECIPE_STEPS_INDEX,
                                mRecipeStepsIndex + 1);

                        Fragment recipeStepsDetailFragment = new RecipeStepsDetailFragment();
                        recipeStepsDetailFragment.setArguments(recipeStepsDataBundle);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fm_recipe_detail, recipeStepsDetailFragment)
                                .commit();
                    }
                }
            });
        }
        return rootView;
    }

    private float getSmallestWidth() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        int widthInPixel = displayMetrics.widthPixels;
        int heightInPixel = displayMetrics.heightPixels;

        float scaleFactor = displayMetrics.density;

        float widthInDp = widthInPixel / scaleFactor;
        float heightInDp = heightInPixel / scaleFactor;

        return Math.min(widthInDp, heightInDp);
    }

    //Initialize the Player Method
    private void initializePlayer(Uri mediaUri) {
        if (mSimpleExoPlayer == null) {
            //Create an instance of the ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),
                    trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mSimpleExoPlayer);

            //Prepare the MediaStore
            String userAgent = Util.getUserAgent(getActivity(), "foodbook");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
        }

    }

    /**
     * Release the Player when the Fragment is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleExoPlayer.stop();
        mSimpleExoPlayer.release();
        mSimpleExoPlayer = null;
    }
}
