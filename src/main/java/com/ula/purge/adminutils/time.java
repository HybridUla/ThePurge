package com.ula.purge.adminutils;

import com.ula.purge.Purge;
import org.bukkit.Bukkit;
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

public class time implements Listener {

    private final Purge plugin;

    public time(Purge plugin) {
        this.plugin = plugin;
    }

    public static String staffTimeTitle = "§4Staff Time";

    public static void staffTimer(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, staffTimeTitle);

        ItemStack morningItem = createMenuItem(Material.PAPER, "§a§lMORNING");
        ItemStack noonItem = createMenuItem(Material.PAPER, "§a§lNOON");
        ItemStack sunsetItem = createMenuItem(Material.PAPER, "§a§lSUNSET");
        ItemStack nightItem = createMenuItem(Material.PAPER, "§a§lNIGHT");
        ItemStack goBackItem = createMenuItem(Material.BARRIER, "§c§lGo Back");

        inv.setItem(0, morningItem);
        inv.setItem(1, noonItem);
        inv.setItem(2, sunsetItem);
        inv.setItem(3, nightItem);
        inv.setItem(8, goBackItem);

        p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }
        final String invTitle = e.getView().getTitle();
        if (invTitle == null || !invTitle.equals(staffTimeTitle)) {
            return;
        }

        final ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null || e.getCurrentItem().getType() == Material.AIR || !itemStack.hasItemMeta()) {
            return;
        }


        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            String itemName = itemStack.getItemMeta().getDisplayName();
            e.setCancelled(true);


            if (itemName.equals("§c§lGo Back")) {
                e.setCancelled(true);

                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                staffmenu.staffMenu(p);
            } else {
                handleTimeChange(p, itemName);
            }
        }
    }

    private void handleTimeChange(Player player, String time) {
        long timeTicks = 0;

        switch (time) {
            case "§a§lMORNING":
                timeTicks = 0;
                break;
            case "§a§lNOON":
                timeTicks = 6000;
                break;
            case "§a§lSUNSET":
                timeTicks = 12000;
                break;
            case "§a§lNIGHT":
                timeTicks = 18000;
                break;
        }

        player.closeInventory();
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.getWorld().setTime(timeTicks);
        }

        Bukkit.getServer().broadcastMessage(np + player.getName() + " changed the time to " + time);
    }

    private static ItemStack createMenuItem(Material material, String displayName) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
