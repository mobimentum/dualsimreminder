package it.mobimentum.dualsimwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class DualSimAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.NetworkManagement"));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dual_sim_appwidget);
            views.setOnClickPendingIntent(R.id.shortcut, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
	}
}
