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
import app.cooking_partner.com.cookingpartner.model.Ingredient;
import app.cooking_partner.com.cookingpartner.model.Recipe;
import app.cooking_partner.com.cookingpartner.model.Step;

import static app.cooking_partner.com.cookingpartner.fragments.MasterRecipeFragment.PARCELABLE_KEY;

public class RecipeHomeFragment extends Fragment {
    private static final String TAG = RecipeHomeFragment.class.getSimpleName();

    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_recipe_home, container, false);

        TextView ingredientsTitle = rootView.findViewById(R.id.flrm_ingredients_title_id);
        TextView ingredientsList = rootView.findViewById(R.id.flrm_ingredients_id);
        RecyclerView recyclerView = rootView.findViewById(R.id.flrm_recycler_view_id);

        Recipe recipe = getArguments().getParcelable(PARCELABLE_KEY);
        Log.e(TAG,"Received Recipe: " + recipe.getName());

        List<Step> steps = recipe.getSteps();
        List<Ingredient> ingredients = recipe.getIngredients();

        ingredientsTitle.setText(String.format(this.getActivity().getResources().getString(R.string.x_ingredients),ingredients.size()));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this.getActivity(),steps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);


        return rootView;
    }
}
