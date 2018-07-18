package app.cooking_partner.com.cookingpartner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import app.cooking_partner.com.cookingpartner.fragments.IndividualStepFragment;
import app.cooking_partner.com.cookingpartner.fragments.MasterRecipeFragment;
import app.cooking_partner.com.cookingpartner.fragments.RecipeHomeFragment;

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

        if (savedInstanceState != null) {
            Log.e(TAG, "default stored SavedInstanceState is used.");
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

}
