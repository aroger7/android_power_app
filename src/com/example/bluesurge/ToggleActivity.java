package com.example.bluesurge;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import com.example.bluesurge.Enumerations;
import com.example.bluesurge.Enumerations.packetType;

import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class ToggleActivity extends Activity {
	
	BluetoothSocket BTsock;
	OutputStream output;
	InputStream input;
	String username;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toggle);
		// Show the Up button in the action bar.
		setupActionBar();
		Button toggle_button = (Button) findViewById(R.id.circuit_toggle_button);
		toggle_button.setClickable(false);
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
		BluetoothDevice remoteBT = toggleIntent.getParcelableExtra("REMOTE_BT");
    	UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    	BluetoothSocket BTsocket = null;
    	try {
			BTsocket = remoteBT.createRfcommSocketToServiceRecord(uuid);
			BTsocket.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("MainActivity", "connect failed");
			return;
		}
		Button connect_button = (Button) view;
		connect_button.setClickable(false);
		Button toggle_button = (Button) findViewById(R.id.circuit_toggle_button);
		toggle_button.setClickable(true);
		EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
		EditText passwordInput = (EditText) findViewById(R.id.passwordInput);
		username = usernameInput.getText().toString();
		password = passwordInput.getText().toString();
		BTsock = BTsocket;
		try {
			output = BTsocket.getOutputStream();
			input = BTsocket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		
	}
	
	public void toggle_button_click(View view) {
		ToggleButton toggleButton = (ToggleButton) view;
		Packet togglePacket = new Packet();
		
		if( toggleButton.isChecked() ) {
			togglePacket.setPacketType(packetType.ENABLE);
		} else {
			togglePacket.setPacketType(packetType.DISABLE);
		}
		togglePacket.setCredentials(username, password);
		try {
			output.write(togglePacket.getPacketData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
