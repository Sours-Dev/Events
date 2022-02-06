package sixpack.events.mode;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;


import sixpack.events.events;
import sixpack.events.mode.mode;
import sixpack.events.mode.modes.sumo;
import sixpack.events.player.userEVNT;
import sixpack.events.player.userManager;
import sixpack.events.util.c;
import sixpack.events.util.configData;
import sixpack.events.util.messagesUtil;

public class modeEVNT implements Listener{

	
	private events plugin = events.getInstance();
	private configData data = new configData();
	private int timer;
	public static List<Block> minedBlocks;

	
	
	
	public void startTimer(Player p, String id) {
		
	
		
		for(Entry<String, mode> mode : plugin.getmodeManager().modes.entrySet()) {
			if(mode.getValue().getStarted() == true) {
				messagesUtil.lineMessage(p, "mode.messages.alreadyStarted");
				return;
			}		
		}		
		mode mode = plugin.getmodeManager().getMode(id);
		
		
		if(mode.getEnabled() == false) {
			messagesUtil.playerErrorMSG(p, "Event has not been enabled");
			return;
		}
		cooldown.clear();
		cooldownTask.clear();
		mode.setStarted(true);
		mode.setJoinable(true);
		
		timer =  Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
		        
	           int Counterdown = 30;
	           
	           public void run() {
		        if(mode.getStarted() == false) {
			  	   Bukkit.getScheduler().cancelTask(timer);
			     }
		        
		        if((Counterdown % 5) == 0 || Counterdown <= 5) {
		        	  messagesUtil.modeStarting(mode, Counterdown);	   
		        }
		             	  
	        	  Counterdown--;
	           if (Counterdown == 0) {
	           	Bukkit.getScheduler().cancelTask(timer);
	        	  messagesUtil.modeStarting(mode, Counterdown);
	        	  mode.setJoinable(false);
	        	  if(mode.getPlayers().size() <= 1) {
	        		  stopModeEvent(mode);
	        	  }
  				startEvent(mode);
	      		if(mode.getID().equalsIgnoreCase("oneinchamber")) {
	    			for(Player players : mode.getPlayers()) {
	    				players.getInventory().clear();
	    				loadKit(players,mode);

	    			}
	    		}
				if(mode.getID().equalsIgnoreCase("sumo")) {
					if(checkConfigSumop(mode) == false) {
						stopModeEvent(mode);
					}
				}
				if(mode.getID().equalsIgnoreCase("brackets")) {
					if(checkConfigSumop(mode) == false) {
						stopModeEvent(mode);
					}
				}
	           }

	             
	           }
	       }, 20, 20);

	
	}
	public static List<Player> fighting = new ArrayList<>();
	private void randomAdd(mode mode) {

		ArrayList<Player> play = new ArrayList<Player>();
		for (Player online : mode.getPlayers()) {
				play.add(online);			
		}
		Random random = new Random();
		int index = random.nextInt(play.size());
		Player loc = (Player) play.get(index);
		int index2 = random.nextInt(play.size());
		while(index == index2) {
			index2 = random.nextInt(play.size());
		}
		Player p1 = play.get(index);
		Player p2 = play.get(index2);
		
		fighting.add(p1);
		fighting.add(p2);
		tpSpawnSumo(p1, 1, mode);
		tpSpawnSumo(p2, 2, mode);
		
		loadKit(p1,mode);
		loadKit(p2, mode);
	}
	private void randomAdd1() {
		fighting.clear();
		mode mode = plugin.getmodeManager().getMode("redrover");
		ArrayList<Player> play = new ArrayList<Player>();
		for (Player online : mode.getPlayers()) {
				play.add(online);			
		}
		Random random = new Random();
		int index = random.nextInt(play.size());
		Player loc = (Player) play.get(index);
		Player p1 = play.get(index);
		
		fighting.add(p1);
		tpSpawnredrover(p1);
	}
	
	
	private void tpSpawnSumo(Player p, int pos2, mode mode) {
		data.setupData();
	
		
		
		if(!data.getData().contains(mode.getID() + "." + pos2 + ".world")) {
			messagesUtil.playerErrorMSG(p, "Spawn is not set for "+ pos2);
			return;
		}
	
		
		World world = Bukkit.getWorld(data.getData().getString(mode.getID() + "." + pos2+".world"));
		double x = data.getData().getDouble(mode.getID() + "." + pos2+ ".x");
		double y = data.getData().getDouble(mode.getID() + "." + pos2+ ".y");
		double z = data.getData().getDouble(mode.getID() + "." + pos2 + ".z");
		float yaw = (float) data.getData().getDouble(mode.getID() + "." + pos2 + ".yaw");
		float pitch = (float )data.getData().getDouble(mode.getID() + "." + pos2+".pitch");
		p.teleport(new Location(world, x, y, z, yaw, pitch));
		return;
	}
	
	private void tpSpawnredrover(Player p) {
		data.setupData();
		mode mode = plugin.getmodeManager().getMode("redrover");
		
		
		if(!data.getData().contains(mode.getID() + ".spawnP.world")) {
			messagesUtil.playerErrorMSG(p, "Spawn is not set for redrover");
			return;
		}
	
		
		World world = Bukkit.getWorld(data.getData().getString(mode.getID() + "." + "spawnP"+".world"));
		double x = data.getData().getDouble(mode.getID() + "." + "spawnP"+ ".x");
		double y = data.getData().getDouble(mode.getID() + "." + "spawnP"+ ".y");
		double z = data.getData().getDouble(mode.getID() + "." + "spawnP" + ".z");
		float yaw = (float) data.getData().getDouble(mode.getID() + "." + "spawnP" + ".yaw");
		float pitch = (float )data.getData().getDouble(mode.getID() + "." + "spawnP"+".pitch");
		p.teleport(new Location(world, x, y, z, yaw, pitch));
		return;
	}
	
	private void startEvent(mode mode) {
		if(mode.getID().equalsIgnoreCase("spleef")) {
			minedBlocks = new ArrayList<Block>();
		}
		if(mode.getID().equalsIgnoreCase("sumo")) {
			
			randomAdd(mode);
		}
		if(mode.getID().equalsIgnoreCase("brackets")) {
			for(Player players : mode.getPlayers()) {
				if(!fighting.contains(players)) {
					players.getInventory().clear();
					players.getInventory().setArmorContents(null);
				}
			}
			randomAdd(mode);
		}
		if(mode.getID().equalsIgnoreCase("tntrun")) {
			minedBlocks = new ArrayList<Block>();
		}
		if(mode.getID().equalsIgnoreCase("redrover")) {
			randomAdd1();
			for(Player players : mode.getPlayers()) {
				if(!fighting.contains(players)) {
					players.getInventory().clear();
				}
			}
		}
	}
	
	private boolean checkConfigSumop(mode mode) {
		data.setupData();
		if(!data.getData().contains(mode.getID() + ".1.world") || !data.getData().contains(mode.getID() + ".2.world")) {
			return false;
		}
		return true;
	}
	
	public void playerJoinEvent(Player p, mode mode) {
		if(mode.getStarted() == false) {
			messagesUtil.lineMessage(p, "eventsadmin.msg.noModes");
			return;
		}
		if(!mode.getJoinable()) {
			messagesUtil.lineMessage(p, "mode.messages.join.alreadystarted");
			return;
		}
		
		if(mode.getPlayers().contains(p)) {
			messagesUtil.lineMessage(p, "mode.messages.alreadyIn");
			return;
		}
		
		userManager userManager = plugin.userMangerHashMap.get(p);
				
		List<Player> players = mode.getPlayers();
		players.add(p);
		mode.setPlayers(players);
		
		userManager.setArmor(p.getInventory().getArmorContents());
		userManager.setInv(p.getInventory().getContents());
		p.setGameMode(GameMode.SURVIVAL);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		tpspawn(p, mode.getID());
		// not done
	
		loadKit(p,mode);
		messagesUtil.lineMessage(p, "mode.messages.joined");
	}
	
	private void loadKit(Player p, mode mode) {
		userManager userManager = plugin.userMangerHashMap.get(p);
		if(!mode.getPlayers().contains(p)) {
			messagesUtil.lineMessage(p, "mode.messages.notIn");
			return;
		}
		if(mode.getInv().size() >= 1) {
			for(ItemStack inv : mode.getInv()) {
				p.getInventory().addItem(inv);
			}
		}
		
		if(mode.getArmor().size() >=1 ) {
			if(mode.getArmor().containsKey("helmet")) {
				p.getInventory().setHelmet(mode.getArmor().get("helmet"));
			}
			if(mode.getArmor().containsKey("chestplate")) {
				p.getInventory().setChestplate(mode.getArmor().get("chestplate"));
			}
			if(mode.getArmor().containsKey("pants")) {
				p.getInventory().setLeggings(mode.getArmor().get("pants"));
			}
			if(mode.getArmor().containsKey("boots")) {
				p.getInventory().setBoots(mode.getArmor().get("boots"));
			}
		}
		

	}

	
	public void playerLeaveEvent(Player p, mode mode) {
		userManager userManager = plugin.userMangerHashMap.get(p);
		if(!mode.getPlayers().contains(p)) {
			messagesUtil.lineMessage(p, "mode.messages.notIn");
			return;
		}
		p.getInventory().setArmorContents(userManager.getArmor());
		p.getInventory().setContents(userManager.getInv());
		p.setHealth(20);
		p.setFoodLevel(20);
		mode.getPlayers().remove(p);
		messagesUtil.lineMessage(p, "mode.messages.left");
		checkWinners(mode);
		tpspawn(p, "spawn");
	}
	
	public void playerElmEvent(Player p, mode mode) {
		userManager userManager = plugin.userMangerHashMap.get(p);
		if(!mode.getPlayers().contains(p)) {
			messagesUtil.lineMessage(p, "mode.messages.notIn");
			return;
		}
		p.getInventory().setArmorContents(userManager.getArmor());
		p.getInventory().setContents(userManager.getInv());
		p.setHealth(20);
		p.setFoodLevel(20);
		mode.getPlayers().remove(p);
		messagesUtil.elmWinnMessage("mode.messages.PlayerElm", p);
		tpspawn(p, "spawn");
		checkWinners(mode);
		if(mode.getID().equalsIgnoreCase("sumo")) {
			if(mode.getPlayers().size() >= 2) {
				fighting.remove(p);
				Player pl = fighting.get(0);
				tpspawn(pl, mode.getID());
				fighting.clear();
				randomAdd(mode);
			}
		}
		if(mode.getID().equalsIgnoreCase("brackets")) {
			if(mode.getPlayers().size() >= 2) {
				fighting.remove(p);
				Player pl = fighting.get(0);
				tpspawn(pl, mode.getID());
				fighting.clear();
				randomAdd(mode);
			}

		}
	}
	
	public void checkWinners(mode mode) {
		//WinnerCommand
		if(!mode.getEnabled()) {
			return;
		}

		if(mode.getJoinable()) {
			return;
		}
		
		if(mode.getPlayers().size() <1) {
			stopModeEvent(mode);
		}
		
		if(mode.getPlayers().size() == 1) {
			Player winner = mode.getPlayers().get(0);
			userManager userManager = plugin.userMangerHashMap.get(winner);
			if(!mode.getPlayers().contains(winner)) {
				messagesUtil.lineMessage(winner, "mode.messages.notIn");
				return;
			}

			winner.getInventory().setArmorContents(userManager.getArmor());
			winner.getInventory().setContents(userManager.getInv());
			winner.setHealth(20);
			winner.setFoodLevel(20);
			tpspawn(winner, "spawn");
			messagesUtil.elmWinnMessage("mode.messages.PlayerWinner", winner);
			String cmd = plugin.getConfig().getString("WinnerCommand");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", winner.getName()));
			mode.getPlayers().remove(winner);
			returnBlocks(mode);
			stopModeEvent(mode);
			return;
		}
		
		
		
	}
	
	private void returnBlocks(mode mode) {
		if(mode.getID().equalsIgnoreCase("spleef")) {
			if(minedBlocks == null || minedBlocks.size() == 0) {
				return;
			}
			for(Block block : minedBlocks) {
				block.setType(Material.SNOW_BLOCK);
			}
		}
		if(mode.getID().equalsIgnoreCase("tntrun")) {
			if(minedBlocks == null || minedBlocks.size() == 0) {
				return;
			}
			for(Block block : minedBlocks) {
				block.setType(Material.TNT);
			}
		}
	}
	
	private void playerForceStop(Player p, mode mode) {
		userManager userManager = plugin.userMangerHashMap.get(p);
		if(!mode.getPlayers().contains(p)) {
			messagesUtil.lineMessage(p, "mode.messages.notIn");
			return;
		}
		p.getInventory().setArmorContents(userManager.getArmor());
		p.getInventory().setContents(userManager.getInv());
		messagesUtil.lineMessage(p, "mode.messages.left");
		tpspawn(p, "spawn");
		fighting.clear();
		cooldown.clear();
		cooldownTask.clear();
	}
	
	public boolean checkInEvent(Player p, mode mode) {
		if(mode.getPlayers().contains(p)) {
			return true;
		}		
		return false;
	}
	
	public boolean checkInAnyEvents(Player p) {
		for(Entry<String, mode> mode : plugin.getmodeManager().modes.entrySet()) {
			if(mode.getValue().getStarted() == true) {
				if(mode.getValue().getPlayers().contains(p)) {
					return true;
				}
			}		
		}
		return false;
	}

	public boolean modeStarted() {
		for(Entry<String, mode> mode : plugin.getmodeManager().modes.entrySet()) {
			if(mode.getValue().getStarted() == true) {
				return true;
			}		
			
		}

		return false;
	}
	
	public void stopModes() {
		for(Entry<String, mode> mode : plugin.getmodeManager().modes.entrySet()) {
			if(mode.getValue().getStarted() == true) {
				stopModeEvent(mode.getValue());
			}		
		}
		
		
	}

	public mode getActiceMode() {
		for(Entry<String, mode> mode : plugin.getmodeManager().modes.entrySet()) {
			if(mode.getValue().getStarted() == true) {
				return mode.getValue();
			}		
		}
		return null;
	}
	
	public void stopModeEvent(mode mode) {
		mode.setStarted(false);
		mode.setJoinable(false);	
		for(int i = 0;i<mode.getPlayers().size();i++) {
			playerForceStop(mode.getPlayers().get(i), mode);
		}
		if(mode.getID().equalsIgnoreCase("spleef") || mode.getID().equalsIgnoreCase("tntrun")) {
			returnBlocks(mode);
		}

		mode.getPlayers().clear();
	}
	
	public void setspawnSumo(Player p, int spawn, mode mode) {
		data.setupData();
		
		if(spawn != 1 && spawn!=2){
			messagesUtil.playerErrorMSG(p, "Spawnpoint must be 1 or 2");
			return;
		}

		
		String world = p.getLocation().getWorld().getName();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		float yaw = p.getLocation().getYaw();
		float pitch = p.getLocation().getPitch();

		data.getData().set(mode.getID() + "." + spawn + ".world", world);
		data.getData().set(mode.getID() + "." + spawn + ".x", x);
		data.getData().set(mode.getID() + "." + spawn + ".y", y);
		data.getData().set(mode.getID() + "." + spawn + ".z", z);
		data.getData().set(mode.getID() + "." + spawn + ".yaw", yaw);
		data.getData().set(mode.getID() + ".." + spawn + ".pitch", pitch);
		data.saveData();
		messagesUtil.customMessage(p, "&6(Events) &fYou have set spawnpoint for " + mode.getID() + " " + spawn);
	}
	
	public void setSpawnredrover(Player p) {
		data.setupData();
		

		
		String world = p.getLocation().getWorld().getName();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		float yaw = p.getLocation().getYaw();
		float pitch = p.getLocation().getPitch();

		data.getData().set("redrover." + ".spawnP" + ".world", world);
		data.getData().set("redrover." + ".spawnP" + ".x", x);
		data.getData().set("redrover." + ".spawnP" + ".y", y);
		data.getData().set("redrover." + ".spawnP" + ".z", z);
		data.getData().set("redrover." + ".spawnP" + ".yaw", yaw);
		data.getData().set("redrover." + ".spawnP" + ".pitch", pitch);
		data.saveData();
		messagesUtil.customMessage(p, "&6(Events) &fYou have set spawnpoint for redrover");
	}
	
	public void setspawn(Player p, String  mode) {
		data.setupData();
		
		if(!plugin.getmodeManager().getModes().containsKey(mode) && !mode.equalsIgnoreCase("spawn")) {
			messagesUtil.playerErrorMSG(p, "There is no event with that name");
			return;
		}
		
		
		String world = p.getLocation().getWorld().getName();
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		float yaw = p.getLocation().getYaw();
		float pitch = p.getLocation().getPitch();

		data.getData().set(mode + ".world", world);
		data.getData().set(mode + ".x", x);
		data.getData().set(mode + ".y", y);
		data.getData().set(mode + ".z", z);
		data.getData().set(mode + ".yaw", yaw);
		data.getData().set(mode + ".pitch", pitch);
		data.saveData();
		messagesUtil.customMessage(p, "&6(Events) &fYou have set &6" + mode + " &fspawn.");
	}
	
	public void tpspawn(Player p, String mode) {
		data.setupData();
		
		
		if(!data.getData().contains(mode + ".x")) {
			messagesUtil.playerErrorMSG(p, "Spawn is not set for "+ mode);
			return;
		}
		
		World world = Bukkit.getWorld(data.getData().getString(mode + ".world"));
		double x = data.getData().getDouble(mode + ".x");
		double y = data.getData().getDouble(mode + ".y");
		double z = data.getData().getDouble(mode + ".z");
		float yaw = (float) data.getData().getDouble(mode + ".yaw");
		float pitch = (float )data.getData().getDouble(mode + ".pitch");
		

		
		p.teleport(new Location(world, x, y, z, yaw, pitch));
		return;
	}
	
	private HashMap<Player, Integer> cooldown = new HashMap<>();
	private HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<>();
	
	public boolean onCoolDown(Player p) {
		if (cooldown.containsKey(p)) {
			return true;
		}
		return false;
	}

	public void removeCoolDown(Player p) {
		cooldown.remove(p);
	}

	public void addCooldown(Player p, int cooldownT) {
		cooldown.put(p, cooldownT);
		cooldownTask.put(p, new BukkitRunnable() {
			public void run() {
				p.setLevel(cooldown.get(p));
				cooldown.put(p, cooldown.get(p) - 1);
				if (cooldown.get(p) == 0) {
					cooldown.remove(p);
					cooldownTask.remove(p);
					cancel();
				}
			
			}
		});

		cooldownTask.get(p).runTaskTimer(plugin, 20, 20);
	}
	
	public void modeGUI(Player p) {
		
		Inventory inv = Bukkit.createInventory(null, 9, c.f("&6&lEvents"));
		
		for(Entry<String, mode> mode : plugin.getmodeManager().modes.entrySet()) {
			mode mod = mode.getValue();
			if(mod.getEnabled() == false) {
				continue;
			}
			
			ItemStack event = new ItemStack(mod.getIcon(), 1);
			ItemMeta eventMeta = event.getItemMeta();
			eventMeta.setDisplayName(c.f(mod.getName()));
			event.setItemMeta(eventMeta);
			inv.addItem(event);
		}
		
		p.openInventory(inv);
		
	}
	

	
	@EventHandler
	public void onInventoryCHECKS(InventoryClickEvent e) {	
		if (e.getInventory().getName().equalsIgnoreCase(c.f("&6&lEvents"))) {
			
			Player p = (Player) e.getWhoClicked();
			userManager userManager = plugin.userMangerHashMap.get(p);
			e.setCancelled(true);
			

			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			
			if(!e.getCurrentItem().hasItemMeta()) {
				return;
			}
			ItemStack selectedItem = e.getCurrentItem();
			ItemMeta seletectedMeta = selectedItem.getItemMeta();
			for(Entry<String, mode> mode : plugin.getmodeManager().modes.entrySet()) {
				mode mod = mode.getValue();
				if(mod.getEnabled() == false) {
					continue;
				}
				if(seletectedMeta.getDisplayName().equalsIgnoreCase(c.f(mod.getName()))) {
					if(userManager.getTokens() >=1 || p.hasPermission("events.admin")) {
						userEVNT userEVNT = new userEVNT();
						userEVNT.removeTokens(p , 1);
						startTimer(p, mod.getID());
						p.closeInventory();
					}else {
						messagesUtil.lineMessage(p, "mode.messages.NotEnough");
						p.closeInventory();
					}
				}
			}
			
			
		}
			
			
	}
	
	
	
	
}
