package com.example.bluesurge;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BluetoothComms extends Service {
	public final IBinder mBinder = new LocalBinder();
	private LinkedList<BTCommInfo> infoList = new LinkedList<BTCommInfo>();

	public class LocalBinder extends Binder {
		BluetoothComms getService() {
			return BluetoothComms.this;
	    }
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("BluetoothComms", "Starting BT service");
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}
	
	public int connectToDevice(BluetoothDevice device) {
		BTCommInfo info = new BTCommInfo();
		info.device = device;
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    	try {
			info.sock = info.device.createRfcommSocketToServiceRecord(uuid);
			info.sock.connect();
			info.output = info.sock.getOutputStream();
			info.input = info.sock.getInputStream();
			infoList.add(info);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("MainActivity", "connect failed");
			return 0;
		}
    	return 1;
		
	}
	
	public void sendPacket(BluetoothDevice device, byte[] buffer) {
		Iterator<BTCommInfo> it = infoList.iterator();
		while(it.hasNext()) {
			BTCommInfo info = it.next();
			if(info.device == device) {
				try {
					Log.i("BluetoothComms", "writing packet to output stream of device " + info.device.getAddress());
					info.output.write(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
		Toast.makeText(this, "Couldn't find device " + device.getAddress(), Toast.LENGTH_SHORT).show();
	}
	
	
}