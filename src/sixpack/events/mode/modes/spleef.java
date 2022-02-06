package sixpack.events.mode.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import sixpack.events.events;
import sixpack.events.mode.mode;
import sixpack.events.mode.modeEVNT;
import sixpack.events.player.userManager;
import sixpack.events.util.configData;
import sixpack.events.util.messagesUtil;
import sixpack.events.util.playerUtil;

public class spleef extends mode{

	private configData data = new configData();
	private events plugin = events.getInstance();
	private modeEVNT modeEVNT = new modeEVNT();
	
	
	public spleef(boolean enabled, boolean started, boolean joinable, String id, String name, Material icon, List<Player> players, ArrayList<ItemStack> inv, HashMap<String, ItemStack> armor) {
		super(enabled, started, joinable, id, name, icon, players, inv ,armor);
	}

	


	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("spleef");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getStarted()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void blockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("spleef");
		
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getStarted() == false) {
			return;
		}
		if(mode.getStarted() && mode.getJoinable()) {
			e.setCancelled(true);
			return;
		}
		if(mode.getStarted() && !mode.getJoinable()) {
			if(e.getBlock().getType() != Material.SNOW_BLOCK) {
				e.setCancelled(true);				
				return;
			}
		}
	
		Block block = e.getBlock();
		
		modeEVNT.minedBlocks.add(block);	
	}
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("spleef");

		sixpack.events.mode.modeEVNT modeEVNT = new modeEVNT();
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getJoinable() ==true) {
			return;
		}
		
		if(mode.getStarted() == false) {
			return;
		}
		
		if(mode.getEnabled() == false) {
			return;
		}
		if(playerUtil.isInWater(p)) {
			modeEVNT.playerElmEvent(p, mode);
		}
		
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {	
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		Player tar = (Player) e.getDamager();
		userManager userManager = plugin.userMangerHashMap.get(tar);
		mode mode = plugin.getmodeManager().getMode("spleef");
		if(!mode.getPlayers().contains(tar)) {
			return;
		}
		if(mode.getStarted()) {
			e.setCancelled(true);
		}

		
	}
	
	@EventHandler
	public void invMove(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}	
		Player p = (Player) e.getWhoClicked();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("spleef");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getStarted()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void pickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("spleef");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getStarted()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void drop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("spleef");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getStarted()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onHungerLoss(FoodLevelChangeEvent e) {
		Player p = (Player) e.getEntity();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("spleef");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getStarted()) {
			e.setCancelled(true);
		}
	}
	
	
}

