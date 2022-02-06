package sixpack.events.player;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import sixpack.events.events;
import sixpack.events.mode.modeEVNT;
import sixpack.events.util.c;
import sixpack.events.util.configLang;
import sixpack.events.util.messagesUtil;



public class userEVNT implements Listener{

	
	
	private configUser configUser = new configUser();
	private events plugin = events.getInstance();
	private configLang lang = new configLang();
	
	public void newUser(Player p) {
		configUser.setupData(p.getUniqueId());	
		configUser.getDefaultData(p);
		configUser.saveData(p.getUniqueId());
	
	}
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		sixpack.events.mode.modeEVNT modeEVNT = new modeEVNT();
		if(modeEVNT.checkInAnyEvents(p)) {
			
			modeEVNT.playerLeaveEvent(p, modeEVNT.getActiceMode());
		}
		
		saveUser(p);
	}
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		setUp(p);
	}
	
	public void saveUser(Player p) {				
		configUser.setupData(p.getUniqueId());	
				
		userManager userManager = userManager = plugin.userMangerHashMap.get(p);
		
		if(plugin.userMangerHashMap.containsKey(p)) {
			configUser.getData(p.getUniqueId()).set("Name", userManager.getName());
			configUser.getData(p.getUniqueId()).set("Tokens", userManager.getTokens());
			
			configUser.saveData(p.getUniqueId());
			plugin.userMangerHashMap.remove(p);
			messagesUtil.consoleMessage(" Saved user '" + p.getName() + "'");
		}else { messagesUtil.debugConsole(" Could not save user " + p.getName());}
	}
	
	public void resetUser(Player p) {
		configUser.setupData(p.getUniqueId());	
		for(String key : configUser.getData(p.getUniqueId()).getKeys(false)){
			configUser.getData(p.getUniqueId()).set(key, null);
			}
		configUser.getDefaultData(p);
		configUser.saveData(p.getUniqueId());
		
		setUp(p);
	}
	
	public boolean hasPlayer(Player p) {
		configUser.setupData(p.getUniqueId());
		if(!configUser.getData(p.getUniqueId()).contains("Name")) {
			return false;
		}
		return true;
	}
	
	private void changeNameFix(Player p) {
		configUser.setupData(p.getUniqueId());
		if(!configUser.getData(p.getUniqueId()).getString("Name").equals(p.getName())) {
			configUser.getData(p.getUniqueId()).set("Name", p.getName());
			configUser.saveData(p.getUniqueId());
		}
	}
	
	
	 public void setUp(Player p) {
		    if (!hasPlayer(p)) {
		      newUser(p);
		    } else {
		      changeNameFix(p);
		      if (this.plugin.getConfig().getBoolean("Options.joinMessage.enabled")) {
		        String msg = this.plugin.getConfig().getString("Options.joinMessage.message");
		        msg = msg.replaceAll("%player%", p.getDisplayName());
		        p.sendMessage(c.f(msg));
		      } 
		    } 
		    this.configUser.setupData(p.getUniqueId());
		    int tokens = this.configUser.getData(p.getUniqueId()).getInt("Tokens");
		    this.plugin.userMangerHashMap.put(p, new userManager(p.getName(), tokens, p.getInventory().getContents(), p.getInventory().getArmorContents()));
		    messagesUtil.consoleMessage(" Player '" + p.getName() + "' has been updated!");
		    
	 	}
		  

	 public void addTokens(Player p, int tokens) {
		 userManager userMan = plugin.userMangerHashMap.get(p);
		 userMan.setTokens(userMan.getTokens() + tokens);
	 }
	 
	 public void removeTokens(Player p, int tokens) {
		 userManager userMan = plugin.userMangerHashMap.get(p);
		 int newTokens = userMan.getTokens() - tokens;
		 if(newTokens < 0) {
			 newTokens = 0;
		 }
		 userMan.setTokens(newTokens);
	 }
	 
	 public void setTokens(Player p, int tokens) {
		 userManager userMan = plugin.userMangerHashMap.get(p);
		 userMan.setTokens(tokens);
	 }
	 
	 public void resetTokens(Player p) {
		 userManager userMan = plugin.userMangerHashMap.get(p);
		 int defaultTokens = plugin.getConfig().getInt("Player.defaultTokens");
		 userMan.setTokens(defaultTokens);
	 }
}
