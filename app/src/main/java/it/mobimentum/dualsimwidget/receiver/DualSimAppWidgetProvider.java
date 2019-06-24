package it.mobimentum.dualsimwidget.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import it.mobimentum.dualsimwidget.DualSimPhone;
import it.mobimentum.dualsimwidget.R;

public class DualSimAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			final Intent intent = DualSimPhone.getDualSimSettingsIntent();
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dual_sim_appwidget);
			views.setOnClickPendingIntent(R.id.shortcut, pendingIntent);

			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
