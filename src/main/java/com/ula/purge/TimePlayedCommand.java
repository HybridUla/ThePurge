package com.ula.purge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimePlayedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String playerName = args[0];
            Player targetPlayer = Bukkit.getPlayer(playerName);

            if (targetPlayer != null) {
                long onlineTimeTicks = targetPlayer.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE);
                long onlineTimeSeconds = onlineTimeTicks / 20L; // Convert ticks to seconds
                long onlineTimeMinutes = onlineTimeSeconds / 60L; // Convert seconds to minutes
                long onlineTimeHours = onlineTimeMinutes / 60L; // Convert minutes to hours

                // Calculate days, hours, and minutes
                long days = onlineTimeHours / 24L;
                long hours = onlineTimeHours % 24L;
                long minutes = onlineTimeMinutes % 60L;

                sender.sendMessage(playerName + "'s time played: " + days + " days, " + hours + " hours, " + minutes + " minutes.");
            } else {
                sender.sendMessage("Player not found.");
            }
        } else {
            sender.sendMessage("Usage: /timeplayed <player>");
        }

        return true;
    }
}