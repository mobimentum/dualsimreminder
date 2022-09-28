package it.mauriziopinotti.dualsimwidget.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import it.mauriziopinotti.dualsimwidget.DualSimPhone;
import it.mauriziopinotti.dualsimwidget.R;

public class DualSimAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            final Intent intent = DualSimPhone.getDualSimSettingsIntent();
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dual_sim_appwidget);
            views.setOnClickPendingIntent(R.id.shortcut, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
