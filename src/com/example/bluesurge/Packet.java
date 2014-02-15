package com.example.bluesurge;
import android.util.Log;

import com.example.bluesurge.Enumerations.packetType;

public class Packet {
	private byte packetTypes;
	private String username;
	private String password;
	private String userToAdd;
	private String passwordToAdd;
	
	public void setPacketType(packetType type) {
		byte packetTypeByte = (byte) 0;
		switch(type) {
			case ADD_USER: 
				packetTypes = (byte) 128;
				break;
			case DELETE_USER:
				packetTypes = (byte) 64;
				break;
			case CHANGE_PASS:
				packetTypes = (byte) 32;
				break;
			case ENABLE:
				packetTypes = (byte) 16;
				break;
			case DISABLE:
				packetTypes = (byte) 8;
				break;
			default:
				
		}
		
		//packetTypes = packetTypeByte;
	}
	
	public void setCredentials(String user, String pass) {
		username = user;
		password = pass;
	}
	
	public void setUserToAdd(String user, String pass) {
		userToAdd = user;
		passwordToAdd = pass;
	}
	
	public byte[] getPacketData() {
		byte[] packetData = new byte[101];
		packetData[0] = packetTypes;
		byte[] usernameAsByte = username.getBytes();
		//Log.e("Packet", "usernameAsByte is " + usernameAsByte.length);
		byte[] passwordAsByte = password.getBytes();
		//Log.e("Packet", "passwordAsByte is " + passwordAsByte.length);
		byte[] addUserAsByte = userToAdd.getBytes();
		//Log.e("Packet", "addUserAsByte is " + addUserAsByte.length);
		byte[] addPassAsByte = passwordToAdd.getBytes();
		//Log.e("Packet", "addPassAsByte is " + addPassAsByte.length);
		for(int i = 1; i < 26; i++) {
			if( i <= usernameAsByte.length) {
				//Log.i("Packet", "Username[" + (i-1) + "] =" + usernameAsByte[i-1]);
				packetData[i] = usernameAsByte[i-1];
			} else {				
				//Log.i("Packet", "Username[" + (i-1) + "] = 0");
				packetData[i] = (byte) 0;
			}
			if( i <= passwordAsByte.length) {
				//Log.i("Packet", "Password[" + (i-1) + "] =" + passwordAsByte[i-1]);
				packetData[i+25] = passwordAsByte[i-1];
			} else {
				//Log.i("Packet", "Password[" + (i-1) + "] = 0");
				packetData[i+25] = (byte) 0;
			}
			if( i <= addUserAsByte.length) {
				//Log.i("Packet", "AddUser[" + (i-1) + "] =" + addUserAsByte[i-1]);
				packetData[i+50] = addUserAsByte[i-1];
			} else {
				//Log.i("Packet", "AddUser[" + (i-1) + "] = 0");
				packetData[i+50] = (byte) 0;
			}
			if( i <= addPassAsByte.length) {
				//Log.i("Packet", "AddPass[" + (i-1) + "] =" + addPassAsByte[i-1]);
				packetData[i+75] = addPassAsByte[i-1];
			} else {
				//Log.i("Packet", "AddPass[" + (i-1) + "] = 0");
				packetData[i+75] = (byte) 0;
			}
		}
		return packetData;
	}
}
