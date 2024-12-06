package me.mchiappinam.pdghmagnata;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class Listeners implements Listener {
	
	private Main plugin;
	public Listeners(Main main) {
		plugin=main;
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onJoin(final PlayerJoinEvent e) {
		if ((!plugin.jaDivulgou) && (plugin.getConfig().getString("magnata.nick").contains(e.getPlayer().getName().toLowerCase()))) {
			plugin.jaDivulgou=true;
			plugin.getServer().broadcastMessage(" ");
			plugin.getServer().broadcastMessage("§2§l[Magnata]§2 "+e.getPlayer().getName()+" §alogou-se.");
			plugin.getServer().broadcastMessage(" ");
			return;
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	private void onChat(ChatMessageEvent e) {
		if(plugin.getConfig().getString("magnata.nick").contains(e.getSender().getName().toLowerCase()))
			e.setTagValue("magnata", "§2[Magnata]");
	}
}