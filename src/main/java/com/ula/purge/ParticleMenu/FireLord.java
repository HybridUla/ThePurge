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

public class FireLord implements Listener {


    private final Purge plugin;


    public FireLord(Purge plugin){
        this.plugin = plugin;
    }


    public static ArrayList<UUID> FireLord1 = new ArrayList();


    public static void FireLord(Player p)
    {
        if (FireLord1.contains(p.getPlayer().getUniqueId())) {
            new BukkitRunnable()
            {
                double t = 0.0D;

                public void run()
                {
                    if (!FireLord.FireLord1.contains(p.getPlayer().getUniqueId())) {
                        cancel();
                    }
                    this.t += 0.39269908169872414D;
                    Location loc = p.getLocation();
                    for (double phi = 0.0D; phi <= 6.283185307179586D; phi += 1.5707963267948966D)
                    {
                        double x = 0.3D * (12.566370614359172D - this.t) * Math.cos(this.t + phi);
                        double y = 0.2D * this.t;
                        double z = 0.3D * (12.566370614359172D - this.t) * Math.sin(this.t + phi);
                        loc.add(x, y, z);
                        p.spawnParticle(Particle.FLAME,loc, 0);
                        loc.subtract(x, y, z);
                        if (this.t >= 12.566370614359172D)
                        {
                            loc.add(x, y, z);
                            p.spawnParticle(Particle.FLAME,loc, 0);

                            // p.spawnParticle(Particle.FLAME,loc, 50,0.0F, 0.0F, 0.0F, 1.0F,30.0D);
                            this.t = -4.0D;
                        }
                    }
                }
            }.runTaskTimer(Purge.getInstance(), 0L, 1L);
        }
    }

    @EventHandler
    public static void Quit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        FireLord1.remove(p.getPlayer().getUniqueId());

    }
}
