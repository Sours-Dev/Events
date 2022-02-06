package sixpack.events.mode.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import sixpack.events.events;
import sixpack.events.mode.mode;
import sixpack.events.mode.modeEVNT;
import sixpack.events.player.userManager;
import sixpack.events.util.configData;
import sixpack.events.util.messagesUtil;
import sixpack.events.util.playerUtil;

public class redrover extends mode{

	private configData data = new configData();
	private events plugin = events.getInstance();
	public redrover(boolean enabled, boolean started, boolean joinable, String id, String name, Material icon, List<Player> players, ArrayList<ItemStack> inv , HashMap<String, ItemStack>armor) {
		super(enabled, started, joinable, id, name, icon, players, inv ,armor);
	}

	@EventHandler
	public void damage(EntityDamageEvent e) 
	{
		mode mode = plugin.getmodeManager().getMode("redrover");
	    if (((((Damageable) e.getEntity()).getHealth() - e.getFinalDamage()) <= 0)
	        && e.getEntity() instanceof Player) {
	    	if(mode.getStarted() == false) {
	    		return;
	    	}
	    	
	    	
	    	Player p = (Player) e.getEntity();
	        e.setCancelled(true);
	    	modeEVNT modeEVNT = new modeEVNT();
			modeEVNT.playerElmEvent(p, mode);
	    }     
	}
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		mode mode = plugin.getmodeManager().getMode("redrover");
		
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if(mode.getEnabled() == false) {
				return;
			}
			
			if(mode.getStarted() == false) {
				return;
			}
			
			if(!mode.getPlayers().contains(player)) {
				return;
			}
			
			if(mode.getJoinable()) {
				e.setCancelled(true);
			}
			if(!modeEVNT.fighting.contains(player)) {
				e.setCancelled(true);
			}
		
		}
	}
	

	

	
	

	
	@EventHandler
	public void invMove(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}	
		Player p = (Player) e.getWhoClicked();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("redrover");
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
		mode mode = plugin.getmodeManager().getMode("redrover");
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
		mode mode = plugin.getmodeManager().getMode("redrover");
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
		mode mode = plugin.getmodeManager().getMode("redrover");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		
		if(mode.getStarted()) {
			e.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onHungerLoss(FoodLevelChangeEvent e) {
		Player p = (Player) e.getEntity();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("redrover");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getStarted()) {
			e.setCancelled(true);
		}
	}

}
