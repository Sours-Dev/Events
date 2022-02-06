package sixpack.events.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import sixpack.events.events;
import sixpack.events.mode.mode;
import sixpack.events.player.userEVNT;
import sixpack.events.util.messagesUtil;

public class eventsAdminCMD  implements CommandExecutor{

	private sixpack.events.mode.modeEVNT modeEVNT = new sixpack.events.mode.modeEVNT();
	private events plugin = events.getInstance();
	// force stop // 
	// eventsadmin settokens (player) (amount)
	//eventsadmin addTokesns (player) (amount)
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = null;
		boolean console = false;
		if(!(sender instanceof Player)) {
			console = true;
		}else { p = (Player) sender;}
		
		
		
		if(console == false && !p.hasPermission("events.admin")){
			messagesUtil.noPerms(p);			
			return true;
		}
	
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("forcestop")) {
				if(modeEVNT.modeStarted() == false) {
					if(console == true) {
						messagesUtil.lineMessageConsole("eventsadmin.msg.noModes");
						return true;
					}
					messagesUtil.lineMessage(p, "eventsadmin.msg.noModes");
					return true;
				}else {
					
					modeEVNT.stopModes();
					messagesUtil.broadMessage("eventsadmin.msg.broadcastCancel");
					if(console == true) {
						messagesUtil.lineMessageConsole("eventsadmin.msg.stoppedModes");
						return true;
					}
					messagesUtil.lineMessage(p, "eventsadmin.msg.stoppedModes");
					return true;
				}
				
			}
			if(args[0].equalsIgnoreCase("setspawn")) {
				if(console == true) {
					messagesUtil.playerOnly();
				}
				modeEVNT.setspawn(p, "spawn");
				return true;
			}
			if(args[0].equalsIgnoreCase("setredroverspawn")) {
				if(console == true) {
					messagesUtil.playerOnly();
				}
				modeEVNT.setSpawnredrover(p);
				return true;
			}
		}
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("setspawn")) {
				if(console == true) {
					messagesUtil.playerOnly();
				}
				modeEVNT.setspawn(p , args[1]);
				return true;
			}
			if(args[0].equalsIgnoreCase("setsumospawn")) {
				if(console == true) {
					messagesUtil.playerOnly();
				}
				mode mode = plugin.getmodeManager().getMode("sumo");
				try {
					modeEVNT.setspawnSumo(p, Integer.valueOf(args[1]), mode);
				}catch(Exception e) {
					messagesUtil.playerErrorMSG(p, "Invalid input");
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("setbracketsspawn")) {
				if(console == true) {
					messagesUtil.playerOnly();
				}
				mode mode = plugin.getmodeManager().getMode("brackets");
				try {
					modeEVNT.setspawnSumo(p, Integer.valueOf(args[1]), mode);
				}catch(Exception e) {
					messagesUtil.playerErrorMSG(p, "Invalid input");
				}
				return true;
			}
		}
		userEVNT userEVNT = new userEVNT();
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("settokens")) {
				if(Bukkit.getPlayer(args[1]) == null) {
					if(console == true) {
						messagesUtil.consoleMessage("Invalid Player");
						return true;
					}
					messagesUtil.playerErrorMSG(p, "Invalid player");
					return true;	
				}
				Player target = Bukkit.getPlayer(args[1]);
				
				try {
					userEVNT.setTokens(target, Integer.parseInt(args[2]));
				}catch(Exception e) {
					if(console == true) {
						messagesUtil.consoleMessage("Invalid amount");
						return true;
					}
					messagesUtil.playerErrorMSG(target, "Invalid amount");
					return true;
				}
				if(console == true) {
					messagesUtil.consoleMessage("&6(Events) &fYou have set &6" + target.getName() + " &ftokens to &6" + args[2]);
					return true;
				}
				messagesUtil.customMessage(p, "&6(Events) &fYou have set &6" + target.getName() + " &ftokens to &6" + args[2]);
				return true;
			}
			if(args[0].equalsIgnoreCase("addTokens")) {
				if(Bukkit.getPlayer(args[1]) == null) {
					if(console == true) {
						messagesUtil.consoleMessage("Invalid player");
						return true;
					}
					messagesUtil.playerErrorMSG(p, "Invalid player");
					return true;	
				}
				Player target = Bukkit.getPlayer(args[1]);
				
				try {
					userEVNT.addTokens(target, Integer.parseInt(args[2]));
				}catch(Exception e) {
					if(console == true) {
						messagesUtil.consoleMessage("Invalid amount");
						return true;
					}
					messagesUtil.playerErrorMSG(target, "Invalid amount");
					return true;
				}
				if(console == true) {
					messagesUtil.consoleMessage("&6(Events) &fYou have added &6" + target.getName() + " &ftokens to &6" + args[2]);
					return true;
				}
				messagesUtil.customMessage(p, "&6(Events) &fYou have added &6" + args[2] + " &ftokens to &6" + target.getName());
				return true;
			}
			if(args[0].equalsIgnoreCase("removeTokens")) {
				if(Bukkit.getPlayer(args[1]) == null) {
					if(console == true) {
						messagesUtil.consoleMessage("Invalid player");
						return true;
					}
					messagesUtil.playerErrorMSG(p, "Invalid player");
					return true;	
				}
				Player target = Bukkit.getPlayer(args[1]);
				try {
					userEVNT.removeTokens(target, Integer.parseInt(args[2]));
				}catch(Exception e) {
					if(console == true) {
						messagesUtil.consoleMessage("Invalid amount");
						return true;
					}
					messagesUtil.playerErrorMSG(target, "Invalid amount");
					return true;
				}
				if(console == true) {
					messagesUtil.consoleMessage("&6(Events) &fYou have removed &6" + args[2] + " &ftokens from &6" + target.getName());
					return true;
				}
				messagesUtil.customMessage(p, "&6(Events) &fYou have removed &6" + args[2] + " &ftokens from &6" + target.getName());
				return true;
			}
			
		}
		
		if(console == true) {
			messagesUtil.lineMessageConsole("eventsadmin.msg.help");
			return true;
		}		
		messagesUtil.listMessage(p, "eventsadmin.msg.help");
		return true;
	}


	
}
