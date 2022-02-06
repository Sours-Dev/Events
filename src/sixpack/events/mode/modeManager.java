package sixpack.events.mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import sixpack.events.mode.modes.brackets;
import sixpack.events.mode.modes.oneInChamber;
import sixpack.events.mode.modes.redrover;
import sixpack.events.mode.modes.spleef;
import sixpack.events.mode.modes.sumo;
import sixpack.events.mode.modes.tntrun;
import sixpack.events.util.configData;
import sixpack.events.util.messagesUtil;



public class modeManager {

//TntRun, Spleef, One in the chamber, Sumo Tournament, anvil drop, water drop
		public HashMap<String, mode> modes;
		
		private configData data = new configData();
		
		public modeManager() {
			modes = new HashMap<>();
			init();
		}
		
		
		public void init() {
			data.setupData();
			
			List<Player> players = new ArrayList<Player>();
			//spleef
			ArrayList<ItemStack> invSpleef = new ArrayList<>();
			HashMap<String, ItemStack> armorSpleef = new HashMap<>();
			// One in the chamber
			ArrayList<ItemStack> invOITC = new ArrayList<>();
			HashMap<String, ItemStack> armorOITC = new HashMap();
			//Emty inv
			ArrayList<ItemStack> invEmtpy = new ArrayList<>();
			HashMap<String, ItemStack> armorEmtpy = new HashMap();
			// RedRover
			ArrayList<ItemStack> invRedrover = new ArrayList<>();
			HashMap<String, ItemStack> armorRedrover = new HashMap();
			//Brackets
			ArrayList<ItemStack> invBrackets = new ArrayList<>();
			HashMap<String, ItemStack> armorBrackets = new HashMap<>();
			
			
			//spleef
		
			ItemStack spleef = new ItemStack(Material.DIAMOND_SPADE, 1);
			ItemMeta spleefMeta = spleef.getItemMeta();
			spleefMeta.addEnchant(Enchantment.DIG_SPEED, 10, true);
			spleefMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			spleef.setItemMeta(spleefMeta);
			invSpleef.add(spleef);	
			armorSpleef.put("pants", new ItemStack(Material.CHAINMAIL_LEGGINGS));
			
			
			//brackets
			
			ItemStack brackets = new ItemStack(Material.DIAMOND_SWORD, 1);
			ItemMeta bracketsMeta = brackets.getItemMeta();
			bracketsMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			bracketsMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			brackets.setItemMeta(bracketsMeta);
			invBrackets.add(brackets);	
			for(int i = 0; i<8; i++) {
				invBrackets.add(new  ItemStack(Material.MUSHROOM_SOUP, 1));
			}
			
			armorBrackets.put("pants", new ItemStack(Material.IRON_LEGGINGS));
			armorBrackets.put("helmet", new ItemStack(Material.IRON_HELMET));
			armorBrackets.put("chestplate", new ItemStack(Material.IRON_CHESTPLATE));
			armorBrackets.put("boots", new ItemStack(Material.IRON_BOOTS));
			//redrover
			ItemStack redRover = new ItemStack(Material.DIAMOND_SWORD, 1);
			ItemMeta redRoverMeta = redRover.getItemMeta();
			redRoverMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			redRoverMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			redRover.setItemMeta(redRoverMeta);
			invRedrover.add(redRover);
			//one in chamber
			ItemStack oneInChamger = new ItemStack(Material.BOW);
			ItemMeta oneInChamgerMeta = spleef.getItemMeta();
			oneInChamgerMeta.addEnchant(Enchantment.DURABILITY, 10, true);
			oneInChamger.setItemMeta(oneInChamgerMeta);
			invOITC.add(new ItemStack(Material.WOOD_SWORD));
			invOITC.add(oneInChamger);
			invOITC.add(new ItemStack(Material.ARROW, 1));
			//brackets
			
			
			addMode("oneinchamber", new oneInChamber(data.getData().getBoolean("oneinchamber.enabled"), false, false, "oneinchamber", "&6&lOne in the chamber", Material.BOW, players, invOITC, armorOITC));
			addMode("sumo" , new sumo(data.getData().getBoolean("sumo.enabled"), false, false, "sumo", "&6&lSumo", Material.STICK, players, invEmtpy, armorEmtpy));
			addMode("tntrun" , new tntrun(data.getData().getBoolean("tntrun.enabled"), false, false, "tntrun", "&6&lTnt Run", Material.TNT, players, invEmtpy, armorEmtpy));
			addMode("spleef", new spleef(data.getData().getBoolean("spleef.enabled"), false, false, "spleef", "&6&lSpleef", Material.DIAMOND_SPADE, players, invSpleef, armorSpleef));
			addMode("redrover", new redrover(data.getData().getBoolean("redrover.enabled"), false, false, "redrover", "&6&lRed Rover", Material.DIAMOND_AXE, players, invRedrover, armorRedrover));
			addMode("brackets", new brackets(data.getData().getBoolean("brackets.enabled"), false, false, "brackets", "&6&lBrackets", Material.DIAMOND_SWORD, players, invBrackets, armorBrackets));
			
			for(Entry<String, mode> mode : modes.entrySet()) {
				
				messagesUtil.consoleMessage("Mode '" + mode.getValue().getID() + "' has been loaded!");
			data.setupData();
				//add defaults
				if(!data.getData().contains(mode.getValue().getID() + ".enabled")) {
					data.getData().set(mode.getValue().getID() + ".enabled", true);
					data.saveData();
					messagesUtil.consoleMessage("Mode '" + mode.getValue().getID() + "' has been added to config");
				}
			
			}
			
		}
		
		public void addMode(String id, mode mode) {
			modes.put(id, mode);
		}
		
		public void removeMode(String id) {
			modes.remove(id);
		}
		
		public mode getMode(String id) {
			return modes.get(id);
		}
		public HashMap<String, mode> getModes(){
			return modes;
		}
}