package com.ula.purge.ParticleMenu;

import com.ula.purge.Purge;
import com.ula.purge.adminutils.staffmenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.ula.purge.ParticleMenu.FireHelix.FireHelix1;
import static com.ula.purge.ParticleMenu.WitchHelix.WitchHelix1;

public class ParticleMenu implements Listener {

    private final Purge plugin;

    public ParticleMenu(Purge plugin)
    {
        this.plugin = plugin;
    }

    String Prefix = "§8Hub> ";


    public static void ParticleFire(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, 54, "§2Particles");

        ItemStack WP = createItem(Material.WATER_BUCKET, "§fWater Particles");
        ItemStack WH = createItem(Material.LAPIS_LAZULI, "§aWater Helix");
        ItemStack WL = createItem(Material.POTION, "§aWater Lord");

        ItemStack AP = createItem(Material.FEATHER, "§fAir Particles");
        ItemStack AH = createItem(Material.SNOWBALL, "§aAir Helix");
        ItemStack AL = createItem(Material.FIRE_CHARGE, "§aAir Lord");

        ItemStack FP = createItem(Material.BLAZE_ROD, "§fFire Particles");
        ItemStack FH = createItem(Material.BLAZE_POWDER, "§aFire Helix");
        ItemStack FL = createItem(Material.FIRE_CHARGE, "§aFire Lord");

        ItemStack WitchH = createItem(Material.WITCH_SPAWN_EGG, "§aWitch Helix");
        ItemStack c = createItem(Material.BARRIER, "§4Cancel Effects");

        inv.setItem(11, WP);
        inv.setItem(20, WH);
        inv.setItem(29, WL);
        inv.setItem(13, FP);
        inv.setItem(22, FH);
        inv.setItem(31, FL);
        inv.setItem(15, AP);
        inv.setItem(24, AH);
        inv.setItem(33, AL);
        inv.setItem(34, WitchH);
        inv.setItem(49, c);

        ItemStack gb = createItem(Material.BARRIER, ChatColor.RED + "" + ChatColor.BOLD + "Go Back");
        inv.setItem(53, gb);

        p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void ItemClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }
        final String invTitle = e.getView().getTitle();
        if (invTitle == null || !invTitle.equals("§2Particles")) {
            return;
        }

        final ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§fWater Particles")) {
            e.setCancelled(true);
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Go Back")) {
            e.setCancelled(true);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
            staffmenu.staffMenu(p);
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§4Cancel Effects")) {
            e.setCancelled(true);
            WaterHelix.WaterHelix.remove(p.getPlayer().getUniqueId());
            WaterLord.WaterLord1.remove(p.getPlayer().getUniqueId());
            FireHelix1.remove(p.getPlayer().getUniqueId());
            FireLord.FireLord1.remove(p.getPlayer().getUniqueId());
            AirHelix.AirHelix1.remove(p.getPlayer().getUniqueId());
            AirLord.AirLord1.remove(p.getPlayer().getUniqueId());
            WitchHelix1.remove(p.getPlayer().getUniqueId());
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aWater Helix")) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(Prefix + "Activated §aWater Helix");
            WaterHelix.WaterHelix.add(e.getWhoClicked().getUniqueId());
            if (WaterHelix.WaterHelix.contains(p.getUniqueId())) {
                WaterHelix.WaterHelix(p);
            }
            FireLord.FireLord1.remove(p.getPlayer().getUniqueId());
            WaterLord.WaterLord1.remove(p.getPlayer().getUniqueId());
            FireHelix1.remove(p.getPlayer().getUniqueId());
            AirHelix.AirHelix1.remove(p.getPlayer().getUniqueId());
            AirLord.AirLord1.remove(p.getPlayer().getUniqueId());
            WitchHelix1.remove(p.getPlayer().getUniqueId());
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aWater Lord")) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(Prefix + "Activated §aWater Lord");
            WaterLord.WaterLord1.add(e.getWhoClicked().getUniqueId());
            if (WaterLord.WaterLord1.contains(p.getUniqueId())) {
                WaterLord.WaterLord(p);
            }
            FireLord.FireLord1.remove(p.getPlayer().getUniqueId());
            WaterHelix.WaterHelix.remove(p.getPlayer().getUniqueId());
            FireHelix1.remove(p.getPlayer().getUniqueId());
            AirHelix.AirHelix1.remove(p.getPlayer().getUniqueId());
            AirLord.AirLord1.remove(p.getPlayer().getUniqueId());
            WitchHelix1.remove(p.getPlayer().getUniqueId());
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§fFire Particles")) {
            e.setCancelled(true);
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aFire Helix")) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(Prefix + "Activated §aFire Helix");
            FireHelix1.add(e.getWhoClicked().getUniqueId());
            if (FireHelix1.contains(p.getUniqueId())) {
                FireHelix.FireHelix(p);
            }
            WaterHelix.WaterHelix.remove(p.getPlayer().getUniqueId());
            WaterLord.WaterLord1.remove(p.getPlayer().getUniqueId());
            FireLord.FireLord1.remove(p.getPlayer().getUniqueId());
            AirHelix.AirHelix1.remove(p.getPlayer().getUniqueId());
            AirLord.AirLord1.remove(p.getPlayer().getUniqueId());
            WitchHelix1.remove(p.getPlayer().getUniqueId());
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aFire Lord")) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(Prefix + "Activated §aFire Helix");
            FireLord.FireLord1.add(e.getWhoClicked().getUniqueId());
            if (FireLord.FireLord1.contains(p.getUniqueId())) {
                FireLord.FireLord(p);
            }
            WaterHelix.WaterHelix.remove(p.getPlayer().getUniqueId());
            WaterLord.WaterLord1.remove(p.getPlayer().getUniqueId());
            FireHelix1.remove(p.getPlayer().getUniqueId());
            AirHelix.AirHelix1.remove(p.getPlayer().getUniqueId());
            AirLord.AirLord1.remove(p.getPlayer().getUniqueId());
            WitchHelix1.remove(p.getPlayer().getUniqueId());
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§fAir Particles")) {
            e.setCancelled(true);
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aAir Helix")) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(Prefix + "Activated §aFire Helix");
            AirHelix.AirHelix1.add(e.getWhoClicked().getUniqueId());
            if (AirHelix.AirHelix1.contains(p.getUniqueId())) {
                AirHelix.AirHelix(p);
            }
            WaterHelix.WaterHelix.remove(p.getPlayer().getUniqueId());
            WaterLord.WaterLord1.remove(p.getPlayer().getUniqueId());
            FireHelix1.remove(p.getPlayer().getUniqueId());
            FireLord.FireLord1.remove(p.getPlayer().getUniqueId());
            AirLord.AirLord1.remove(p.getPlayer().getUniqueId());
            WitchHelix1.remove(p.getPlayer().getUniqueId());
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aAir Lord")) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(Prefix + "Activated §aFire Helix");
            AirLord.AirLord1.add(e.getWhoClicked().getUniqueId());
            if (AirLord.AirLord1.contains(p.getUniqueId())) {
                AirLord.AirLord(p);
            }
            WaterHelix.WaterHelix.remove(p.getPlayer().getUniqueId());
            WaterLord.WaterLord1.remove(p.getPlayer().getUniqueId());
            FireHelix1.remove(p.getPlayer().getUniqueId());
            FireLord.FireLord1.remove(p.getPlayer().getUniqueId());
            AirHelix.AirHelix1.remove(p.getPlayer().getUniqueId());
            WitchHelix1.remove(p.getPlayer().getUniqueId());
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§aWitch Helix")) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage(Prefix + "Activated §aWitch Helix");
            WitchHelix.WitchHelix1.add(e.getWhoClicked().getUniqueId());
            if (WitchHelix.WitchHelix1.contains(p.getUniqueId())) {
                WitchHelix.WitchHelix(p);
            }
            AirLord.AirLord1.add(e.getWhoClicked().getUniqueId());
            WaterHelix.WaterHelix.remove(p.getPlayer().getUniqueId());
            WaterLord.WaterLord1.remove(p.getPlayer().getUniqueId());
            FireHelix1.remove(p.getPlayer().getUniqueId());
            FireLord.FireLord1.remove(p.getPlayer().getUniqueId());
            AirHelix.AirHelix1.remove(p.getPlayer().getUniqueId());
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§4LOCKED")) {
            e.setCancelled(true);
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 0.5F, 0.5F);
            p.sendMessage("\n" +
                    " §a§l§m=============================================§r\n" +
                    "             §k§l;;;§r §r§4ERROR§r §k§l;;;§r\n" +
                    " \n" +
                    "    §fInsufficient Permissions\n" +
                    "    §fRank required:\n" +
                    "    §3§lWATER §8- §f§lWIND §8- §4§lFIRE\n" +
                    "   §fwww.thevoidmc.net\n" +
                    " \n" +
                    " \n" +
                    " §a§l§m=============================================§r\n");
        }
    }

    private static ItemStack createItem(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }


}