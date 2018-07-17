package app.cooking_partner.com.cookingpartner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import app.cooking_partner.com.cookingpartner.fragments.IndividualStepFragment;
import app.cooking_partner.com.cookingpartner.fragments.MasterRecipeFragment;
import app.cooking_partner.com.cookingpartner.fragments.RecipeHomeFragment;
import app.cooking_partner.com.cookingpartner.model.Recipe;
import app.cooking_partner.com.cookingpartner.model.Step;

import static app.cooking_partner.com.cookingpartner.fragments.MasterRecipeFragment.PARCELABLE_KEY;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_STATE = "fragment-state-key";

    public static final String MASTER_RECIPE_FRAGMENT = "master-recipe-fragment";
    public static final String RECIPE_HOME_FRAGMENT = "recipe-home-fragment";
    public static final String INDIVIDUAL_STEP_FRAGMENT = "individual-step-fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(FRAGMENT_STATE)) {
            String chosenFragement = savedInstanceState.getString(FRAGMENT_STATE);
            loadFragment(chosenFragement);
            Log.e(TAG, "Loading saved fragment state: " + chosenFragement);
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
                fragment = new MasterRecipeFragment();
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, fragment, chosenFragement)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, getSupportFragmentManager().findFragmentById(R.id.container).getTag() + " is saved");
        Fragment chosenFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (chosenFragment != null) {
            String chosenFragmentTag = chosenFragment.getTag();
            if (chosenFragmentTag != null) {
                outState.putString(FRAGMENT_STATE, chosenFragmentTag);
                if (chosenFragmentTag.equals(RECIPE_HOME_FRAGMENT)) {
                    if (chosenFragment.getArguments() != null) {
                        Recipe recipe = chosenFragment.getArguments().getParcelable(PARCELABLE_KEY);
                        if (recipe != null) {
                            outState.putParcelable(PARCELABLE_KEY, recipe);
                            Log.e(TAG, "Added recipe " + recipe.getName() + " to the bundle");
                        } else
                            Log.e(TAG, "Received NULL recipe");
                    } else
                        Log.e(TAG, "received NULL arguments");
                } else if (chosenFragmentTag.equals(INDIVIDUAL_STEP_FRAGMENT)) {
                    if (chosenFragment.getArguments() != null) {
                        Step step = chosenFragment.getArguments().getParcelable(PARCELABLE_KEY);
                        if (step != null) {
                            outState.putParcelable(PARCELABLE_KEY, step);
                            Log.e(TAG, "Added step " + step.getShortDescription() + " to the bundle");
                        } else
                            Log.e(TAG, "Received NULL Step");
                    } else
                        Log.e(TAG, "received NULL arguments");
                }
            }
        } else
            Log.e(TAG, "Null fragment received");

        super.onSaveInstanceState(outState);
    }
}
