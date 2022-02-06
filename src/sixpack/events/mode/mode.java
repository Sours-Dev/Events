package sixpack.events.mode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import sixpack.events.events;

public class mode implements Listener{

	private events plugin = events.getInstance();
	private boolean enabled;
	private boolean started;
	private boolean joinable;
	private String id;
	private String name;
	private Material icon;
	private List<Player> joinedPlayers;
	private  ArrayList<ItemStack> inv;
    private  HashMap<String, ItemStack> armor;
	
	public mode(boolean enabled, boolean started, boolean joinable, String id, String name, Material icon, List<Player> joinedPlayers, ArrayList<ItemStack> inv, HashMap<String, ItemStack> armor) {
		this.setJoinable(joinable);
		this.setStarted(started);
		this.setID(id);
		this.setName(name);
		this.setMaterial(icon);
		this.setPlayers(joinedPlayers);
		this.setEnabled(enabled);
		this.setInv(inv);
		this.setArmor(armor);
		Bukkit.getPluginManager().registerEvents(this,  plugin);
	}
	
	public ArrayList<ItemStack> getInv() {
		return inv;
	}
	
	public HashMap<String, ItemStack> getArmor() {
		return armor;
	}
	
	public void setInv(ArrayList<ItemStack> inv) {
		this.inv = inv;
	}
 	
	public void setArmor(HashMap<String, ItemStack>armor) {
		this.armor = armor;
	}
	
	
	public boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean Enabled) {
		this.enabled = Enabled;
	}
	
	public boolean getStarted() {
		return started;
	}
	
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public List<Player> getPlayers(){
		return joinedPlayers;
	}
	
	public void setPlayers(List<Player> jointedPlayers) {
		this.joinedPlayers = jointedPlayers;
	}
	
	public boolean getJoinable() {
		return joinable;
	}
	
	public void setJoinable(boolean joinable) {
		this.joinable = joinable;
	}
	
	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Material getIcon() {
		return icon;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setMaterial(Material icon) {
		this.icon = icon;
	}
}
