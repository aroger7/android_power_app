package com.example.bluesurge;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.example.bluesurge.Enumerations.packetType;

public class ControlActivity extends Activity {

	BluetoothComms connectedBTService = null;
	BluetoothDevice device = null;
	String username = null;
	String password = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		device = this.getIntent().getParcelableExtra("REMOTE_BT");
		username = this.getIntent().getParcelableExtra("USERNAME");
		password = this.getIntent().getParcelableExtra("PASSWORD");
		ServiceConnection serviceConnect = new ServiceConnection() {

			@Override
			public void onServiceConnected(ComponentName arg0,
					IBinder arg1) {
				// TODO Auto-generated method stub
				Log.e("ControlActivity", "Service connected");
				connectedBTService = ((BluetoothComms.LocalBinder)arg1).getService();
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				Log.e("ControlActivity", "Service disconnected");
				connectedBTService = null;
			}
    		
    	};
    	
    	Intent serviceIntent = new Intent(this, BluetoothComms.class);
		if( bindService(serviceIntent, serviceConnect, 0) == true) {
			Log.e("ControlActivity", "Bound to BT service");
		}
		Switch toggleSwitch = (Switch) findViewById(R.id.toggleSwitch);
		toggleSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        toggleSwitchHandler(buttonView, isChecked);
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.control, menu);
		return true;
	}

	public void toggleSwitchHandler(CompoundButton buttonView, boolean isChecked) {
		Switch toggleSwitch = (Switch)buttonView;
		Packet togglePacket = new Packet();
		
		if( isChecked ) {
			togglePacket.setPacketType(packetType.ENABLE);
		} else {
			togglePacket.setPacketType(packetType.DISABLE);
		}
		togglePacket.setCredentials("admin", "admin");
		togglePacket.setUserToAdd("random_user_1", "random_password_1");
		
		connectedBTService.sendPacket(device, togglePacket.getPacketData());
	}
}