package sixpack.events.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sixpack.events.events;
import sixpack.events.mode.mode;
import sixpack.events.util.messagesUtil;

public class eventsCMD  implements CommandExecutor{

	private sixpack.events.mode.modeEVNT modeEVNT = new sixpack.events.mode.modeEVNT();
	private events plugin = events.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		
		if(!(sender instanceof Player)) {
			messagesUtil.playerOnly();
			return true;
		}
		
		Player p = (Player) sender;
		
		if(!p.hasPermission("events.default")){
			messagesUtil.noPerms(p);
			return true;
		}

		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("start")) {
				modeEVNT.modeGUI(p);
				return true;
			}
		}
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("start")) {
				if(!plugin.getmodeManager().getModes().containsKey(args[1])) {
					messagesUtil.playerErrorMSG(p, "There is no event with that name");
					return true;
				}
				modeEVNT.startTimer(p, args[1]);
				return true;
			}
			if(args[0].equalsIgnoreCase("join")) {
				if(!plugin.getmodeManager().getModes().containsKey(args[1])) {
					messagesUtil.playerErrorMSG(p, "There is no event with that name");
					return true;
				}
				mode mode = plugin.getmodeManager().getMode(args[1]);
				modeEVNT.playerJoinEvent(p, mode);
				return true;
			}
			if(args[0].equalsIgnoreCase("leave")) {
				if(!plugin.getmodeManager().getModes().containsKey(args[1])) {
					messagesUtil.playerErrorMSG(p, "There is no event with that name");
					return true;
				}
				mode mode = plugin.getmodeManager().getMode(args[1]);
				modeEVNT.playerLeaveEvent(p, mode);
				return true;
			}
		}
		
		messagesUtil.listMessage(p, "events.msg");
		
		return true;
	}

}
