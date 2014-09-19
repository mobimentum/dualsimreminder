package it.mobimentum.dualsimwidget;

import static it.mobimentum.dualsimwidget.AlarmConfigActivity.PREF_ENABLED;
import static it.mobimentum.dualsimwidget.AlarmConfigActivity.PREF_TIME1;
import static it.mobimentum.dualsimwidget.AlarmConfigActivity.PREF_TIME2;
import static it.mobimentum.dualsimwidget.AlarmConfigActivity.setAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	
	static final String TAG = BootReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive()");

		// Re-schedula alarms
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean enabled = prefs.getBoolean(PREF_ENABLED, false);
		if (enabled) {
			String[] time1 = prefs.getString(PREF_TIME1, "9:00").split(":");
			String[] time2 = prefs.getString(PREF_TIME2, "18:00").split(":");
			setAlarm(context, 1, Integer.parseInt(time1[0]), Integer.parseInt(time1[1]));
			setAlarm(context, 2, Integer.parseInt(time2[0]), Integer.parseInt(time2[1]));
		}
	}
}
