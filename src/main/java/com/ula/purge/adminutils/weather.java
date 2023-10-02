package com.ula.purge.adminutils;

import com.ula.purge.Purge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static com.ula.purge.Purge.np;

public class weather implements Listener {

    private final Purge plugin;
    public weather(Purge plugin){

        this.plugin = plugin;
    }

    public static String staffW = ChatColor.translateAlternateColorCodes('&',"&4Staff Weather");

    public static void staffW(Player p){

        Inventory inv = Bukkit.createInventory(null, 9, staffW);

        ItemStack Emerald = new ItemStack(Material.PAPER);
        ItemMeta EmeraldMeta = Emerald.getItemMeta();
        EmeraldMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "CLEAR");
        Emerald.setItemMeta(EmeraldMeta);

        ItemStack RC = new ItemStack(Material.PAPER);
        ItemMeta RCMeta = RC.getItemMeta();
        RCMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "RAIN");
        RC.setItemMeta(RCMeta);

        ItemStack Particles = new ItemStack(Material.PAPER);
        ItemMeta partMeta = Particles.getItemMeta();
        partMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "THUNDER");
        Particles.setItemMeta(partMeta);


        ItemStack gb = new ItemStack(Material.BARRIER);
        ItemMeta gbmeta = gb.getItemMeta();
        gbmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Go Back");
        gb.setItemMeta(gbmeta);


        inv.setItem(0, Emerald);
        inv.setItem(1, RC);
        inv.setItem(2, Particles);
        inv.setItem(8,gb);

        p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void ItemClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }
        final String invTitle = e.getView().getTitle();
        if (invTitle == null || !invTitle.equals(staffW)) {
            return;
        }

        final org.bukkit.inventory.ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Go Back")) {
            e.setCancelled(true);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1F,1F);
            staffmenu.staffMenu(p);
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "CLEAR")) {
            e.setCancelled(true);
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1F,1F);
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.getWorld().setStorm(false);
            }
            Bukkit.getServer().broadcastMessage(np + "" + p.getName() + " Changed the weather to Clear");
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "RAIN")) {
            e.setCancelled(true);
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1F,1F);
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.getWorld().setThundering(false);
                all.getWorld().setStorm(true);
            }
            Bukkit.getServer().broadcastMessage(np + "" + p.getName() + " Changed the weather to Rain");
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "THUNDER")) {
            e.setCancelled(true);
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1F,1F);
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.getWorld().setThundering(true);
                all.getWorld().setStorm(true);
            }
            Bukkit.getServer().broadcastMessage(np + "" + p.getName() + " Changed the weather to Thunder");
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Go Back")) {
            e.setCancelled(true);
            p.closeInventory();
            staffmenu.staffMenu(p);
        }
    }
}
