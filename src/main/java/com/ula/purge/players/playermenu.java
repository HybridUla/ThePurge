package com.ula.purge.players;

import com.ula.purge.Purge;
import com.ula.purge.adminutils.staffmenu;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.UUID;

public class playermenu implements Listener {

    private final Purge plugin;
    private String selectedPlayerName;
    private Player finalSelectedPlayer;
    private Inventory playerInteractionMenu;

    public playermenu(Purge plugin) {
        this.plugin = plugin;
    }

    public static void playermenu(Player p) {
        int size = Bukkit.getOnlinePlayers().size();
        while (size % 9 != 0) {
            size++;
        }
        Inventory inv = Bukkit.createInventory(null, size, ChatColor.GREEN + "ONLINE PLAYERS");

        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) stack.getItemMeta();
            meta.setOwnerProfile(player.getPlayerProfile());
            meta.setDisplayName(player.getDisplayName());
            meta.setLore(Collections.singletonList("Choose What to do with this player"));
            stack.setItemMeta(meta);
            inv.setItem(i, stack);
            i++;
        }
        p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();

        // Check if the clicked inventory is not null and is the top inventory
        if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {

            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                finalSelectedPlayer = Bukkit.getPlayerExact(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                event.setCancelled(true);
                if (finalSelectedPlayer != null) {
                    selectedPlayerName = finalSelectedPlayer.getName();

                    // Initialize playerInteractionMenu if it's null
                    if (playerInteractionMenu == null) {
                        playerInteractionMenu = Bukkit.createInventory(null, 9, ChatColor.GOLD + selectedPlayerName);

                        // Add items to the playerInteractionMenu here
                        // Example: Create and add teleport, kick, freeze, and unfreeze items
                        initializePlayerInteractionMenu(playerInteractionMenu);
                    }

                    // Open the new inventory for the player
                    p.openInventory(playerInteractionMenu);
                }
            } else if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
                if (itemMeta != null && itemMeta.hasDisplayName()) {
                    String itemName = itemMeta.getDisplayName();

                    if (itemName.equals(ChatColor.GREEN + "Teleport to Player")) {
                        event.setCancelled(true);

                        // Handle teleport
                        p.closeInventory();
                        p.sendMessage(ChatColor.GREEN + "Teleporting...");

                        if (finalSelectedPlayer != null) {
                            // Teleport the player to the finalSelectedPlayer's location
                            p.teleport(finalSelectedPlayer.getLocation());
                            p.sendMessage(ChatColor.GREEN + "You have been teleported to " + finalSelectedPlayer.getName());
                        } else {
                            p.sendMessage(ChatColor.RED + "Player not found.");
                        }
                    } else if (itemName.equals(ChatColor.RED + "Kick Player")) {
                        event.setCancelled(true);

                        // Handle kick
                        p.closeInventory();
                        p.sendMessage(ChatColor.RED + "Kicking...");

                        if (finalSelectedPlayer != null) {
                            // Kick the selected player from the server
                            finalSelectedPlayer.kickPlayer(ChatColor.RED + "You have been kicked from the server.");
                            p.sendMessage(ChatColor.RED + "You kicked " + finalSelectedPlayer.getName() + " from the server.");
                        } else {
                            p.sendMessage(ChatColor.RED + "Player not found.");
                        }
                    } else if (itemName.equals(ChatColor.AQUA + "Unfreeze Player") || itemName.equals(ChatColor.BLUE + "Freeze Player")) {
                        // Toggle freeze/unfreeze
                        p.closeInventory();
                        event.setCancelled(true);


                        if (finalSelectedPlayer != null) {
                            if (finalSelectedPlayer.hasMetadata("frozen")) {
                                // Unfreeze the player
                                freezePlayer(finalSelectedPlayer, false);
                                finalSelectedPlayer.removeMetadata("frozen", plugin);
                                p.sendMessage(ChatColor.AQUA + finalSelectedPlayer.getName() + " has been unfrozen.");
                            } else {
                                // Freeze the player
                                freezePlayer(finalSelectedPlayer, true);
                                finalSelectedPlayer.setMetadata("frozen", new FixedMetadataValue(plugin, true));
                                p.sendMessage(ChatColor.BLUE + finalSelectedPlayer.getName() + " has been frozen.");
                            }

                            // Update the freeze/unfreeze item display
                            initializePlayerInteractionMenu(playerInteractionMenu);
                        } else {
                            p.sendMessage(ChatColor.RED + "Player not found.");
                        }
                    } else if (itemName.equals(ChatColor.YELLOW + "Teleport Player to You")) {
                        // Handle teleport-to-you
                        event.setCancelled(true);

                        p.closeInventory();
                        p.sendMessage(ChatColor.YELLOW + "Teleporting player to you...");

                        if (finalSelectedPlayer != null) {
                            // Teleport the selected player to your location
                            finalSelectedPlayer.teleport(p.getLocation());
                            p.sendMessage(ChatColor.YELLOW + finalSelectedPlayer.getName() + " has been teleported to you.");
                        } else {
                            p.sendMessage(ChatColor.RED + "Player not found.");
                        }
                    } else if (itemName.equals(ChatColor.RED + "" + ChatColor.BOLD + "Go Back")) {
                        staffmenu.staffMenu(p);
                    } else if (itemName.equals(ChatColor.GREEN + "Toggle Whitelist")) {
                        handleDiamondItemClick(finalSelectedPlayer);
                    }
                }
            }
        }
    }

    private void handleDiamondItemClick(Player player) {
        if (finalSelectedPlayer != null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(finalSelectedPlayer.getUniqueId());
            try {
                if (Bukkit.getWhitelistedPlayers().contains(offlinePlayer)) {
                    Bukkit.getWhitelistedPlayers().remove(offlinePlayer);
                    player.sendMessage(ChatColor.AQUA + "Removed " + finalSelectedPlayer.getName() + " from the whitelist.");
                } else {
                    Bukkit.getWhitelistedPlayers().add(offlinePlayer);
                    player.sendMessage(ChatColor.AQUA + "Added " + finalSelectedPlayer.getName() + " to the whitelist.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(ChatColor.RED + "An error occurred while modifying the whitelist.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Player not found.");
        }
    }

    // Helper method to initialize the playerInteractionMenu
    private void initializePlayerInteractionMenu(Inventory menu) {
        ItemStack teleportItem = new ItemStack(Material.ENDER_PEARL);
        ItemMeta teleportMeta = teleportItem.getItemMeta();
        teleportMeta.setDisplayName(ChatColor.GREEN + "Teleport to Player");
        teleportItem.setItemMeta(teleportMeta);
        menu.setItem(0, teleportItem);

        ItemStack kickItem = new ItemStack(Material.IRON_BOOTS);
        ItemMeta kickMeta = kickItem.getItemMeta();
        kickMeta.setDisplayName(ChatColor.RED + "Kick Player");
        kickItem.setItemMeta(kickMeta);
        menu.setItem(1, kickItem);

        ItemStack teleportToYouItem = new ItemStack(Material.COMPASS);
        ItemMeta teleportToYouMeta = teleportToYouItem.getItemMeta();
        teleportToYouMeta.setDisplayName(ChatColor.YELLOW + "Teleport Player to You");
        teleportToYouItem.setItemMeta(teleportToYouMeta);
        menu.setItem(3, teleportToYouItem);

        ItemStack whitelistToggleItem = new ItemStack(Material.DIAMOND); // You can use a different item if you prefer
        ItemMeta whitelistToggleMeta = whitelistToggleItem.getItemMeta();
        whitelistToggleMeta.setDisplayName(ChatColor.GREEN + "Toggle Whitelist");
        whitelistToggleItem.setItemMeta(whitelistToggleMeta);
        menu.setItem(4, whitelistToggleItem);

        ItemStack gb = new ItemStack(Material.BARRIER);
        ItemMeta gbmeta = gb.getItemMeta();
        gbmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Go Back");
        gb.setItemMeta(gbmeta);
        menu.setItem(8, gb);

        ItemStack freezeUnfreezeItem = new ItemStack(Material.ICE); // You can use a different item if you prefer
        ItemMeta freezeUnfreezeMeta = freezeUnfreezeItem.getItemMeta();
        boolean isFrozen = finalSelectedPlayer != null && finalSelectedPlayer.hasMetadata("frozen");

        if (isFrozen) {
            freezeUnfreezeMeta.setDisplayName(ChatColor.AQUA + "Unfreeze Player");
        } else {
            freezeUnfreezeMeta.setDisplayName(ChatColor.BLUE + "Freeze Player");
        }

        freezeUnfreezeItem.setItemMeta(freezeUnfreezeMeta);
        menu.setItem(2, freezeUnfreezeItem);
    }

    private void freezePlayer(Player player, boolean freeze) {
        if (freeze) {
            player.setWalkSpeed(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128));
        } else {
            player.setWalkSpeed(0.2f);
            player.removePotionEffect(PotionEffectType.JUMP);
        }
    }
}
