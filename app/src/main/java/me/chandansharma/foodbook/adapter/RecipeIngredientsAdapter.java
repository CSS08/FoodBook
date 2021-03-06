package me.chandansharma.foodbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.chandansharma.foodbook.R;
import me.chandansharma.foodbook.model.RecipeIngredients;

/**
 * Created by iamcs on 2017-06-20.
 */

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<RecipeIngredients> mRecipeIngredients;

    public RecipeIngredientsAdapter(Context context,
                                    ArrayList<RecipeIngredients> recipeIngredients) {
        mContext = context;
        mRecipeIngredients = recipeIngredients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeIngredientsViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_ingredients_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecipeIngredientsViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeIngredients.size();
    }

    class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_recipe_ingredients_name)
        TextView mRecipeIngredientsNameTextView;

        @BindView(R.id.tv_recipe_ingredients_quantity)
        TextView mRecipeIngredientsQuantityTextView;

        @BindView(R.id.tv_recipe_ingredients_measure)
        TextView mRecipeIngredientsMeasureTextView;

        private RecipeIngredientsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        private void bindView(int itemPosition) {
            mRecipeIngredientsNameTextView.setText(mRecipeIngredients.get(itemPosition)
                    .getRecipeIngredientsName());
            mRecipeIngredientsQuantityTextView.setText(String.valueOf(mRecipeIngredients
                    .get(itemPosition).getRecipeIngredientsQuantity()));
            mRecipeIngredientsMeasureTextView.setText(mRecipeIngredients.get(itemPosition)
                    .getRecipeIngredientsMeasure());
        }
    }
}
