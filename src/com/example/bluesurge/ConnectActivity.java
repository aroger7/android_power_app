package com.example.bluesurge;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bluesurge.Enumerations.packetType;

public class ConnectActivity extends Activity {
	
	BluetoothComms connectedBTService = null;
	String username;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toggle);
		// Show the Up button in the action bar.
		setupActionBar();
		ServiceConnection serviceConnect = new ServiceConnection() {

			@Override
			public void onServiceConnected(ComponentName arg0,
					IBinder arg1) {
				// TODO Auto-generated method stub
				Log.e("ConnectActivity", "Service connected");
				connectedBTService = ((BluetoothComms.LocalBinder)arg1).getService();
				if(connectedBTService == null) {
					Log.e("ConnectActivity", "Service is null");
				}
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				Log.e("ConnectActivity", "Service disconnected");
				connectedBTService = null;
			}
    		
    	};
    	
    	Intent serviceIntent = new Intent(this, BluetoothComms.class);
		if( bindService(serviceIntent, serviceConnect, 0) == true) {
			Log.e("ConnectActivity", "Bound to BT service");
		}
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.toggle, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void connect_click(View view) {
		Intent toggleIntent = getIntent();
		BluetoothDevice remoteBT = (BluetoothDevice) toggleIntent.getParcelableExtra("REMOTE_BT");
		Button connect_button = (Button) view;
		connect_button.setClickable(false);
		EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
		EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
		username = usernameInput.getText().toString();
		password = passwordInput.getText().toString();
		
		Intent controlIntent = new Intent(connect_button.getContext(), ControlActivity.class);
		controlIntent.putExtra("USERNAME", username);
		controlIntent.putExtra("PASSWORD", password);
		controlIntent.putExtra("REMOTE_BT", remoteBT);
		
		Log.i("ConnectActivity", "starting ControlActivity for device " + remoteBT.getAddress());
		startActivity(controlIntent);
		
	}

}