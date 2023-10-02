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
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import static com.ula.purge.Purge.np;
import static org.bukkit.Bukkit.getServer;

public class maintenance implements Listener {

    private final Purge plugin;

    public maintenance(Purge plugin) {
        this.plugin = plugin;
    }

    public boolean isListMaintenance = false;
    public static String inventoryTitle = ChatColor.translateAlternateColorCodes('&', "&4Maintenance Mode Menu");

    public static void openMaintenanceMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, inventoryTitle);

        ItemStack startMaintenance = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta startMaintenanceMeta = startMaintenance.getItemMeta();
        startMaintenanceMeta.setDisplayName(ChatColor.GREEN + "Start Maintenance Mode");
        startMaintenance.setItemMeta(startMaintenanceMeta);

        ItemStack stopMaintenance = new ItemStack(Material.CLAY);
        ItemMeta stopMaintenanceMeta = stopMaintenance.getItemMeta();
        stopMaintenanceMeta.setDisplayName("§c§lStop Maintenance Mode");
        stopMaintenance.setItemMeta(stopMaintenanceMeta);

        ItemStack goBack = new ItemStack(Material.BARRIER);
        ItemMeta goBackMeta = goBack.getItemMeta();
        goBackMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Go Back");
        goBack.setItemMeta(goBackMeta);

        inventory.setItem(0, startMaintenance);
        inventory.setItem(2, stopMaintenance);
        inventory.setItem(8, goBack);

        player.openInventory(inventory);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory() == null || event.getClickedInventory() == null) {
            return;
        }

        String inventoryTitle = event.getView().getTitle();

        if (inventoryTitle == null || !inventoryTitle.equals(inventoryTitle)) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR || !clickedItem.hasItemMeta()) {
            return;
        }


        if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Go Back")) {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
            staffmenu.staffMenu(player);
            event.setCancelled(true);

        }

        if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Start Maintenance Mode")) {
            player.closeInventory();
            event.setCancelled(true);

            if (!isListMaintenance) {
                new BukkitRunnable() {
                    int countdown = 8;  // Seconds to count down from

                    @Override
                    public void run() {
                        if (countdown <= 0) {
                            // Maintenance actions here
                            Bukkit.setWhitelist(true);
                            for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                                if (onlinePlayer.isWhitelisted()) {
                                    Bukkit.broadcastMessage(np + Bukkit.getWhitelistedPlayers().size() + " whitelisted players are allowed to join the server while in Maintenance Mode, make sure that you check the list of players you want to allow to join during this session!");
                                } else {
                                    onlinePlayer.kickPlayer(np + "Server has begun its Scheduled Maintenance please check Discord for an ETA");
                                }
                            }
                            isListMaintenance = true;
                            this.cancel();
                            return;
                        } else if (countdown > 0) {
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.1F, 0.1F);
                            for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                                onlinePlayer.sendTitle("§4MaintenanceMode", "§f§l" + countdown + "s" + " §a§lUntil Maintenance starts", 0, 50, 20);
                                Bukkit.getOnlinePlayers().size();
                                countdown--;
                            }
                        }
                    }
                }.runTaskTimer(plugin, 0, 20);
            } else {
                player.sendMessage(np + "MaintenanceMode is already enabled!");
            }
        }

        if (clickedItem.getItemMeta().getDisplayName().equals("§c§lStop Maintenance Mode")) {
            player.closeInventory();
            event.setCancelled(true);

            if (isListMaintenance) {
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1F, 0.1F);
                for (Player onlinePlayer : getServer().getOnlinePlayers()) {
                    onlinePlayer.sendTitle("§4MaintenanceMode", " §a§lEnded!", 0, 50, 20);
                    Bukkit.setWhitelist(false);
                    isListMaintenance = false;
                }
            } else {
                player.sendMessage(np + "Sir, MaintenanceMode is already disabled!");
            }
        }
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        // Get the current date and time in Central Time (CT)
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Chicago"));

        // Define the start and end times for double XP
        LocalDateTime startTime = LocalDateTime.of(2023, Month.SEPTEMBER, 29, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, Month.OCTOBER, 2, 10, 0);

        // Check if it's currently within the double XP period
        boolean doubleXPActive = now.isAfter(startTime) && now.isBefore(endTime);

        // Set the MOTD based on whether double XP is active or not
        if (isListMaintenance) {
            event.setMotd("                 §5§lPHANTOM\n       §f§l>> §r  §4§lCurrently Under Maintenance §r   §f§l<<");
            event.setMaxPlayers(0);
        } else if (doubleXPActive) {
                event.setMotd("         §ksa§5§lPHANTOM§r§ksa§r  §2§l(DOUBLE XP ACTIVE)\n            §f§l>> §r  §6§lAll The Mods 0.1.11§r §r  §f§l<<");
                event.setMaxPlayers(100);
        } else {
                event.setMotd("                     §ksa§5§lPHANTOM§r§ksa§r\n           §f§l>> §r  §6§lAll The Mods 0.1.11 §r  §f§l<<");
                event.setMaxPlayers(100);

            }
        }
}
