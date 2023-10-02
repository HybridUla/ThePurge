package com.ula.purge.UserTools;

import com.ula.purge.Purge;
import com.ula.purge.adminutils.staffmenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class userclick implements Listener {
    private final Purge plugin;
    public userclick (Purge plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void ItemClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }
        final String invTitle = e.getView().getTitle();
        if (invTitle == null || !invTitle.equals(ChatColor.GREEN + "Welcome, " + p.getPlayer().getDisplayName())) {
            return;
        }

        final org.bukkit.inventory.ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§fRTP")) {
            e.setCancelled(true);
            if(plugin.econ.getBalance(p.getPlayer()) < 500){
                p.sendMessage("Sorry you do not have the correct balance to use this!");
                p.closeInventory();
            }else{
                p.closeInventory();
                Bukkit.dispatchCommand(p,"rtp");
                Purge.econ.withdrawPlayer(p, 500);
                p.sendMessage("We have subtracted $500 from your balance, you have: " + Purge.econ.getBalance(p.getPlayer() + " Left"));
            }
        }

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§fDynMap")) {
            e.setCancelled(true);
            p.closeInventory();
            p.sendMessage("Click here to look at the map: 24.14.116.245:25569");
        }

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§fSHOP")) {
            e.setCancelled(true);
            Bukkit.dispatchCommand(p,"SHOP");

        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§fXPBoost")) {
            e.setCancelled(true);
            Bukkit.dispatchCommand(p,"xpboost gui");
        }

        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + p.getName())) {
            e.setCancelled(true);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§fAdmin Tools")) {
            e.setCancelled(true);
            staffmenu.staffMenu(p);
        }

    }
}
