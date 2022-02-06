package sixpack.events;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import sixpack.events.player.userEVNT;
import sixpack.events.player.userManager;
import sixpack.events.util.c;
import sixpack.events.util.configData;
import sixpack.events.util.configLang;
import sixpack.events.util.messagesUtil;

import sixpack.events.commands.eventsAdminCMD;
import sixpack.events.commands.eventsCMD;
import sixpack.events.mode.mode;
import sixpack.events.mode.modeEVNT;
import sixpack.events.mode.modeManager;


public class events extends JavaPlugin implements Listener{

	private static events instance;	
	private configLang lang;
	private configData data;
	public HashMap<Player, userManager> userMangerHashMap;
	private modeManager modeManager;
	
	@Override
	public void onEnable() {
		instance = this;

		modeManager = new modeManager();
		cmd();
		config();
		registerListeners();
		this.userMangerHashMap = new HashMap<>();
		this.RegisterListener(this);

		for(Player players : Bukkit.getOnlinePlayers()) {
			userEVNT user = new userEVNT();
			user.setUp(players);
			
		}

	

		
		Bukkit.getConsoleSender().sendMessage(c.f("&c&m--------------------------------------------"));
		Bukkit.getConsoleSender().sendMessage(c.f("          &a&lEvents"));
		Bukkit.getConsoleSender().sendMessage(c.f("          &a&lVersion: " + instance.getDescription().getVersion()));		
		Bukkit.getConsoleSender().sendMessage(c.f(""));
		Bukkit.getConsoleSender().sendMessage(c.f("          &aPlugin Enabled"));
		Bukkit.getConsoleSender().sendMessage(c.f("&c&m--------------------------------------------"));		
		this.saveDefaultConfig();

	}
	
	
	public modeManager getmodeManager() {
		return modeManager;
	}

	
	@Override
	public void onDisable() {
		
		this.saveDefaultConfig();
		config();
		modeEVNT modeEVNT = new modeEVNT();
		modeEVNT.stopModes();
		
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			userEVNT user = new userEVNT();
			user.saveUser(players);
		}
		
		this.userMangerHashMap.clear();

			
		Bukkit.getConsoleSender().sendMessage(c.f("&c&m--------------------------------------------"));
		Bukkit.getConsoleSender().sendMessage(c.f("          &c&lEvents"));
		Bukkit.getConsoleSender().sendMessage(c.f("          &c&lVersion: " + instance.getDescription().getVersion()));		
		Bukkit.getConsoleSender().sendMessage(c.f(""));
		Bukkit.getConsoleSender().sendMessage(c.f("          &cPlugin Disabled"));
		Bukkit.getConsoleSender().sendMessage(c.f("&c&m--------------------------------------------"));						
	}
	
	
	private void cmd() {
		getCommand("events").setExecutor(new eventsCMD());
		getCommand("eventsadmin").setExecutor(new eventsAdminCMD());
	}
	
	public void registerListeners() {
		 PluginManager pm = getServer().getPluginManager();
		 pm.registerEvents(new userEVNT(), this);	
		 pm.registerEvents(new modeEVNT(), this);
	}

	private void RegisterListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
	private void config() {
		lang = new configLang();
		lang.setupLang();
		lang.saveLang();
		data = new configData();
		data.setupData();
		data.saveData();
	}

	public static events getInstance() {
		return instance;
	}

}
