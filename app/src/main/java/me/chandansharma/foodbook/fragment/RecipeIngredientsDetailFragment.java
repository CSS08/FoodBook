package me.chandansharma.foodbook.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chandansharma.foodbook.R;
import me.chandansharma.foodbook.adapter.RecipeIngredientsAdapter;
import me.chandansharma.foodbook.model.RecipeIngredients;
import me.chandansharma.foodbook.utils.RecipeDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsDetailFragment extends Fragment {

    @BindView(R.id.rv_recipe_ingredients_detail)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<RecipeIngredients> recipeIngredients = getArguments()
                .getParcelableArrayList(RecipeDetails.RECIPE_INGREDIENTS_KEY);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients_detail, container, false);

        ButterKnife.bind(this, rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecipeIngredientsAdapter recipeIngredientsAdapter = new
                RecipeIngredientsAdapter(getActivity(), recipeIngredients);

        mRecyclerView.setAdapter(recipeIngredientsAdapter);
        return rootView;
    }

}
