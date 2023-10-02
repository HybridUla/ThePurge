package com.ula.purge.adminutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.ula.purge.Purge.np;
import static org.bukkit.Bukkit.getServer;

public class broadcast implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        // Check if the player has the required permission (e.g., "myplugin.broadcast")
        if (!player.hasPermission("purge.broadcast")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /broadcast <message>");
            return true;
        }

        String message = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));

        // Broadcast the message to all players online
        Bukkit.broadcastMessage(np + "" + ChatColor.RESET + message);
        return true;
    }
}