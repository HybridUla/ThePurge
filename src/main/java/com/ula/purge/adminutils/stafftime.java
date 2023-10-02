package com.ula.purge.adminutils;

import com.ula.purge.Purge;
import net.milkbowl.vault.chat.Chat;
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

import java.util.ArrayList;

import static com.ula.purge.Purge.chat;
import static com.ula.purge.commands.calculateTime;

public class stafftime implements Listener {
    private final Purge plugin;
    public stafftime (Purge plugin){

        this.plugin = plugin;
    }


    public static String StaffTitle = ChatColor.translateAlternateColorCodes('&',"&4&lStaff Time");

    public static void staffMenu(Player p){

        Inventory inv = Bukkit.createInventory(null, 18, StaffTitle);

        ItemStack vanish1 = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta) vanish1.getItemMeta();
        sm.setLocalizedName("Ula492");
        sm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Ula492");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(" ");
        lore.add("Rank: " + chat.getPlayerPrefix(Bukkit.getPlayer("Ula492")));

        lore.add(" ");
        if (Bukkit.getPlayerExact("Ula492").hasPlayedBefore()) {
            long ula = p.getStatistic(Statistic.PLAY_ONE_MINUTE);
            lore.add("Time Played: " + calculateTime(ula));
        } else {
            lore.add("Time Played: " + "User has never played!?");
        }
        lore.add(" ");
        sm.setLore(lore);
        vanish1.setItemMeta(sm);

        ItemStack gb = new ItemStack(Material.BARRIER);
        ItemMeta gbmeta = gb.getItemMeta();
        gbmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Go Back");
        gb.setItemMeta(gbmeta);
        inv.setItem(8,gb);


        inv.setItem(0,vanish1);


        p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void RewardClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }
        final String invTitle = e.getView().getTitle();
        if (invTitle == null || !invTitle.equals(StaffTitle)) {
            return;
        }

        final org.bukkit.inventory.ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
            return;
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Ula492")) {
            e.setCancelled(true);
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Go Back")) {
            e.setCancelled(true);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1F,1F);
            staffmenu.staffMenu(p);
        }
    }


/*
    public static long getOfflinePlayerStatistic(OfflinePlayer player, Statistic statistic) {
        //Default server world always be the first of the list
        //O mundo padrão sempre vai ser o primeiro da lista
        File worldFolder = new File(Bukkit.getServer().getWorlds().get(0).getWorldFolder(), "stats");
        File playerStatistics = new File(worldFolder, player.getUniqueId().toString() + ".json");
        if(playerStatistics.exists()){
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = null;
            try {
                jsonObject = (JsonObject) parser.parse(new FileReader(playerStatistics));
            } catch (IOException | ParseException e) {
                Bukkit.getLogger().log(Level.WARNING, "Falha ao ler o arquivo de estatisticas do jogador " + player.getName(), e);
            }
            StringBuilder statisticNmsName = new StringBuilder("stat.");
            for(char character : statistic.name().toCharArray()) {
                if(statisticNmsName.charAt(statisticNmsName.length() - 1) == '_') {
                    statisticNmsName.setCharAt(statisticNmsName.length() - 1, Character.toUpperCase(character));
                }else {
                    statisticNmsName.append(Character.toLowerCase(character));
                }
            }
            if(jsonObject.has(statisticNmsName.toString())) {

            }else {
                //This statistic has not yet been saved to file, so it is 0
                //Estatistica ainda não foi salva no arquivo, portato é 0
                return 0;
            }
        }
        //Any statistic can be -1?
        //Alguma estatistica pode virar -1?
        return -1;
    }
    */
}
