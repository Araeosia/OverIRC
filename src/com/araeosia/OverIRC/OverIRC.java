package com.araeosia.OverIRC;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;

/**
 * 
 * @author Daniel, Bruce
 *
 */
public class OverIRC extends JavaPlugin implements Listener {

	OverIRCBot bot;
	Logger log;
	
	//CONFIG
	public String host;
	public int port;
	public String password;
	public String nick;
	public List<String> channels;
	public String identify;
	//CONFIG
	/**
	 * 
	 */
	@Override
	public void onEnable(){
		Config.loadConfiguration(this);
		log = Logger.getLogger("Minecraft");
		log.info("OverIRC API Enabled");
		
		log.info("Enabling IRC Bot...");
		bot = new OverIRCBot(this);
		
		bot.setVerbose(false);
		
		try {
			bot.connect(host, port, password);
		} catch (IOException | IrcException e) {
			e.printStackTrace();
			log.info("An Error Occured. Disabling plugin...");
			this.setEnabled(false);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void onDisable(){
		log.info("OverIRC API Disabled");
	}

	/**
	 * 
	 */
	public void sendIRCMessage(String channel, String message){
		if(channel.equalsIgnoreCase("all")) bot.sendMessage(message);
		else bot.sendMessage(channel, message);
	}
	

}

/**
 * 
 * @author Daniel
 *
 */
class OverIRCBot extends PircBot {
	
	private OverIRC plugin;
	/**
	 * 
	 */
	public OverIRCBot(OverIRC plugin){
		this.plugin = plugin;
		this.setName(plugin.nick);
	}
	
	/**
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		for(String s : this.getChannels()){
			sendMessage(s, message);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message){
		IRCMessageRecieveEvent event = new IRCMessageRecieveEvent(sender, message);
		Bukkit.getServer().getPluginManager().callEvent(event);
		IRC irc = new IRC();
		irc.messageRecieved(sender, message);
	}
	

	public void onConnect(){
		if(!plugin.identify.isEmpty() || !(plugin.identify == "") || !(plugin.identify == null) ){
			this.identify(plugin.identify);
		}
		for(String s: plugin.channels){
	 		this.joinChannel(s);
		}
	}
	
}