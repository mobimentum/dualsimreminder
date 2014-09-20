package it.mobimentum.dualsimwidget;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TimePicker;

public class AlarmConfigActivity extends Activity implements OnClickListener {

	static final String PREF_TIME1 = "TIME1", PREF_TIME2 = "TIME2", 
			PREF_ENABLED = "ENABLED", PREF_WEEKENDS = "WEEKENDS";

	private static final String TAG = AlarmConfigActivity.class.getSimpleName();

	private TimePicker mPicker1, mPicker2;
	private CheckBox mEnabled, mWeekends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_alarmconfig);
		
		findViewById(R.id.saveBtn).setOnClickListener(this);
		mEnabled = (CheckBox) findViewById(R.id.enabledCheck);
		mWeekends = (CheckBox) findViewById(R.id.weekendsCheck);
		mPicker1 = (TimePicker) findViewById(R.id.timePicker1);
		mPicker2 = (TimePicker) findViewById(R.id.timePicker2);

		mPicker1.setIs24HourView(DateFormat.is24HourFormat(this));
		mPicker2.setIs24HourView(DateFormat.is24HourFormat(this));

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolean enabled = prefs.getBoolean(PREF_ENABLED, false);
		mEnabled.setChecked(enabled);
		mWeekends.setChecked(prefs.getBoolean(PREF_WEEKENDS, true));
		mPicker1.setEnabled(enabled); mPicker2.setEnabled(enabled);
		mEnabled.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mPicker1.setEnabled(isChecked); mPicker2.setEnabled(isChecked);
			}
		});
		
		String[] time1 = prefs.getString(PREF_TIME1, "9:00").split(":");
		String[] time2 = prefs.getString(PREF_TIME2, "18:00").split(":");
		mPicker1.setCurrentHour(Integer.parseInt(time1[0]));
		mPicker1.setCurrentMinute(Integer.parseInt(time1[1]));
		mPicker2.setCurrentHour(Integer.parseInt(time2[0]));
		mPicker2.setCurrentMinute(Integer.parseInt(time2[1]));
	}

	@Override
	public void onClick(View v) {
		PreferenceManager.getDefaultSharedPreferences(this).edit()
				.putBoolean(PREF_ENABLED, mEnabled.isChecked())
				.putBoolean(PREF_WEEKENDS, mWeekends.isChecked())
				.putString(PREF_TIME1, mPicker1.getCurrentHour()+":"+mPicker1.getCurrentMinute())
				.putString(PREF_TIME2, mPicker2.getCurrentHour()+":"+mPicker2.getCurrentMinute())
				.commit();
		
		setAlarm(this, 1, mPicker1.getCurrentHour(), mPicker1.getCurrentMinute());
		setAlarm(this, 2, mPicker2.getCurrentHour(), mPicker2.getCurrentMinute());
		
		finish();
	}
	
	public static void setAlarm(Context context, int alarmNum, int hours, int mins) {
		AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, alarmNum/*no cache*/, intent, 0);
		
		Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, mins);
        calendar.set(Calendar.SECOND, 0);

        // XXX DEBUG
//		calendar = Calendar.getInstance(); 
//		calendar.add(Calendar.SECOND, 5);
        
        long diff = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        Log.d(TAG, "Alarm scheduled at "+calendar.getTime()+" in "+diff+"''");
    	
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 
        		AlarmManager.INTERVAL_DAY, alarmIntent);
	}
}
