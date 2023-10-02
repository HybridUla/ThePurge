package com.ula.purge;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class AnvilXPModifier implements Listener {
    private final Purge plugin;
    public AnvilXPModifier(Purge plugin){
        this.plugin = plugin;
    }

    int newXPCap = 9999999; // Set your desired new XP cap here

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack left = event.getInventory().getItem(0);
        ItemStack right = event.getInventory().getItem(1);

        if (left != null && right != null) {
            // Check if the items are compatible for an enchantment transfer
            if (canTransferEnchantment(left, right)) {
                // Set the new XP cap
                event.getInventory().setMaximumRepairCost(newXPCap);
            }
        }
    }

    private boolean canTransferEnchantment(ItemStack item1, ItemStack item2) {
        // Check if item1 is enchanted and can transfer an enchantment to item2
        return item1.getEnchantments().size() > 0 &&
                item1.getType() != Material.ENCHANTED_BOOK &&
                item2.getType() != Material.ENCHANTED_BOOK;
    }
}
