package com.araeosia.OverIRC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

	static OverIRCBot bot;
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
		bot.disconnect();
	}

	/**
	 * 
	 */
	public static void sendIRCMessage(String channel, String message){
		if(channel.equalsIgnoreCase("all")) bot.sendMessage(message);
		else bot.sendMessage(channel, message);
	}
	
	/**
	 * 
	 * @param message
	 */
	public static void sendRawIRCMessage(String message){
		bot.sendRawLine(message);
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
		IRCMessageRecieveEvent event = new IRCMessageRecieveEvent(message, sender);
		plugin.getServer().getPluginManager().callEvent(event);
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

class Config {

	/**
	 * 
	 */
	public static void loadConfiguration(OverIRC plugin){
		boolean configIsCurrentVersion = plugin.getConfig().getDouble("OverIRC.technical.version")==0.1;
		if(!configIsCurrentVersion){
			plugin.getConfig().set("OverIRC.network.host", plugin.getConfig().get("OverIRC.network.host", "irc.esper.net"));
			plugin.getConfig().set("OverIRC.network.port", plugin.getConfig().get("OverIRC.network.port", 6667));
			plugin.getConfig().set("OverIRC.network.password", plugin.getConfig().get("OverIRC.network.password", ""));
			plugin.getConfig().set("OverIRC.account.username", plugin.getConfig().get("OverIRC.account.username", "MCChatLink"));
			plugin.getConfig().set("OverIRC.account.nick", plugin.getConfig().get("OverIRC.account.nick", "MCChatLink"));
			plugin.getConfig().set("OverIRC.account.identify", plugin.getConfig().get("OverIRC.account.identify", ""));
			
			ArrayList<String> channels = new ArrayList<String>();
			channels.add("#exampleChannel");
			
			plugin.getConfig().set("OverIRC.channels", plugin.getConfig().get("OverIRC.channels", channels));
			plugin.saveConfig();
		}
		plugin.host = plugin.getConfig().getString("OverIRC.network.host");
		plugin.port = plugin.getConfig().getInt("OverIRC.network.port");
		plugin.password = plugin.getConfig().getString("OverIRC.network.password");
		plugin.nick = plugin.getConfig().getString("OverIRC.account.nick");
		plugin.channels = plugin.getConfig().getStringList("OverIRC.channels");
		plugin.identify = plugin.getConfig().getString("OverIRC.account.identify");
	}
}
