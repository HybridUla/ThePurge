package com.ula.purge.adminutils;

import com.ula.purge.Purge;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;


import static com.ula.purge.Purge.np;

public class gamemode implements Listener {

    private final Purge plugin;

    public gamemode(Purge plugin) {
        this.plugin = plugin;
    }

    public static String staffG = ChatColor.translateAlternateColorCodes('&', "&4Staff Gamemode");

    public static void staffGamemode(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, staffG);

        ItemStack creativeItem = createMenuItem("Creative", ChatColor.GREEN + "" + ChatColor.BOLD + "CREATIVE");
        ItemStack survivalItem = createMenuItem("Survival", ChatColor.GREEN + "" + ChatColor.BOLD + "SURVIVAL");
        ItemStack spectatorItem = createMenuItem("Spectator", ChatColor.GREEN + "" + ChatColor.BOLD + "SPECTATOR");
        ItemStack adventureItem = createMenuItem("Adventure", ChatColor.GREEN + "" + ChatColor.BOLD + "ADVENTURE");
        ItemStack goBackItem = createMenuItem("Go Back", ChatColor.RED + "" + ChatColor.BOLD + "Go Back");

        inv.setItem(0, creativeItem);
        inv.setItem(1, survivalItem);
        inv.setItem(2, spectatorItem);
        inv.setItem(3, adventureItem);
        inv.setItem(8, goBackItem);

        p.openInventory(inv);
    }

    private static ItemStack createMenuItem(String itemName, String displayName) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        item.setItemMeta(itemMeta);
        return item;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null || e.getView().getTitle() == null || !e.getView().getTitle().equals(staffG)) {
            return;
        }

        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) {
            return;
        }

        e.setCancelled(true);

        String displayName = clickedItem.getItemMeta().getDisplayName();
        if (displayName.equals(ChatColor.GREEN + "" + ChatColor.BOLD + "CREATIVE")) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
            p.setGameMode(GameMode.CREATIVE);
            p.sendMessage(np + ChatColor.GREEN + "" + ChatColor.BOLD + "Creative mode enabled");
        } else if (displayName.equals(ChatColor.GREEN + "" + ChatColor.BOLD + "SURVIVAL")) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
            p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage(np + ChatColor.GREEN + "" + ChatColor.BOLD + "Survival mode enabled");
        } else if (displayName.equals(ChatColor.GREEN + "" + ChatColor.BOLD + "SPECTATOR")) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(np + ChatColor.GREEN + "" + ChatColor.BOLD + "Spectator mode enabled");
        } else if (displayName.equals(ChatColor.GREEN + "" + ChatColor.BOLD + "ADVENTURE")) {
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
            p.setGameMode(GameMode.ADVENTURE);
            p.sendMessage(np + ChatColor.GREEN + "" + ChatColor.BOLD + "Adventure mode enabled");
        } else if (displayName.equals(ChatColor.RED + "" + ChatColor.BOLD + "Go Back")) {
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
            staffmenu.staffMenu(p);
        }
    }
}