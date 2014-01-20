package com.example.bluesurge;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.widget.Button;

public class BTButton extends Button {

	BluetoothDevice remote;
	
	public BTButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setRemoteBT(BluetoothDevice device) {
		remote = device;
	}
	
	public BluetoothDevice getRemoteBT() {
		return remote;
	}
}
