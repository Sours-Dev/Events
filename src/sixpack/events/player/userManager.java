package sixpack.events.player;

import java.util.List;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class userManager {

	
	private String name;
	private int tokens;
	private  ItemStack[] inv;
    private  ItemStack[] armor;
	
	
	
	public userManager(String name, int tokens, ItemStack[] inv, ItemStack[] armor) {
		this.setName(name);
		this.setTokens(tokens);
		this.setInv(inv);
		this.setArmor(armor);
	}


	public ItemStack[] getInv() {
		return inv;
	}
	
	public ItemStack[] getArmor() {
		return armor;
	}
	
	public void setInv(ItemStack[] inv) {
		this.inv = inv;
	}
 	
	public void setArmor(ItemStack[]armor) {
		this.armor = armor;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getTokens() {
		return tokens;
	}

	public void setTokens(int tokens) {
		this.tokens = tokens;
	}




	
	
}
