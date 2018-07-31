package app.cooking_partner.com.cookingpartner.fragments;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.cooking_partner.com.cookingpartner.R;
import app.cooking_partner.com.cookingpartner.adapters.RecyclerViewAdapter;
import app.cooking_partner.com.cookingpartner.api.RecipesApiCall;
import app.cooking_partner.com.cookingpartner.interfaces.RecipeApiResponseListener;
import app.cooking_partner.com.cookingpartner.model.Recipe;

public class MasterRecipeApiFragment extends Fragment implements RecipeApiResponseListener {

    public static final String PARCELABLE_KEY = "parcelable-key";

    private static final String TAG = MasterRecipeApiFragment.class.getSimpleName();
    List<Recipe> recipes;
    RecipesApiCall recipesApiCall;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private boolean twoPaneLayout = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_recyclerview, container, false);

        recyclerView = rootView.findViewById(R.id.lr_recycler_view_id);
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView.setHasFixedSize(true);
        if (isTablet())
            recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        recipesApiCall = new RecipesApiCall(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recipesApiCall != null) {
            recipesApiCall.execute();
        }
    }

    public void setRecipes(List<Recipe> recipe) {
        recipes = recipe;
    }


    public boolean isTablet() {
        boolean xlarge = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Override
    public void onResponseReceived(List<Recipe> recipes) {
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), recipes, null);
        recyclerView.setAdapter(recyclerViewAdapter);
        if (progressDialog.isShowing())
            progressDialog.cancel();
    }

    @Override
    public void onResponseFailed(Throwable T) {
        if (progressDialog.isShowing())
            progressDialog.cancel();
    }
}
