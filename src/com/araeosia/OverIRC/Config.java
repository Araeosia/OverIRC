package com.araeosia.OverIRC;

import java.util.ArrayList;

import com.araeosia.OverIRC.OverIRC;

public class Config {

	/**
	 * 
	 */
	public static void loadConfiguration(OverIRC plugin){
		boolean configIsCurrentVersion = plugin.getConfig().getDouble("OverIRC.technical.version")==0.1;
		if(!configIsCurrentVersion){
			plugin.getConfig().set("OverIRC.network.host", "irc.esper.net");
			plugin.getConfig().set("OverIRC.network.port", 6667);
			plugin.getConfig().set("OverIRC.network.password", "");
			plugin.getConfig().set("OverIRC.account.username", "MCChatLink");
			plugin.getConfig().set("OverIRC.account.nick", "MCChatLink");
			plugin.getConfig().set("OverIRC.account.identify", "");
			plugin.getConfig().set("OverIRC.channels", new ArrayList<String>());
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
