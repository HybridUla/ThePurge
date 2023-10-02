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

public class FireHelix implements Listener {

    private final Purge plugin;


    public FireHelix(Purge plugin){
        this.plugin = plugin;
    }

    public static ArrayList<UUID> FireHelix1 = new ArrayList();


    public static void FireHelix(Player p)
    {
        if (FireHelix1.contains(p.getPlayer().getUniqueId())) {
            new BukkitRunnable()
            {
                public void run()
                {
                    if (!FireHelix1.contains(p.getPlayer().getUniqueId())) {
                        cancel();
                    }
                    double phi = 0.0D;

                    phi += 0.39269908169872414D;

                    Location loc = p.getLocation();
                    for (double t = 0.0D; t <= 6.283185307179586D; t += 0.19634954084936207D) {
                        for (double i = 0.0D; i <= 1.0D; i += 1.0D)
                        {
                            double x = 0.3D * (6.283185307179586D - t) * 0.5D * Math.cos(t + phi + i * 3.141592653589793D);
                            double y = 0.5D * t;
                            double z = 0.3D * (6.283185307179586D - t) * 0.5D * Math.sin(t + phi + i * 3.141592653589793D);
                            loc.add(x, y, z);
                            p.getWorld().spawnParticle(Particle.FLAME,loc,0);
                            // p.getWorld().spawnParticle(Particle.FLAME,loc, 3,0.0F, 0.0F, 0.0F, 0.0F,30.0D);
                            loc.subtract(x, y, z);
                        }
                    }
                }
            }.runTaskTimer(Purge.getInstance(), 0L, 20L);
        }
    }


    @EventHandler
    public static void Quit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        FireHelix1.remove(p.getPlayer().getUniqueId());

    }

}
