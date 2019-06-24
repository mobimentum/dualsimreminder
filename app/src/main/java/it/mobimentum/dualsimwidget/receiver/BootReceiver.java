package it.mobimentum.dualsimwidget.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent != null && Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			// Re-schedula alarms
			AlarmReceiver.rescheduleNotifications(context);
		}
	}
}
