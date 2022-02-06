package sixpack.events.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import sixpack.events.events;
import sixpack.events.mode.mode;




public class configData {
	private events plugin = events.getInstance();
	
	// -----------------------------------
	//		Data Config
	// -----------------------------------
	
	// Files & Configs 
	
	public FileConfiguration DataCFG;
	public File DataFile;

	
	// creating setup
	/**
	 *  API: %tokens%
	 */
	
	private void defaultData() {


		saveData();
	}
	
	
	public void setupData() {
		
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		DataFile = new File(plugin.getDataFolder(), "Datas.yml");
		
		if (!DataFile.exists()) {
			try {
				DataFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[Events] Data.yml file has been created");	
				DataCFG = YamlConfiguration.loadConfiguration(DataFile);
				defaultData();
				
			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Events] Could not create Data.yml file");
			}
		}
		
		DataCFG = YamlConfiguration.loadConfiguration(DataFile);
		
		
	}
	
	

	
	public FileConfiguration getData() {
		return DataCFG;
	}
	
	public void saveData() {
		try{
			DataCFG.save(DataFile);
		}catch(IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Events] Could not save Data.yml file");
		}
	}
	
	// Broken 
	public void reloadData() {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[Events] Data.yml file has been reloaded");
		DataCFG = YamlConfiguration.loadConfiguration(DataFile);
	}
	
	
}
