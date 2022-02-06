package sixpack.events.player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import sixpack.events.events;




public class configUser {

	private events plugin = events.getInstance();
	
	// -----------------------------------
	//		Data Config
	// -----------------------------------
	
	// Files & Configs 
	
	public FileConfiguration DataCFG;
	public File DataFile;

	
	// creating setup
	
	
	
	public void setupData(UUID uuid) {
		
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		DataFile = new File(plugin.getDataFolder() + File.separator + "userData", uuid + ".yml");
		
		if (!DataFile.exists()) {
			try {
				DataFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Events] Data.yml file has been created for " + uuid);	
				DataCFG = YamlConfiguration.loadConfiguration(DataFile);
			

			}catch(IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Events] Data.yml file has been created for " + uuid);
			}
		}
		
		DataCFG = YamlConfiguration.loadConfiguration(DataFile);
		
		
	}
	
	

	
	public FileConfiguration getData(UUID uuid) {
		return DataCFG;
	}
	
	public void saveData(UUID uuid) {
		try{
			DataCFG.save(DataFile);
		}catch(IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Events] Could not save Data.yml file");
		}
	}
	
	public void reloadData() {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[Events] Data.yml file has been reloaded");
		DataCFG = YamlConfiguration.loadConfiguration(DataFile);
	}
	
		// -----------------------------------
		//		end of Data Config
		// -----------------------------------
	
	
	
	
	
	public void getDefaultData(Player p) {
		String name = p.getName();
		int defaultTokens = plugin.getConfig().getInt("Player.defaultTokens");

		

		
		getData(p.getUniqueId()).set("Name", name);
		getData(p.getUniqueId()).set("Tokens", 0);
		saveData(p.getUniqueId());
	}
	
	
	
	
}
