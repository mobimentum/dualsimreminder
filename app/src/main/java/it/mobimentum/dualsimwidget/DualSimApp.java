package it.mobimentum.dualsimwidget;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * @author Maurizio Pinotti
 */
public class DualSimApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		if (!BuildConfig.DEBUG) {
			Fabric.with(this, new Crashlytics());
		}
	}
}
