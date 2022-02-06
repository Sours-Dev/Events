package sixpack.events.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import sixpack.events.events;




public class configLang {
	private events plugin = events.getInstance();
	
	// -----------------------------------
	//		Lang Config
	// -----------------------------------
	
	// Files & Configs 
	
	public FileConfiguration LangCFG;
	public File LangFile;

	
	// creating setup
	/**
	 *  API: %tokens%
	 */
	
	private void defaultLang() {
		LangCFG.set("no-perms", "&cNo Permissions");
		LangCFG.set("invalidInput", "&cInput is invalid");
		LangCFG.set("errorMSG", "&cERROR: &f%MSG%");
		List<String> eventsMsg = new ArrayList<>();
		
		eventsMsg.add("&8&m----------------------------------");
		eventsMsg.add(" &6You have &f%tokens% &6event tokens.");
		eventsMsg.add(" &6/events start &fTo start an event");
		eventsMsg.add(" &6/events leave (event) &fTo leave event");
		eventsMsg.add(" &6/events join (event) &fTo join event");
		eventsMsg.add("&8&m----------------------------------");
		
		LangCFG.set("events.msg", eventsMsg);
		
		
	List<String> eventsAdminMsg = new ArrayList<>();
		
	eventsAdminMsg.add("&8&m----------------------------------");
	eventsAdminMsg.add(" &6&lEvents Admin");
	eventsAdminMsg.add(" &6&l* &6/eventsadmin forcestop");
	eventsAdminMsg.add(" &6&l* &6/eventsadmin setspawn &fSet spawn");
	eventsAdminMsg.add(" &6&l* &6/eventsadmin setspawn (event) &fSet spawn for event");
	eventsAdminMsg.add(" &6&l* &6/eventsadmin setTokens (player) (amount) &fSet players tokens amount");
	eventsAdminMsg.add(" &6&l* &6/eventsadmin removeTokens (player) (amount) &fRemove players tokens");
	eventsAdminMsg.add(" &6&l* &6/eventsadmin addTokens (player) (amount) &fAdd tokens to player");
	eventsAdminMsg.add(" &6&l* &6/eventsadmin setsumospawn (1/2) &fset spawnpoint for player 1 and 2");
	eventsAdminMsg.add(" &6&l* &6/eventsadmin setbracketsspawn (1/2) &fset spawnpoint for player 1 and 2");
	eventsAdminMsg.add(" &6&l* &6/eventsadmin setredroverspawn &fset spawnpoint for redrover");
	eventsAdminMsg.add("&8&m----------------------------------");
		
		LangCFG.set("eventsadmin.msg.help", eventsAdminMsg);
		LangCFG.set("eventsadmin.msg.noModes",  "&cThere is currently no event going on");
		LangCFG.set("eventsadmin.msg.broadcastCancel", "&6Event has been cancelled");
		LangCFG.set("eventsadmin.msg.stoppedModes", "&cYou have stopped all event.");
		LangCFG.set("mode.messages.start.countdown", "&6%mode% is starting &7%seconds% &6remaining.");
		LangCFG.set("mode.messages.start.started", "&6%mode% has started!");
		LangCFG.set("mode.messages.start.enabled", "&6%mode% has been started!");
		LangCFG.set("mode.messages.alreadyStarted", "&cThere is a event already started");
		LangCFG.set("mode.messages.join.alreadystarted", "&6The event has already started");
		LangCFG.set("mode.messages.alreadyIn", "&6You are already in the event");
		LangCFG.set("mode.messages.notIn", "&6You are not in an event");
		LangCFG.set("mode.messages.joined", "&6You have joined the event");
		LangCFG.set("mode.messages.left", "&6You have left the event");
		LangCFG.set("mode.messages.PlayerElm", "&6&l%player% &feliminated from the event!");
		LangCFG.set("mode.messages.PlayerWinner", "&6&l%player% &f&lhas won the event!!!!");
		LangCFG.set("mode.messages.NotEnough", "&fYou dont have enough tokens to start this event");
		saveLang();
	}
	
	
	public void setupLang() {
		
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		LangFile = new File(plugin.getDataFolder(), "Langs.yml");
		
		if (!LangFile.exists()) {
			try {
				LangFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[Events] Lang.yml file has been created");	
				LangCFG = YamlConfiguration.loadConfiguration(LangFile);
				defaultLang();
				
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Events] Could not create Lang.yml file");
			}
		}
		
		LangCFG = YamlConfiguration.loadConfiguration(LangFile);
		
		
	}
	
	

	
	public FileConfiguration getLang() {
		return LangCFG;
	}
	
	public void saveLang() {
		try{
			LangCFG.save(LangFile);
		}catch(IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Events] Could not save Lang.yml file");
		}
	}
	
	// Broken 
	public void reloadLang() {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[Events] Lang.yml file has been reloaded");
		LangCFG = YamlConfiguration.loadConfiguration(LangFile);
	}
	
	
}
