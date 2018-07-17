package app.cooking_partner.com.cookingpartner.fragments;

import android.app.ProgressDialog;
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

import java.util.List;

import app.cooking_partner.com.cookingpartner.Api;
import app.cooking_partner.com.cookingpartner.R;
import app.cooking_partner.com.cookingpartner.adapters.RecyclerViewAdapter;
import app.cooking_partner.com.cookingpartner.interfaces.OnRecipeClickedListener;
import app.cooking_partner.com.cookingpartner.model.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static app.cooking_partner.com.cookingpartner.MainActivity.RECIPE_HOME_FRAGMENT;

public class MasterRecipeFragment extends Fragment implements OnRecipeClickedListener {

    public static final String PARCELABLE_KEY = "parcelable-key";

    private static final String TAG = MasterRecipeFragment.class.getSimpleName();
    private RecyclerViewAdapter recyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_recyclerview, container, false);

        final RecyclerView recyclerView = rootView.findViewById(R.id.lr_recycler_view_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        //creating retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creating api interface
        Api api = retrofit.create(Api.class);

        // Set up progress before call
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(MasterRecipeFragment.this.getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.show();


        Call<List<Recipe>> recipesCall = api.getRecipes();
        recipesCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                progressDialog.dismiss();
                List<Recipe> recipes = response.body();
                recyclerViewAdapter = new RecyclerViewAdapter(MasterRecipeFragment.this.getActivity(), recipes, MasterRecipeFragment.this);
                recyclerView.setAdapter(recyclerViewAdapter);
                for (Recipe recipe : recipes) {
                    Log.e(TAG, "Recipe name: " + recipe.getName());
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "Failed to retrieve recipes with error: " + t.getMessage());
            }
        });

        return rootView;
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        RecipeHomeFragment recipeHomeFragment = new RecipeHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELABLE_KEY, recipe);
        recipeHomeFragment.setArguments(bundle);
        Log.e(TAG, "Clicked " + recipe.getName());
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, recipeHomeFragment, RECIPE_HOME_FRAGMENT)
                .commit();
    }
}
