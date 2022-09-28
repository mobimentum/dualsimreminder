package it.mauriziopinotti.dualsimwidget;

import android.app.Application;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

/**
 * @author Maurizio Pinotti
 */
public class DualSimApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG);
    }
}
