package it.mobimentum.dualsimwidget;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DualSimActivity extends Activity {

	private static final String TAG = DualSimActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.NetworkManagement"));
        try {
        	startActivity(intent);
        }
        catch (Exception e) {
        	Log.e(TAG, e.getClass().getName(), e);
        	Toast.makeText(this, e.getClass().getSimpleName()+": "+e.getMessage(), Toast.LENGTH_LONG).show();;
        }

        finish();
	}
}
