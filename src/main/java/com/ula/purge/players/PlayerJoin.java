package com.ula.purge.players;

import com.nametagedit.plugin.NametagEdit;
import com.ula.purge.Purge;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.TimeUnit;

import static com.ula.purge.Purge.chat;
import static com.ula.purge.Purge.np;

public class PlayerJoin implements Listener {
    private final Purge plugin;
    public PlayerJoin(Purge plugin){
        this.plugin = plugin;
    }

    @EventHandler
   public void onPlayerJoinEvent(PlayerJoinEvent e){

        Player p = e.getPlayer();
        long pTime =  p.getPlayerTime();
        Location loc = p.getLocation();

        if(p.getName().equals("OnTheHorizon")){
            p.setDisplayName("The Stresser");
            Bukkit.broadcastMessage(np + "The Server Stresser has joined!");
        }else{
            NametagEdit.getApi().setPrefix(p, chat.getPlayerPrefix(p));

        }

        long onlineTimeTicks = p.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE);
        long onlineTimeSeconds = onlineTimeTicks / 20L; // Convert ticks to seconds
        long onlineTimeMinutes = onlineTimeSeconds / 60L; // Convert seconds to minutes
        long onlineTimeHours = onlineTimeMinutes / 60L; // Convert minutes to hours

        // Calculate days, hours, and minutes
        long days = onlineTimeHours / 24L;
        long hours = onlineTimeHours % 24L;
        long minutes = onlineTimeMinutes % 60L;



        p.setPlayerListHeader(" \n" +
                "§f§l§nPHANTOM SURVIVAL§r\n" +
                " \n");

        p.setPlayerListFooter("§r \n" +
                "§6§nSeason 1 Stats§r\n" + "§r     \n"+

                "§2Time Played: §f" + days + " Days " + hours + " hours " + minutes + " minutes" + "§r  \n" +
                "§2Raids Won: §f" + p.getStatistic(Statistic.RAID_WIN) +"§r   \n");
        Bukkit.getServer().setMotd("                     &5&lPHANTOM&r\\n           &f&l>> &r  &6&lAll The Mods 0.1.11&r   &f&l<<");


        p.sendMessage(
                "§9§m--------------------------------------------§r\n" +
                        " \n" +
                        " \n" +
                        "                   §f§l§nPHANTOM SURVIVAL§r                \n" +
                        " \n" +

                        "                     §6Season 1§r                      \n" +
                        " \n" +
                        " \n" +
                        "§9§m--------------------------------------------§r");
        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1F, 1F);


        p.sendTitle("§a§lPHANTOM SURVIVAL","WELCOME",0,120,0);

    }

    public static String calculateTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        return day + " days " + hours + " hours " + minute + " minutes " + second + " seconds ";

    }
}
