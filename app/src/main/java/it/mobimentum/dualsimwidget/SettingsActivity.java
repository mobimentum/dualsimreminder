package it.mobimentum.dualsimwidget;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Locale;

import it.mobimentum.dualsimwidget.preferences.TimePreference;
import it.mobimentum.dualsimwidget.receiver.AlarmReceiver;

public class SettingsActivity extends AppCompatActivity {

	private static final String TAG = SettingsActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new MyPreferenceFragment())
				.commit();
	}

	public static class MyPreferenceFragment extends PreferenceFragment
			implements Preference.OnPreferenceChangeListener {

		@SuppressWarnings("squid:S1659")
		private TimePreference startTimePref, endTimePref;

		private SharedPreferences prefs;

		@Override
		public void onCreate(final Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			addPreferencesFromResource(R.xml.preferences);

			prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

			// Device
			Preference devicePref = findPreference(getString(R.string.pref_key_device));
			devicePref.setSummary(Build.BRAND + " " + Build.MODEL);
			final Intent intent = DualSimPhone.getDualSimSettingsIntent();
			Log.i(TAG, "model=" + Build.MODEL + ", brand=" + Build.BRAND + ", intent=" + intent);
			if (!DualSimPhone.isPhoneSupported()) devicePref.setIcon(R.drawable.ic_action_warning);

			// Debug test
			devicePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					try {
						startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
					}
					catch (ActivityNotFoundException e) {
						Log.w(TAG, e.getMessage(), e);

						startActivity(DualSimPhone.DEFAULT_SETTINGS_INTENT);
					}

					return true;
				}
			});

			// Working hours
			startTimePref = (TimePreference) findPreference(getString(R.string.pref_key_alarm_start));
			startTimePref.setOnPreferenceChangeListener(this);
			endTimePref = (TimePreference) findPreference(getString(R.string.pref_key_alarm_end));
			endTimePref.setOnPreferenceChangeListener(this);
			updateWorkingHours();
		}

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// Save working hours
			if (preference.equals(startTimePref) || preference.equals(endTimePref)) {
				prefs.edit().putString(preference.getKey(), (String) newValue).commit();
			}

			updateWorkingHours();

			return false;
		}

		private void updateWorkingHours() {
			startTimePref.setSummary(String.format(Locale.getDefault(), getString(R.string.pref_alarm_start_summary),
					TimePreference.formatTime(prefs.getString(getString(R.string.pref_key_alarm_start),
							getString(R.string.pref_alarm_start_default)))));
			endTimePref.setSummary(String.format(Locale.getDefault(), getString(R.string.pref_alarm_end_summary),
					TimePreference.formatTime(prefs.getString(getString(R.string.pref_key_alarm_end),
							getString(R.string.pref_alarm_end_default)))));

			// Reschedule alarms
			AlarmReceiver.rescheduleNotifications(getActivity());
		}
	}
}
