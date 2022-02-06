package sixpack.events.mode.modes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import sixpack.events.events;
import sixpack.events.mode.mode;
import sixpack.events.mode.modeEVNT;
import sixpack.events.player.userManager;
import sixpack.events.util.configData;
import sixpack.events.util.playerUtil;

public class tntrun extends mode{

	private configData data = new configData();
	private events plugin = events.getInstance();
	public tntrun(boolean enabled, boolean started, boolean joinable, String id, String name, Material icon, List<Player> players, ArrayList<ItemStack> inv , HashMap<String, ItemStack>armor) {
		super(enabled, started, joinable, id, name, icon, players, inv ,armor);
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		mode mode = plugin.getmodeManager().getMode("tntrun");
		
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
			e.setCancelled(true);

		}
	}
	
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("tntrun");
		sixpack.events.mode.modeEVNT modeEVNT = new modeEVNT();
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getJoinable() ==true) {
			return;
		}
		
		if(mode.getEnabled() == false) {
			return;
		}
		
		if(mode.getStarted() == false) {
			return;
		}
		
		if(playerUtil.isInWater(p)) {
			modeEVNT.playerElmEvent(p, mode);
			return;
		}
		
		Location loc = e.getPlayer().getLocation().clone().subtract(0, 1, 0);
		Block block = loc.getBlock();
		
		if(block.getType() != Material.TNT) {
			return;
		}
		
		modeEVNT.minedBlocks.add(block);
		
		if(!onCoolDown(block)) {
			addCooldown(block, 1);
		}

	}
	

	private HashMap<Block, Integer> cooldown = new HashMap<>();
	private HashMap<Block, BukkitRunnable> cooldownTask = new HashMap<>();
	
	public boolean onCoolDown(Block block) {
		if (cooldown.containsKey(block)) {
			return true;
		}
		return false;
	}

	public void removeCoolDown(Block block) {
		cooldown.remove(block);
	}

	public void addCooldown(Block block, int cooldownT) {
		cooldown.put(block, cooldownT);
		cooldownTask.put(block, new BukkitRunnable() {
			public void run() {
				cooldown.put(block, cooldown.get(block) - 1);
				if (cooldown.get(block) == 0) {
					block.setType(Material.AIR);
					cooldown.remove(block);
					cooldownTask.remove(block);
					cancel();
				}
			}
		});

		cooldownTask.get(block).runTaskTimer(plugin, 20, 20);
	}
	
	
	

	
	@EventHandler
	public void invMove(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}	
		Player p = (Player) e.getWhoClicked();
		userManager userManager = plugin.userMangerHashMap.get(p);
		mode mode = plugin.getmodeManager().getMode("tntrun");
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
		mode mode = plugin.getmodeManager().getMode("tntrun");
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
		mode mode = plugin.getmodeManager().getMode("tntrun");
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
		mode mode = plugin.getmodeManager().getMode("tntrun");
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
		mode mode = plugin.getmodeManager().getMode("tntrun");
		if(!mode.getPlayers().contains(p)) {
			return;
		}
		if(mode.getStarted()) {
			e.setCancelled(true);
		}
	}
	
}
