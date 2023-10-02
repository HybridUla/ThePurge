package com.ula.purge.players;

import com.nametagedit.plugin.NametagEdit;
import com.ula.purge.Purge;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class playermention implements Listener {

    private final Purge plugin;
    public playermention(Purge plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        // Get the player who sent the message
        String playerName = event.getPlayer().getName();

        // Get the message sent by the player
        String message = event.getMessage();

        // Check if the message contains the player's full display name (case-insensitive)
        if (message.contains(playerName) || message.contains(ChatColor.stripColor(playerName))) {
            // Play the "ding" sound for the player
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }
    }

}
