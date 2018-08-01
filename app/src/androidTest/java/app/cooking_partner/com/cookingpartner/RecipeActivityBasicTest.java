package app.cooking_partner.com.cookingpartner;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityBasicTest {

	@Rule
	public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
	
	@Test
	public void ClickRecipe1_checkIngredients() {
		onView(withId(R.id.lr_recycler_view_id))
				.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
		
		onView(withId(R.id.flrm_ingredients_title_id)).check(matches(withText("9 Ingredients")));
	}
	
	
	@Before
	public void init() {
		activityTestRule.getActivity()
				.getSupportFragmentManager().beginTransaction();
	}

}
