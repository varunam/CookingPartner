package app.cooking_partner.com.cookingpartner;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

import app.cooking_partner.com.cookingpartner.api.RecipesApiCallForWidget;
import app.cooking_partner.com.cookingpartner.interfaces.RecipeApiResponseListener;
import app.cooking_partner.com.cookingpartner.model.Ingredient;
import app.cooking_partner.com.cookingpartner.model.Recipe;

import static app.cooking_partner.com.cookingpartner.fragments.MasterRecipeApiFragment.PARCELABLE_KEY;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeApiWidgetProvider extends AppWidgetProvider implements RecipeApiResponseListener {

	List<Recipe> recipeList;
	RecipesApiCallForWidget mRecipesApiCallForWidget;
	RemoteViews mViews;

	private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
								 int appWidgetId, List<Recipe> recipes) {
		CharSequence widgetText = context.getString(R.string.appwidget_text);
		// Construct the RemoteViews object
		mViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
		mViews.setTextViewText(R.id.appwidget_text, widgetText);

		int count = context.getSharedPreferences("RECIPE_PREF", Context.MODE_PRIVATE).getInt("RECIPE_CLICKED", 0);
		Recipe recipe = recipes.get(count);
		List<Ingredient> ingredients = recipe.getIngredients();
		String ingredientsList = recipe.getName() + "\n\n" + ingredients.size() + " Ingredients";
		String ingredients1 = "";
		for (int i = 0; i < ingredients.size(); i++) {
			ingredients1 += "\n" + (i + 1) + ". " + (ingredients.get(i).getIngredientName());
		}

		//create an intent to launch MainActivity when clicked
		Intent intent = new Intent(context, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(PARCELABLE_KEY, recipe);
		intent.putExtra("recipe", bundle);
		Log.e("log", recipe.getName() + "");
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

		mViews.setTextViewText(R.id.appwidget_text, ingredientsList + "\n" + ingredients1);
		mViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

		// Instruct the widget manager to update the widget
		appWidgetManager.updateAppWidget(appWidgetId, mViews);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		mRecipesApiCallForWidget = new RecipesApiCallForWidget(this, context, appWidgetManager, appWidgetIds);
		mRecipesApiCallForWidget.execute();
	}

	public void onPostUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, List<Recipe> recipes) {
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId, recipes);
		}
	}


	@Override
	public void onEnabled(Context context) {
		// Enter relevant functionality for when the first widget is created
	}

	@Override
	public void onDisabled(Context context) {
		// Enter relevant functionality for when the last widget is disabled
	}

	@Override
	public void onResponseReceived(List<Recipe> recipes) {
		onPostUpdate(mRecipesApiCallForWidget.getmContext(), mRecipesApiCallForWidget.getmAppWidgetManager(), mRecipesApiCallForWidget.getmAppWidgetIds(), recipes);
	}

	@Override
	public void onResponseFailed(Throwable T) {

	}
}

