package com.ula.purge.ParticleMenu;

import com.ula.purge.Purge;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class WitchHelix implements Listener {
    private final Purge plugin;


    public WitchHelix(Purge plugin){
        this.plugin = plugin;
    }

    public static ArrayList<UUID> WitchHelix1 = new ArrayList();


    public static void WitchHelix(Player p) {
        if (WitchHelix1.contains(p.getUniqueId())) {

            new BukkitRunnable() {
                double phi = 0.0D;
                double radius = 1.5D;
                Location playerLocation = p.getLocation();
                Location groundLocation = playerLocation.clone().subtract(0, 1, 0);

                public void run() {
                    if (!WitchHelix1.contains(p.getUniqueId())) {
                        cancel();
                        return;
                    }

                    Location loc = playerLocation.clone();
                    loc.add(radius * Math.cos(phi), 1.0D, radius * Math.sin(phi));
                    p.spawnParticle(Particle.SPELL_WITCH, loc, 1, 0, 0, 0, 0);

                    if (loc.distance(groundLocation) <= 0.1) {
                        WitchHelix1.remove(p.getUniqueId());
                        cancel();
                    }

                    phi += Math.PI / 16.0D;

                    if (phi >= 2 * Math.PI) {
                        phi = 0;
                    }
                }
            }.runTaskTimer(Purge.getInstance(), 0L, 1L);
        }
    }


    @EventHandler
    public static void Quit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        WitchHelix1.remove(p.getPlayer().getUniqueId());

    }
}
