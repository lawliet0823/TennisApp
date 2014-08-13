package com.example.tennisanalysis;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int IDs = appWidgetIds.length;

		for (int i = 0; i < IDs; i++) {
			int appWidgetId = appWidgetIds[i];
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	public void onDeleted(Context context, int[] appWidgetIds) {

	}

	private void updateAppWidget(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId) {
		// TODO Auto-generated method stub
		CharSequence text;
		text = "my test";

		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.widget);
		views.setTextViewText(R.id.HelloTextView01, text);

		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
}
