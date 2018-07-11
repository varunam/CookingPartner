package app.cooking_partner.com.cookingpartner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.cooking_partner.com.cookingpartner.R;
import app.cooking_partner.com.cookingpartner.model.Recipe;
import app.cooking_partner.com.cookingpartner.model.Step;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private List<Recipe> recipes;
    private Context context;
    private OnRecipeClickedListener onRecipeClickedListener;

    private List<Step> steps;

    private String CATEGORY;
    private String RECIPE_LIST = "recipe-list";
    private String STEPS_LIST = "steps-list";

    public interface OnRecipeClickedListener {
        void onRecipeSelected(Recipe recipe);
    }


    public RecyclerViewAdapter(Context context, List<Recipe> recipes, OnRecipeClickedListener onRecipeClickedListener) {
        this.onRecipeClickedListener = onRecipeClickedListener;
        this.recipes = recipes;
        this.context = context;
        Log.e(TAG, "Initialized recyclerViewAdapter with recipes size: " + recipes.size());

        CATEGORY = RECIPE_LIST;
    }

    public RecyclerViewAdapter(Context context, List<Step> steps) {
        this.context = context;
        this.steps = steps;

        CATEGORY = STEPS_LIST;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (CATEGORY.equals(RECIPE_LIST)) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_step, viewGroup, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (CATEGORY.equals(RECIPE_LIST)) {
            final Recipe recipe = recipes.get(position);
            Log.e(TAG, "Recipe " + recipe.getName() + " has " + recipe.getIngredients().size() + " ingredients");
            viewHolder.recipeTitleTextView.setText(recipe.getName());
            viewHolder.ingredientsCountTextView.setText(String.format(context.getResources().getString(R.string.x_ingredients),
                    recipe.getIngredients().size()));
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, "Sending position " + position);
                    onRecipeClickedListener.onRecipeSelected(recipe);
                }
            });
        } else if (CATEGORY.equals(STEPS_LIST)) {
            Step step = steps.get(position);
            viewHolder.stepTextView.setText(String.format(context.getResources().getString(R.string.step_x), position+1));
        }
    }

    @Override
    public int getItemCount() {
        if (CATEGORY.equals(RECIPE_LIST))
            return recipes.size();
        else
            return steps.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        //views related to RECIPE
        private ImageView recipeIconImageView;
        private TextView ingredientsCountTextView, recipeTitleTextView;
        private CardView cardView;

        //views related to STEP
        private TextView stepTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if (CATEGORY.equals(RECIPE_LIST)) {
                cardView = itemView.findViewById(R.id.recipe_card_view_id);
                recipeTitleTextView = itemView.findViewById(R.id.ir_recipe_title_id);
                recipeIconImageView = itemView.findViewById(R.id.ir_recipe_icon_id);
                ingredientsCountTextView = itemView.findViewById(R.id.ir_recipe_ingredients_count_text_view_id);
            }
            if (CATEGORY.equals(STEPS_LIST)) {
                stepTextView = itemView.findViewById(R.id.is_step_id);
            }
        }
    }
}
