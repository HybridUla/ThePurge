package com.ula.purge.adminutils;

import com.ula.purge.ParticleMenu.ParticleMenu;
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

import java.util.List;

public class particlechooser implements Listener {

    private final Purge plugin;
    public particlechooser(Purge plugin){

        this.plugin = plugin;
    }

    public static String staffP = ChatColor.translateAlternateColorCodes('&', "&4Staff Particle Chooser");

    public static void staffParticleChooser(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, staffP);

        ItemStack emerald = createParticleItem("FIRE", ChatColor.GREEN + "" + ChatColor.BOLD + "FIRE");
        ItemStack rc = createParticleItem("AIR", ChatColor.GREEN + "" + ChatColor.BOLD + "AIR");
        ItemStack particles = createParticleItem("WATER", ChatColor.GREEN + "" + ChatColor.BOLD + "WATER");

        ItemStack gb = createMenuItem("Go Back", ChatColor.RED + "" + ChatColor.BOLD + "Go Back");

        inv.setItem(0, emerald);
        inv.setItem(1, rc);
        inv.setItem(2, particles);
        inv.setItem(8, gb);

        p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }
        final String invTitle = e.getView().getTitle();
        if (invTitle == null || !invTitle.equals(staffP)) {
            return;
        }

        final ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        final String itemDisplayName = itemStack.getItemMeta().getDisplayName();
        if (itemDisplayName != null) {
            if (itemDisplayName.equals(ChatColor.GREEN + "" + ChatColor.BOLD + "FIRE")) {
                e.setCancelled(true);
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                ParticleMenu.ParticleFire(p);
            } else if (itemDisplayName.equals(ChatColor.RED + "" + ChatColor.BOLD + "Go Back")) {
                e.setCancelled(true);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                staffmenu.staffMenu(p);
            }
        }
    }

    private static ItemStack createParticleItem(String particleType, String displayName) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        item.setItemMeta(itemMeta);
        return item;
    }

    private static ItemStack createMenuItem(String itemName, String displayName) {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        item.setItemMeta(itemMeta);
        return item;
    }
}