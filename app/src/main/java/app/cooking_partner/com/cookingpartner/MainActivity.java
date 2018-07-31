package app.cooking_partner.com.cookingpartner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import app.cooking_partner.com.cookingpartner.fragments.IndividualStepFragment;
import app.cooking_partner.com.cookingpartner.fragments.MasterRecipeApiFragment;
import app.cooking_partner.com.cookingpartner.fragments.RecipeHomeFragment;
import app.cooking_partner.com.cookingpartner.interfaces.OnRecipeClickedListener;
import app.cooking_partner.com.cookingpartner.interfaces.OnStepClickedListener;
import app.cooking_partner.com.cookingpartner.model.Recipe;
import app.cooking_partner.com.cookingpartner.model.Step;

import static app.cooking_partner.com.cookingpartner.fragments.MasterRecipeApiFragment.PARCELABLE_KEY;

public class MainActivity extends AppCompatActivity implements OnRecipeClickedListener, OnStepClickedListener {

    public static final String MASTER_RECIPE_FRAGMENT = "master-recipe-fragment";
    public static final String RECIPE_HOME_FRAGMENT = "recipe-home-fragment";
    public static final String INDIVIDUAL_STEP_FRAGMENT = "individual-step-fragment";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_STATE = "fragment-state-key";
    private boolean twoPaneLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("recipe")) {
            Bundle bundle = getIntent().getBundleExtra("recipe");
            RecipeHomeFragment recipeHomeFragment = new RecipeHomeFragment();
            recipeHomeFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container, recipeHomeFragment, RECIPE_HOME_FRAGMENT)
                    .commit();
        } else if (savedInstanceState != null) {
            Log.e(TAG, "SavedInstanceState received non null");
        } else {
            loadFragment(MASTER_RECIPE_FRAGMENT);
            Log.e(TAG, "Loading master fragment by default");
        }
    }

    private void loadFragment(String chosenFragement) {
        Fragment fragment;
        switch (chosenFragement) {
            case INDIVIDUAL_STEP_FRAGMENT:
                fragment = new IndividualStepFragment();
                break;
            case RECIPE_HOME_FRAGMENT:
                fragment = new RecipeHomeFragment();
                break;
            default:
                fragment = new MasterRecipeApiFragment();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, fragment, chosenFragement)
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setActionbarTitle(String title, boolean backIconDisplay) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(backIconDisplay);
        }
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        if (twoPaneLayout) {
            RecipeHomeFragment recipeHomeFragment = new RecipeHomeFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PARCELABLE_KEY, recipe);
            recipeHomeFragment.setArguments(bundle);
            Log.e(TAG, "Clicked " + recipe.getName());
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, recipeHomeFragment, RECIPE_HOME_FRAGMENT)
                    .commit();
        } else {
            RecipeHomeFragment recipeHomeFragment = new RecipeHomeFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PARCELABLE_KEY, recipe);
            recipeHomeFragment.setArguments(bundle);
            Log.e(TAG, "Clicked " + recipe.getName());
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, recipeHomeFragment, RECIPE_HOME_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void onStepSelected(Step step) {
        twoPaneLayout = findViewById(R.id.container_step) != null;
        if (twoPaneLayout) {
            IndividualStepFragment stepFragment = new IndividualStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PARCELABLE_KEY, step);
            stepFragment.setArguments(bundle);
            if (getSupportFragmentManager() != null) {
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container_step, stepFragment, INDIVIDUAL_STEP_FRAGMENT)
                        .commit();
                Log.e(TAG, "Replacing RecipeHomeFragment by IndividualStepFragment");
            } else
                Log.e(TAG, "Received null FragmentManager");
        } else {
            IndividualStepFragment stepFragment = new IndividualStepFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PARCELABLE_KEY, step);
            stepFragment.setArguments(bundle);
            if (getSupportFragmentManager() != null) {
                getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, stepFragment, INDIVIDUAL_STEP_FRAGMENT)
                        .commit();
                Log.e(TAG, "Replacing RecipeHomeFragment by IndividualStepFragment");
            } else
                Log.e(TAG, "Received null FragmentManager");
        }
    }


}
