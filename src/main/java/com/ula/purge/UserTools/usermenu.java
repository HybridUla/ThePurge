package com.ula.purge.UserTools;

import com.ula.purge.Purge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class usermenu implements Listener {

    private final Purge plugin;

    public usermenu(Purge plugin) {
        this.plugin = plugin;
    }

    String Prefix = "§f§l§nPHANTOM SURVIVAL > ";


    public static void MeMenu(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, 54, ChatColor.GREEN + "Welcome, " + p.getPlayer().getDisplayName());

        ItemStack WP = new ItemStack(Material.END_PORTAL_FRAME);
        ItemMeta WPMeta = WP.getItemMeta();
        WPMeta.setDisplayName("§fRTP");
        ArrayList<String> RTPLore = new ArrayList<>();
        RTPLore.add("");
        RTPLore.add("Randomly teleports you in this world");
        RTPLore.add("COST: $500");
        RTPLore.add("§6Click here to use");
        RTPLore.add("");
        WPMeta.setLore(RTPLore);
        WP.setItemMeta(WPMeta);

        ItemStack Map = new ItemStack(Material.MAP);
        ItemMeta MapMeta = Map.getItemMeta();
        MapMeta.setDisplayName("§fDynMap");
        ArrayList<String> MapLore = new ArrayList<>();
        MapLore.add("");
        MapLore.add("Gives you the link to a big website map");
        MapLore.add("§6Click here to use");
        MapLore.add("");
        MapMeta.setLore(MapLore);
        Map.setItemMeta(MapMeta);

        ItemStack Shop = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta ShopMeta = Shop.getItemMeta();
        ShopMeta.setDisplayName("§fSHOP");
        ArrayList<String> ShopLore = new ArrayList<>();
        ShopLore.add("");
        ShopLore.add("Opens up the shop menu to sell things");
        ShopLore.add("§6Click here to use");
        ShopLore.add("");
        MapMeta.setLore(ShopLore);
        Shop.setItemMeta(ShopMeta);

        ItemStack XP = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta XPMeta = XP.getItemMeta();
        XPMeta.setDisplayName("§fXPBoost");
        ArrayList<String> XPlore = new ArrayList<>();
        XPlore.add("");
        XPlore.add("Opens up the shop menu to sell things");
        XPlore.add("§6Click here to use");
        XPlore.add("");
        MapMeta.setLore(XPlore);
        XP.setItemMeta(XPMeta);

        ItemStack player = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playermeta = (SkullMeta)player.getItemMeta();
        playermeta.setOwner(p.getName());
        playermeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + p.getName());
        ArrayList<String> playerlore = new ArrayList();
        playerlore.add(" ");
        playerlore.add("Balance: " + Purge.econ.getBalance(p.getName()));
        playerlore.add(" ");
        playermeta.setLore(playerlore);
        player.setItemMeta(playermeta);

        ItemStack ChestAdmin = new ItemStack(Material.CHEST);
        ItemMeta ChestAdminMeta = ChestAdmin.getItemMeta();
        ChestAdminMeta.setDisplayName("§fAdmin Tools");
        ChestAdmin.setItemMeta(ChestAdminMeta);


        inv.setItem(20, WP);
        inv.setItem(22, Map);
        inv.setItem(24, Shop);
        inv.setItem(28, XP);

        inv.setItem(49, player);
        inv.setItem(50, ChestAdmin);
        p.openInventory(inv);

    }
}
