package com.ula.purge.adminutils;

import com.ula.purge.ParticleMenu.ParticleMenu;
import com.ula.purge.Purge;
import com.ula.purge.players.playermenu;
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

import static com.ula.purge.Purge.np;

public class staffmenu implements Listener {
    private final Purge plugin;
    public staffmenu (Purge plugin){

        this.plugin = plugin;
    }

    public static String StaffTitle = ChatColor.translateAlternateColorCodes('&',"&4&lStaff Menu");

    public static void staffMenu(Player p){
        Inventory inv = Bukkit.createInventory(null, 18, StaffTitle);

        ItemStack vanish1 = new ItemStack(Material.WOODEN_AXE);
        ItemMeta vanish1meta = vanish1.getItemMeta();
        vanish1meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Vanish");
        vanish1.setItemMeta(vanish1meta);

        ItemStack RC = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta RCMeta = RC.getItemMeta();
        RCMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Game Rules");
        RC.setItemMeta(RCMeta);

        ItemStack Particles = new ItemStack(Material.BEDROCK);
        ItemMeta partMeta = Particles.getItemMeta();
        partMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Staff Particles");
        Particles.setItemMeta(partMeta);

        ItemStack Weather = new ItemStack(Material.QUARTZ_BLOCK);
        ItemMeta weatherm = Weather.getItemMeta();
        weatherm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Weather");
        Weather.setItemMeta(weatherm);

        ItemStack time = new ItemStack(Material.CLOCK);
        ItemMeta timem = time.getItemMeta();
        timem.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Time");
        time.setItemMeta(timem);

        ItemStack mode = new ItemStack(Material.WOODEN_SHOVEL);
        ItemMeta modem = mode.getItemMeta();
        modem.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "GameMode");
        mode.setItemMeta(modem);

        ItemStack qteleport = new ItemStack(Material.ELYTRA);
        ItemMeta qteleportm = qteleport.getItemMeta();
        qteleportm.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Quick Teleport");
        qteleport.setItemMeta(qteleportm);

        ItemStack mm = new ItemStack(Material.PAPER);
        ItemMeta mmeta = mm.getItemMeta();
        mmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Maintenance Mode");
        mm.setItemMeta(mmeta);

        ItemStack play = new ItemStack(Material.PAPER);
        ItemMeta playmeta = play.getItemMeta();
        playmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Players");
        play.setItemMeta(playmeta);

        ItemStack space = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta spacem = space.getItemMeta();
        spacem.setDisplayName(ChatColor.BOLD + "");
        space.setItemMeta(spacem);


        inv.setItem(0, vanish1);
        inv.setItem(1,space);
        inv.setItem(2, RC);
        inv.setItem(3,space);
        inv.setItem(4,Particles);
        inv.setItem(5,space);
        inv.setItem(6, Weather);
        inv.setItem(7,space);
        inv.setItem(8, time);
        inv.setItem(9,space);
        inv.setItem(10, mode);
        inv.setItem(11,space);
        inv.setItem(12,qteleport);
        inv.setItem(13,space);
        inv.setItem(14,mm);
        inv.setItem(15,space);
        inv.setItem(16, play);
        inv.setItem(17,space);


        p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void ItemClick(InventoryClickEvent e) {
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


        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Vanish")) {
            e.setCancelled(true);
            if(p.hasPermission("staff.vanish")) {
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                //vanish.openStaffVanishMenu(p);
            }else{
                p.sendMessage(np + "" + ChatColor.RED + "" + ChatColor.BOLD + "ACCESS DENIED! MUST BE 'TRAINEE' TO USE THIS COMMAND" );
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F,1F);
            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Game Rules")) {
            e.setCancelled(true);
            p.closeInventory();
            if(p.hasPermission("punish.fallen")){
                gamerules.showGameRules(p);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F,1F);
            }else{
                p.sendMessage(np + "" + ChatColor.RED + "" + ChatColor.BOLD + "ACCESS DENIED! MUST BE 'TRAINEE' TO USE THIS COMMAND" );
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F,1F);
            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Staff Particles")) {
            e.setCancelled(true);
            p.closeInventory();
            ParticleMenu.ParticleFire(p);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F,1F);
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Weather")) {
            e.setCancelled(true);
            if(p.hasPermission("admin.fallen")){
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F,1F);
                weather.staffW(p);
            }else{
                p.sendMessage(np + "" + ChatColor.RED + "" + ChatColor.BOLD + "ACCESS DENIED! MUST BE 'ADMIN' TO USE THIS COMMAND" );
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F,1F);
            }

        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Time")) {
            e.setCancelled(true);
            if(p.hasPermission("admin.fallen")){
                time.staffTimer(p);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F,1F);
            }else{
                p.sendMessage(np + "" + ChatColor.RED + "" + ChatColor.BOLD + "ACCESS DENIED! MUST BE 'ADMIN' TO USE THIS COMMAND" );
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F,1F);
            }
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "GameMode")) {
            e.setCancelled(true);
            p.closeInventory();
            gamemode.staffGamemode(p);
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F,1F);
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Quick Teleport")) {
            e.setCancelled(true);
            p.sendMessage(np + "" +ChatColor.RED + "" + ChatColor.BOLD + "COMING SOON");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F,1F);
        }
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Maintenance Mode")){
            e.setCancelled(true);
            if(p.hasPermission("admin.fallen")){
                maintenance.openMaintenanceMenu(p);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F,1F);
            }else{
                p.sendMessage(np + "" + ChatColor.RED + "" + ChatColor.BOLD + "ACCESS DENIED! MUST BE 'ADMIN' TO USE THIS COMMAND" );
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F,1F);
            }

        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Players")) {
            e.setCancelled(true);
            playermenu.playermenu(p);
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F,1F);
        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BOLD + "")) {
            e.setCancelled(true);
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F,1F);
        }
    }
}