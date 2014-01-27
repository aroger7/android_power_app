package com.example.bluesurge;
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
		
		packetTypes = packetTypeByte;
	}
	
	public void setCredentials(String user, String pass) {
		username = user;
		password = pass;
	}
	
	public void setUserToAdd(String user, String pass) {
		userToAdd = user;
		passwordToAdd = password;
	}
	
	public byte[] getPacketData() {
		byte[] packetData = new byte[101];
		//packetData
		return packetData;
	}
}
