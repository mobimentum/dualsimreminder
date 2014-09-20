package it.mobimentum.dualsimwidget;

import static it.mobimentum.dualsimwidget.AlarmConfigActivity.PREF_WEEKENDS;

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	
	static final String TAG = AlarmReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive()");
		
		// Weekends?
		int weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		boolean excludeWeekends = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PREF_WEEKENDS, true);
		if (excludeWeekends && (weekDay == Calendar.SUNDAY || weekDay == Calendar.SATURDAY)) {
			Log.d(TAG, "Skipping weekend alarms...");
			return;
		}

		// Crea notifica
		Notification.Builder builder =
			    new Notification.Builder(context)
			    .setSmallIcon(R.drawable.ic_settings_sim_card_manager_notif)
			    .setContentTitle(context.getString(R.string.app_name))
			    .setContentText(context.getString(R.string.notification))
			    .setAutoCancel(true);

		// Pending intent
		Intent resultIntent = new Intent(context, DualSimActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(
				context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(resultPendingIntent);
		
		// GO!
		NotificationManager notifMgr =  (NotificationManager) 
				context.getSystemService(Context.NOTIFICATION_SERVICE);
		notifMgr.notify(1, builder.build());
	}
}
