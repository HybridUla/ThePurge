package com.ula.purge.players;

import com.ula.purge.Purge;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static com.ula.purge.Purge.np;

public class DoubleXP implements Listener {
    private final JavaPlugin plugin;
    public final LocalDateTime startTime;
    public final LocalDateTime endTime;

    public DoubleXP(JavaPlugin plugin) {
        this.plugin = plugin;

        // Define the start and end times for double XP
        // Example: Double XP starts on January 1, 2024, at 10:00 AM and ends on January 2, 2024, at 10:00 AM
        this.startTime = LocalDateTime.of(2023, Month.SEPTEMBER, 29, 10, 0);
        this.endTime = LocalDateTime.of(2023, Month.OCTOBER, 2, 10, 0);

        // Register this class as an event listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        plugin.getLogger().info("Player " + player.getName() + " gained " + event.getAmount() + " XP.");

        // Check if it's currently within the double XP period
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(startTime) && now.isBefore(endTime)) {
            // Double the experience gained
            event.setAmount(event.getAmount() * 2);

        }
    }


}
