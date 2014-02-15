package com.example.bluesurge;

import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BTCommInfo {
	public BluetoothDevice device = null;
	public BluetoothSocket sock = null;
	public OutputStream output = null;
	public InputStream input = null;
}
