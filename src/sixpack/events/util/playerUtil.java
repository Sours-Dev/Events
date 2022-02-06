package sixpack.events.util;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class playerUtil {

	
    public static boolean isInWater(Player p) {
		  if (!(p.getLocation().getBlock().isLiquid() || p.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid() || p.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid())) {
	            return false;
	        }
	        return true;
	}
}
