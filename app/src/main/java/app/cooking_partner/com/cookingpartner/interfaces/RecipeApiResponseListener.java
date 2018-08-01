package app.cooking_partner.com.cookingpartner.interfaces;

import java.util.List;

import app.cooking_partner.com.cookingpartner.model.Recipe;

public interface RecipeApiResponseListener {
	void onResponseReceived(List<Recipe> recipes);
	
	void onResponseFailed(Throwable T);
}
