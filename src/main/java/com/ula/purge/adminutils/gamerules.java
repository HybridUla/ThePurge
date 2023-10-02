package com.ula.purge.adminutils;

import com.ula.purge.ParticleMenu.ParticleMenu;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ula.purge.Purge.np;

public class gamerules implements Listener {
    private final Purge plugin;

    public gamerules(Purge plugin) {
        this.plugin = plugin;
    }

    private static final String[] GAME_RULES = {
            "ANNOUNCE_ADVANCEMENTS",
            "DO_DAYLIGHT_CYCLE",
            "DO_ENTITY_DROPS",
            "DO_FIRE_TICK",
            "DO_LIMITED_CRAFTING",
            "DO_MOB_LOOT",
            "DO_MOB_SPAWNING",
            "DO_TILE_DROPS",
            "DO_WEATHER_CYCLE",
            "DROWNING_DAMAGE",
            "FALL_DAMAGE",
            "FIRE_DAMAGE",
            "FREEZE_DAMAGE",
            "KEEP_INVENTORY",
            "MAX_COMMAND_CHAIN_LENGTH",
            "MAX_ENTITY_CRAMMING",
            "MOB_GRIEFING",
            "NATURAL_REGENERATION",
            "RANDOM_TICK_SPEED",
            "REDUCED_DEBUG_INFO",
            "SEND_COMMAND_FEEDBACK",
            "SHOW_DEATH_MESSAGES",
            "SPAWN_RADIUS",
            "SPECTATORS_GENERATE_CHUNKS",
            "TNT_EXPLODES"
    };

    private static final Map<String, GameRule<Boolean>> GAME_RULE_MAP = new HashMap<>();

    static {
        GAME_RULE_MAP.put("ANNOUNCE_ADVANCEMENTS", GameRule.ANNOUNCE_ADVANCEMENTS);
        GAME_RULE_MAP.put("DO_DAYLIGHT_CYCLE", GameRule.DO_DAYLIGHT_CYCLE);
        GAME_RULE_MAP.put("DO_ENTITY_DROPS", GameRule.DO_ENTITY_DROPS);
        GAME_RULE_MAP.put("DO_FIRE_TICK", GameRule.DO_FIRE_TICK);
        GAME_RULE_MAP.put("DO_LIMITED_CRAFTING", GameRule.DO_LIMITED_CRAFTING);
        GAME_RULE_MAP.put("DO_MOB_LOOT", GameRule.DO_MOB_LOOT);
        GAME_RULE_MAP.put("DO_MOB_SPAWNING", GameRule.DO_MOB_SPAWNING);
        GAME_RULE_MAP.put("DO_TILE_DROPS", GameRule.DO_TILE_DROPS);
        GAME_RULE_MAP.put("DO_WEATHER_CYCLE", GameRule.DO_WEATHER_CYCLE);
        GAME_RULE_MAP.put("DROWNING_DAMAGE", GameRule.DROWNING_DAMAGE);
        GAME_RULE_MAP.put("FALL_DAMAGE", GameRule.FALL_DAMAGE);
        GAME_RULE_MAP.put("FIRE_DAMAGE", GameRule.FIRE_DAMAGE);
        GAME_RULE_MAP.put("FREEZE_DAMAGE", GameRule.FREEZE_DAMAGE);
        GAME_RULE_MAP.put("KEEP_INVENTORY", GameRule.KEEP_INVENTORY);
        GAME_RULE_MAP.put("MOB_GRIEFING", GameRule.MOB_GRIEFING);
        GAME_RULE_MAP.put("NATURAL_REGENERATION", GameRule.NATURAL_REGENERATION);
        GAME_RULE_MAP.put("REDUCED_DEBUG_INFO", GameRule.REDUCED_DEBUG_INFO);
        GAME_RULE_MAP.put("SEND_COMMAND_FEEDBACK", GameRule.SEND_COMMAND_FEEDBACK);
        GAME_RULE_MAP.put("SHOW_DEATH_MESSAGES", GameRule.SHOW_DEATH_MESSAGES);
        GAME_RULE_MAP.put("SPECTATORS_GENERATE_CHUNKS", GameRule.SPECTATORS_GENERATE_CHUNKS);
        GAME_RULE_MAP.put("TNT_EXPLODES", GameRule.TNT_EXPLOSION_DROP_DECAY);
    }

    public static void showGameRules(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GREEN + "Game Rules");

        for (int i = 0; i < GAME_RULES.length; i++) {
            String gameRuleName = GAME_RULES[i];
            GameRule<Boolean> gameRule = GAME_RULE_MAP.get(gameRuleName);

            if (gameRule != null) {
                String displayName = ChatColor.GOLD + gameRuleName;
                boolean value = Bukkit.getServer().getWorld("New World").getGameRuleValue(gameRule);
                ChatColor valueColor = value ? ChatColor.GREEN : ChatColor.RED;

                Material woolColor = value ? Material.GREEN_WOOL : Material.RED_WOOL;

                ItemStack item = createGameRuleItem(displayName, valueColor + "Value: " + value, woolColor);
                inv.setItem(i, item);
            } else {
                // Handle the case where the game rule does not exist
                ItemStack item = createGameRuleItem(ChatColor.RED + "Invalid Game Rule", ChatColor.RED + "Value: N/A", Material.RED_WOOL);
                inv.setItem(i, item);
            }
        }

        ItemStack goBackItem = createMenuItem("Go Back", ChatColor.RED + "" + ChatColor.BOLD + "Go Back");
        inv.setItem(26, goBackItem);

        p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }
        final String invTitle = e.getView().getTitle();
        if (invTitle == null || !invTitle.equals(ChatColor.GREEN + "Game Rules")) {
            return;
        }

        final org.bukkit.inventory.ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        final String itemDisplayName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
        GameRule<Boolean> gameRule = GAME_RULE_MAP.get(itemDisplayName);

        if (gameRule != null) {
            e.setCancelled(true);
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);

            boolean currentValue = Bukkit.getServer().getWorld("New World").getGameRuleValue(gameRule);
            boolean newValue = !currentValue;

            Bukkit.getServer().getWorld("New World").setGameRule(gameRule, newValue);

            String ruleName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
            String status = newValue ? "Enabled" : "Disabled";
            p.sendMessage(np + " " + p.getName() + " has " + status + " Game Rule " + ruleName);
        } else if (itemDisplayName.equals("Go Back")) {
            e.setCancelled(true);
            staffmenu.staffMenu(p);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
        }
    }

    private static ItemStack createGameRuleItem(String displayName, String lore, Material woolColor) {
        ItemStack item = new ItemStack(woolColor);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add(lore);
        itemMeta.setLore(itemLore);
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