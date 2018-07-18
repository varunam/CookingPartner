package app.cooking_partner.com.cookingpartner.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.cooking_partner.com.cookingpartner.R;
import app.cooking_partner.com.cookingpartner.adapters.RecyclerViewAdapter;
import app.cooking_partner.com.cookingpartner.interfaces.OnStepClickedListener;
import app.cooking_partner.com.cookingpartner.model.Ingredient;
import app.cooking_partner.com.cookingpartner.model.Recipe;
import app.cooking_partner.com.cookingpartner.model.Step;

import static app.cooking_partner.com.cookingpartner.MainActivity.INDIVIDUAL_STEP_FRAGMENT;
import static app.cooking_partner.com.cookingpartner.fragments.MasterRecipeFragment.PARCELABLE_KEY;

public class RecipeHomeFragment extends Fragment implements OnStepClickedListener {
    private static final String TAG = RecipeHomeFragment.class.getSimpleName();
    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_recipe_home, container, false);

        TextView ingredientsTitle = rootView.findViewById(R.id.flrm_ingredients_title_id);
        TextView ingredientsList = rootView.findViewById(R.id.flrm_ingredients_id);
        RecyclerView recyclerView = rootView.findViewById(R.id.flrm_recycler_view_id);
        recipe = getArguments().getParcelable(PARCELABLE_KEY);

        if (recipe != null) {
            Log.e(TAG, "Received Recipe: " + recipe.getName());

            List<Step> steps = recipe.getSteps();
            List<Ingredient> ingredients = recipe.getIngredients();

            int counter = 1;
            for (Ingredient ingredient : ingredients) {
                ingredientsList.append(counter + ". " + ingredient.getIngredientName() + " " + ingredient.getQuantity() + ingredient.getMeasure() + "\n");
                counter++;
            }
            ingredientsTitle.setText(String.format(this.getActivity().getResources().getString(R.string.x_ingredients), ingredients.size()));
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this.getActivity(), steps, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            recyclerView.setAdapter(recyclerViewAdapter);
        } else
            Log.e(TAG, "Received NULL Recipe");

        return rootView;
    }

    @Override
    public void onRecipeSelected(Step step) {
        IndividualStepFragment stepFragment = new IndividualStepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELABLE_KEY, step);
        stepFragment.setArguments(bundle);
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, stepFragment, INDIVIDUAL_STEP_FRAGMENT)
                    .commit();
            Log.e(TAG, "Replacing RecipeHomeFragment by IndividualStepFragment");
        } else
            Log.e(TAG, "Received null FragmentManager");
    }

}
