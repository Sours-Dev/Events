package sixpack.events.mode.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import sixpack.events.events;
import sixpack.events.mode.mode;
import sixpack.events.mode.modeEVNT;
import sixpack.events.player.userManager;
import sixpack.events.util.configData;
import sixpack.events.util.messagesUtil;

public class oneInChamber  extends mode{

	
	private configData data = new configData();
	private events plugin = events.getInstance();
	private modeEVNT modeEVNT = new modeEVNT();
	
	
	public oneInChamber(boolean enabled, boolean started, boolean joinable, String id, String name, Material icon, List<Player> players, ArrayList<ItemStack> inv, HashMap<String, ItemStack> armor) {
		super(enabled, started, joinable, id, name, icon, players, inv ,armor);
	}
	

	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("oneinchamber");
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
		mode mode = plugin.getmodeManager().getMode("oneinchamber");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		
		if(mode.getStarted()) {
			e.setCancelled(true);
			return;
		}

	
	}
	private HashMap<Player, Integer> lifes = new HashMap();
	private void playerShot(Player p) {
		mode mode = plugin.getmodeManager().getMode("oneinchamber");

		lifes.putIfAbsent(p, 3);
		
		int live = lifes.get(p) - 1;
		if(live == 0) {
			modeEVNT.playerElmEvent(p, mode);
			lifes.remove(p);			
			return;
		}
		
		lifes.remove(p);
		lifes.put(p, live);
		p.setHealth(20);

		modeEVNT.tpspawn(p, mode.getID());
		p.setVelocity(new Vector(0,0,0));
		p.getInventory().addItem(new ItemStack(Material.ARROW,1));
		messagesUtil.customMessage(p, "&6(Events) &fYou have died and lost a life (" + lifes.get(p)  + "/3)");
		
		
		modeEVNT.addCooldown(p, 5);
		
	}
	
	@EventHandler
	public void damage(EntityDamageEvent e) 
	{
		mode mode = plugin.getmodeManager().getMode("redrover");
    
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		mode mode = plugin.getmodeManager().getMode("oneinchamber");
		if (!(e.getDamager() instanceof Player)) {
			
			if(e.getDamager() instanceof Arrow) {
				if(e.getEntity() instanceof Player) {
					Player target = (Player) e.getEntity();
					if(!mode.getPlayers().contains(target)) {
						return;
					}
					if(mode.getStarted() == false) {
						return;
					}
					
					if(mode.getJoinable()) {
						e.setCancelled(true);
						return;
					}
					if(modeEVNT.onCoolDown(target)) {
						e.setCancelled(true);
						return;
					}
					playerShot(target);					
					Arrow arrow = (Arrow) e.getDamager();
					if(arrow.getShooter() instanceof Player) {
						Player player = (Player) arrow.getShooter();
						if(modeEVNT.onCoolDown(player)) {
							e.setCancelled(true);
							return;
						}
						player.getInventory().addItem(new ItemStack(Material.ARROW,1));
						messagesUtil.customMessage(player, "&6(Events) &fYou have killed &6&l" + target.getName());
						
					}
					e.setCancelled(true);
				}
			}
			
			return;
		}
		Player player = (Player) e.getDamager();
		if(!mode.getPlayers().contains(player)) {
			return;
		}
		if(mode.getStarted() == false) {
			return;
		}
		
		if(mode.getJoinable()) {
			e.setCancelled(true);
			return;
		}
		

		
		if (e.getEntity() instanceof Player) {
			Player target = (Player) e.getEntity();
			if(modeEVNT.onCoolDown(target)) {
				e.setCancelled(true);
				return;
			}
			if(modeEVNT.onCoolDown(player)) {
				e.setCancelled(true);
				return;
			}
		    if (((((Damageable) e.getEntity()).getHealth() - e.getFinalDamage()) <= 0)
			        && e.getEntity() instanceof Player) {
			    	if(mode.getStarted() == false) {
			    		return;
			    	}		    	
			    	playerShot(target);
					player.getInventory().addItem(new ItemStack(Material.ARROW,1));
					messagesUtil.customMessage(player, "&6(Events) &fYou have killed &6&l" + target.getName());
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
		mode mode = plugin.getmodeManager().getMode("oneinchamber");
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
		mode mode = plugin.getmodeManager().getMode("oneinchamber");
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
		mode mode = plugin.getmodeManager().getMode("oneinchamber");
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
		mode mode = plugin.getmodeManager().getMode("oneinchamber");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getStarted()) {
			e.setCancelled(true);
		}
	}

}
