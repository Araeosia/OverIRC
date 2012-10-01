package com.araeosia.OverIRC;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class IRCMessageRecieveEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String message;
	private String sender;
	
	/**
	 * 
	 * @param message
	 * @param sender
	 */
	protected IRCMessageRecieveEvent(String message, String sender){
		this.message = message;
		this.sender = sender;
	}
	
	/**
	 * 
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	/**
	 * Get the Message revieved
	 * @return The message recieved
	 */
	public String getMessage(){
		return message;
	}
	
	/**
	 * Get the sender of the message
	 * @return the sender of the message
	 */
	public String getSender(){
		return sender;
	}
}
