package sixpack.events.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import sixpack.events.events;
import sixpack.events.mode.mode;
import sixpack.events.player.userManager;


public class messagesUtil implements Listener{

	private static configLang lang = new configLang();
	private static events plugin = events.getInstance();	
	//console
	public static void debugConsole(String msg) {
		Bukkit.getConsoleSender().sendMessage(c.f("&c[Events] (Debug) " + msg));
	}
	
	public static void consoleMessage(String msg) {
		Bukkit.getConsoleSender().sendMessage(c.f("&a[Events] (Console Message) " + msg));
	}
	
	public static void playerOnly() {
		Bukkit.getConsoleSender().sendMessage(c.f("&a[Events] This command is for players only"));
	}

	
	public static void noPerms(Player p) {
		lang.setupLang();
		p.sendMessage(c.f(lang.getLang().getString("no-perms")));
	}
	
	public static void playerErrorMSG(Player p, String msg) {
		lang.setupLang();
		p.sendMessage(c.f(lang.getLang().getString("errorMSG").replaceAll("%MSG%", msg)));
	}
	
	public static void customMessage(Player p, String msg) {
		lang.setupLang();
		userManager userManger = plugin.userMangerHashMap.get(p);
		msg = msg.replaceAll("%tokens%", String.valueOf(userManger.getTokens()));
		p.sendMessage(c.f(msg));
	}
	
	public static void lineMessageConsole(String msg) {
		lang.setupLang();
		
		
		String rmsg = lang.getLang().getString(msg);
				
		
		Bukkit.getConsoleSender().sendMessage(c.f(rmsg));
	}
	
	public static void lineMessage(Player p, String msg) {
		lang.setupLang();
		userManager userManger = plugin.userMangerHashMap.get(p);
		
		
		String rmsg = lang.getLang().getString(msg);
		rmsg = rmsg.replaceAll("%tokens%", String.valueOf(userManger.getTokens()));
				
		
		p.sendMessage(c.f(rmsg));
	}
	
	public static void listMessage(Player p, String msg) {
		lang.setupLang();
		userManager userManger = plugin.userMangerHashMap.get(p);
		List<String> rmsg = lang.getLang().getStringList(msg);
		String tLine;
		for (String line : rmsg) {
			tLine = line;
			tLine = tLine.replaceAll("%tokens%", String.valueOf(userManger.getTokens()))
					;
			p.sendMessage(c.f(tLine));
		}				
	}
	
	public static void broadMessage(String msg) {
		lang.setupLang();
		String rmsg = lang.getLang().getString(msg);
		Bukkit.broadcastMessage(c.f(rmsg));
	}
	public static void elmWinnMessage(String msg, Player p) {
		lang.setupLang();
		String rmsg = lang.getLang().getString(msg);
		Bukkit.broadcastMessage(c.f(rmsg.replaceAll("%player%", p.getName())));
	}

	
	public static void modeEnabled(String mode) {
		lang.setupLang();
		String msg = lang.getLang().getString("mode.messages.start.enabled");
		msg = msg.replaceAll("%mode%", mode);
		Bukkit.broadcastMessage(c.f(msg));
	}
	
	public static void modeStarting(mode mode, int seconds) {
		
		
		lang.setupLang();
		String msg = lang.getLang().getString("mode.messages.start.countdown");
		msg = msg.replaceAll("%mode%", mode.getName()).replaceAll("%seconds%", String.valueOf(seconds));
		
		TextComponent jmsg = new TextComponent(c.f(msg));
		jmsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(c.f("&f&lClick to join " + mode.getName())).create()));
		jmsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/events join " + mode.getID()));
		
		if(seconds != 0) {
			msg = msg.replaceAll("%seconds%", String.valueOf(seconds));
			Bukkit.spigot().broadcast(jmsg);
			return;
		}
		msg = lang.getLang().getString("mode.messages.start.started");
		msg = msg.replaceAll("%mode%", mode.getName());
		Bukkit.broadcastMessage(c.f(msg));
	}
	
	
	
	
}
