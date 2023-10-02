package com.ula.purge.players;

import com.nametagedit.plugin.NametagEdit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static com.ula.purge.Purge.chat;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class AFKCommand implements CommandExecutor, Listener {



    private final Map<Player, Boolean> afkStatus = new HashMap<>();
    private Scoreboard scoreboard;
    private final Set<UUID> afkPlayers = new HashSet<>();


    public AFKCommand(JavaPlugin plugin) {
        // Register the /afk command to this class as its executor
        plugin.getCommand("afk").setExecutor(this);

        // Register events for AFK handling
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        // Create a new scoreboard
        scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        // Get the server's scoreboard manager
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        if (scoreboardManager != null) {
            // Initialize the scoreboard here
            scoreboard = scoreboardManager.getNewScoreboard();
        } else {
            // Handle the case where the scoreboard manager is null
            plugin.getLogger().severe("Scoreboard manager is not available.");
            // You may want to disable your plugin gracefully or take other appropriate action.
        }


        // Initialize the AFK scoreboard team
        Team afkTeam = scoreboard.registerNewTeam("AFK");
        afkTeam.setSuffix("");
        afkTeam.setPrefix("");
        afkTeam.setNameTagVisibility(NameTagVisibility.NEVER);
        afkTeam.setDisplayName(afkTeam.getName());
    }

    public void setAFK(Player player) {
        afkPlayers.add(player.getUniqueId());
        player.setDisplayName(player.getName());
        NametagEdit.getApi().setPrefix(player, chat.getPlayerPrefix(player));
        NametagEdit.getApi().setSuffix(player, " §6§l[AFK]");
        player.setCollidable(false);


        // Debugging: Check if setDisplayName is called
        System.out.println("Set AFK for player: " + player.getName());
        System.out.println("Display Name: " + player.getDisplayName());
    }

    public void removeAFK(Player player) {
        afkPlayers.remove(player.getUniqueId());
        NametagEdit.getApi().setPrefix(player, chat.getPlayerPrefix(player));
        NametagEdit.getApi().setSuffix(player,"");

        // Debugging: Check if removeAFK is called
        System.out.println("Removed AFK for player: " + player.getName());
        System.out.println("Display Name: " + player.getDisplayName());
        player.setCollidable(true);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("afk")) {
            if (!afkPlayers.contains(player.getUniqueId())) {
                // Player is not AFK, set them as AFK
                setAFK(player);
                player.sendMessage("You are now AFK.");
                //NametagEdit.getApi().setPrefix(player,chat.getPlayerPrefix(player));
            } else {
                // Player is already AFK, clear AFK status
                removeAFK(player);
                player.sendMessage("You are no longer AFK.");

            }
        }

        return true;
    }
    private final Map<UUID, Boolean> voluntaryMovement = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // Check if the player is in AFK mode
            if (afkPlayers.contains(player.getUniqueId())) {
                // Cancel the damage event for AFK players
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Remove the player from the AFK team when they quit
        if (afkPlayers.contains(player.getUniqueId())) {
            // Player moved voluntarily, remove from AFK
            removeAFK(player);
            player.sendMessage("You are no longer AFK.");
        }
    }




    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (afkPlayers.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }

        if (event.getDamager().getType() == EntityType.PLAYER) {
            Player attacker = (Player) event.getDamager();
            if (afkPlayers.contains(attacker.getUniqueId())) {
                // If you want to handle cases where an AFK player tries to attack, you can do it here.
                // For example, you can send a message to the attacker.
                attacker.sendMessage("You are in AFK mode and cannot attack.");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            Player player = (Player) event.getTarget();
            if (afkPlayers.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Check if the player is AFK
        if (afkPlayers.contains(player.getUniqueId())) {
            // Player moved voluntarily, remove from AFK
            removeAFK(player);
            player.sendMessage("You are no longer AFK.");
        }
        // Check if the player is in AFK mode
        if (afkPlayers.contains(player.getUniqueId())) {
            // Cancel the movement event for AFK players
            event.setCancelled(true);

            // Optionally, you can notify the player that they can't move
            player.sendMessage("You are in AFK mode and cannot move.");
        }
    }
    @EventHandler
    public void onPlayerToggleGlide(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (afkPlayers.contains(player.getUniqueId())) {
                // Player is AFK, prevent collisions
                event.setCancelled(true);
            }
        }
    }
}
