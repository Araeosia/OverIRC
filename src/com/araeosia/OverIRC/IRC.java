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
	public void sendMessage(String channel, String message) {
		OverIRC overIRC = new OverIRC();
		overIRC.sendIRCMessage(channel, message);
	}

	/**
	 * Send a message to all channels
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		OverIRC overIRC = new OverIRC();
		overIRC.sendIRCMessage("all", message);
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
