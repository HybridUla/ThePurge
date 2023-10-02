package com.ula.purge;

import com.ula.purge.UserTools.usermenu;
import com.ula.purge.adminutils.gamerules;
import com.ula.purge.adminutils.staffmenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.TimeUnit;

import static com.ula.purge.Purge.np;

public class commands implements CommandExecutor {
    public boolean onCommand(final CommandSender cs, Command cmd, String label, final String[] args) {

        if (cs instanceof Player) {
            Player p = (Player) cs;
            if(cmd.getName().equalsIgnoreCase("Mtest")){
                gamerules.showGameRules(p);
            }
            if(cmd.getName().equalsIgnoreCase("c")){
                usermenu.MeMenu(p);
            }
            if(cmd.getName().equalsIgnoreCase("Staff")){
                if(p.hasPermission("staff.fallen")){
                    staffmenu.staffMenu(p);
                }else{
                    p.sendMessage(np + "You must be a staff member to run this command!");
                }
            }
            if(cmd.getName().equalsIgnoreCase("StartShow")){

            }
            if(cmd.getName().equalsIgnoreCase("rwvillager")){
                // member.rewardGUI(p);
                //p.sendMessage(np + "The Ancient Villager is seen coming far from home... Those who wait will receive rewards.");
                // p.playSound(p.getLocation(), Sound.BLOCK_CHEST_LOCKED,1F,1F);
            }
            if(cmd.getName().equalsIgnoreCase("mm")){
                p.sendMessage("MM Works");
            }


            if(cmd.getName().equalsIgnoreCase("CoinV")){
                if(p.hasPermission("admin.fallen")){

                    ItemStack Emerald = new ItemStack(Material.PAPER);
                    ItemMeta EmeraldMeta = Emerald.getItemMeta();
                    EmeraldMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "150");
                    Emerald.setItemMeta(EmeraldMeta);

                    ItemStack NS = new ItemStack(Material.PAPER);
                    ItemMeta NSM = NS.getItemMeta();
                    NSM.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "250");
                    NS.setItemMeta(NSM);

                    ItemStack ES = new ItemStack(Material.PAPER);
                    ItemMeta ESM = ES.getItemMeta();
                    ESM.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "300");
                    ES.setItemMeta(ESM);

                    ItemStack SS = new ItemStack(Material.PAPER);
                    ItemMeta SSM = SS.getItemMeta();
                    SSM.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "500");
                    SS.setItemMeta(SSM);

                    ItemStack WS = new ItemStack(Material.PAPER);
                    ItemMeta WSM = WS.getItemMeta();
                    WSM.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "600");
                    WS.setItemMeta(WSM);

                    p.getInventory().addItem(Emerald);
                    p.getInventory().addItem(NS);
                    p.getInventory().addItem(ES);
                    p.getInventory().addItem(SS);
                    p.getInventory().addItem(WS);

                }else{
                    p.sendMessage(np + "You must be a staff member to run this command!");
                }
            }



        }
        return true;
    }


    public static String calculateTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        return day + " days " + hours + " hours " + minute + " minutes " + second + " seconds ";

    }
}