package com.leadinsource.prudentcook.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.data.FavoriteManager;
import com.leadinsource.prudentcook.recipeactivity.RecipeActivity;

import timber.log.Timber;

import static com.leadinsource.prudentcook.recipeactivity.RecipeActivity.EXTRA_INGREDIENTS;
import static com.leadinsource.prudentcook.recipeactivity.RecipeActivity.EXTRA_RECIPE_NAME;
import static com.leadinsource.prudentcook.recipeactivity.RecipeActivity.WIDGET_ACTION;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link RecipeWidgetConfigureActivity RecipeWidgetConfigureActivity}
 */
public class RecipeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Timber.d("Updating widget no %s", appWidgetId);
        CharSequence recipeName = RecipeWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        if(recipeName.length()==0) return;
        String ingredients = FavoriteManager.getIngredients(context, recipeName.toString());
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.tvWidgetRecipeName, recipeName);
        views.setTextViewText(R.id.tvWidgetIngredients, ingredients);
        Intent intent = new Intent(context, RecipeActivity.class);
        intent.putExtra(EXTRA_RECIPE_NAME, recipeName);
        intent.putExtra(EXTRA_INGREDIENTS, ingredients);
        intent.setAction(WIDGET_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            RecipeWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
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
}

