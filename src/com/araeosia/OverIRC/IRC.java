package com.araeosia.OverIRC;

import java.util.ArrayList;

public class IRC {

	private ArrayList<Object> classes = new ArrayList<Object>();
	
	public IRC(){
		classes.add(this);
	}
	/**
	 * Send a message to the specified channel
	 * 
	 * @param channel
	 * @param message
	 */
	public static void sendMessage(String channel, String message) {
		OverIRC.sendIRCMessage(channel, message);
	}

	/**
	 * Send a message to all channels
	 * 
	 * @param message
	 */
	public static void sendMessage(String message) {
		OverIRC.sendIRCMessage("all", message);
	}
	
	/**
	 * Send a raw message to the IRC server
	 * @param message
	 */
	public static void sendRawMessage(String message){
		OverIRC.sendRawIRCMessage(message);
	}

	/**
	 * Called when a message is received from the server
	 * 
	 * @param sender
	 * @param message
	 */
	public void messageRecieved(String sender, String message) {
	}
}
