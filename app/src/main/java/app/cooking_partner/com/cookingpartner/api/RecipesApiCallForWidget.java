package app.cooking_partner.com.cookingpartner.api;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

import app.cooking_partner.com.cookingpartner.Api;
import app.cooking_partner.com.cookingpartner.interfaces.RecipeApiResponseListener;
import app.cooking_partner.com.cookingpartner.model.Recipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class RecipesApiCallForWidget {
	RecipeApiResponseListener mRecipeApiResponseListener;
	Context mContext;
	AppWidgetManager mAppWidgetManager;
	int[] mAppWidgetIds;
	
	public RecipesApiCallForWidget(RecipeApiResponseListener recipeApiResponseListener, Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		mRecipeApiResponseListener = recipeApiResponseListener;
		mContext = context;
		mAppWidgetManager = appWidgetManager;
		mAppWidgetIds = appWidgetIds;
	}
	
	public Context getmContext() {
		return mContext;
	}
	
	public AppWidgetManager getmAppWidgetManager() {
		return mAppWidgetManager;
	}
	
	public int[] getmAppWidgetIds() {
		return mAppWidgetIds;
	}
	
	public void execute() {
		
		//creating retrofit object
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(Api.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		
		//creating api interface
		Api api = retrofit.create(Api.class);
		
		
		Call<List<Recipe>> recipesCall = api.getRecipes();
		recipesCall.enqueue(new Callback<List<Recipe>>() {
			@Override
			public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
				List<Recipe> recipes = response.body();
				for (Recipe recipe : recipes) {
					Log.e(TAG, "Recipe name: " + recipe.getName());
				}
				mRecipeApiResponseListener.onResponseReceived(recipes);
			}
			
			@Override
			public void onFailure(Call<List<Recipe>> call, Throwable t) {
				mRecipeApiResponseListener.onResponseFailed(t);
				Log.e(TAG, "Failed to retrieve recipes with error: " + t.getMessage());
			}
		});
	}
}
