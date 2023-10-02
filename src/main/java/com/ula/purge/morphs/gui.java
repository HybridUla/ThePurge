package com.ula.purge.morphs;

import com.ula.purge.Purge;
import de.slikey.effectlib.effect.DnaEffect;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class gui implements Listener {
    private final Purge plugin;

    public gui(Purge plugin) {

        this.plugin = plugin;
    }

    public static ArrayList<UUID> CreeperMorph = new ArrayList();
    public static ArrayList<UUID> SkeletonMorph = new ArrayList();
    public static ArrayList<UUID> SheepMorph = new ArrayList<>();
    public static ArrayList<UUID> PigMorph = new ArrayList<>();
    public static ArrayList<UUID> CowMorph = new ArrayList<>();
    public static ArrayList<UUID> ChickenMorph = new ArrayList<>();
    public static ArrayList<UUID> SquidMorph = new ArrayList<>();
    public static ArrayList<UUID> VillagerMorph = new ArrayList<>();

    //MorphAPI morphAPI = MorphPlugin.getInstance().getMorphAPI();


    public static String morph = ChatColor.translateAlternateColorCodes('&', "§fMorphs");

    public static void MorphMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, morph);


        ItemStack Creeper = new ItemStack(Material.CREEPER_SPAWN_EGG);
        ItemMeta CreeperMeta = Creeper.getItemMeta();
        CreeperMeta.setDisplayName("§fCreeper");
        Creeper.setItemMeta(CreeperMeta);

        ItemStack Skeleton = new ItemStack(Material.SKELETON_SPAWN_EGG);
        ItemMeta SkeletonMeta = Skeleton.getItemMeta();
        SkeletonMeta.setDisplayName("§fSkeleton");
        Skeleton.setItemMeta(SkeletonMeta);

        ItemStack Sheep = new ItemStack(Material.SHEEP_SPAWN_EGG);
        ItemMeta SheepMeta = Sheep.getItemMeta();
        SheepMeta.setDisplayName("§fSheep");
        Sheep.setItemMeta(SheepMeta);

        ItemStack Pig = new ItemStack(Material.PIG_SPAWN_EGG);
        ItemMeta PigMeta = Pig.getItemMeta();
        PigMeta.setDisplayName("§fPig");
        Pig.setItemMeta(PigMeta);

        ItemStack Cow = new ItemStack(Material.COW_SPAWN_EGG);
        ItemMeta CowMeta = Cow.getItemMeta();
        CowMeta.setDisplayName("§fCow");
        Cow.setItemMeta(CowMeta);

        ItemStack Chicken = new ItemStack(Material.CHICKEN_SPAWN_EGG);
        ItemMeta ChickenMeta = Chicken.getItemMeta();
        ChickenMeta.setDisplayName("§fChicken");
        Chicken.setItemMeta(ChickenMeta);

        ItemStack Squid = new ItemStack(Material.SQUID_SPAWN_EGG);
        ItemMeta SquidMeta = Squid.getItemMeta();
        SquidMeta.setDisplayName("§fSquid");
        Squid.setItemMeta(SquidMeta);

        ItemStack Villager = new ItemStack(Material.VILLAGER_SPAWN_EGG);
        ItemMeta VillagerMeta = Villager.getItemMeta();
        VillagerMeta.setDisplayName("§fVillager");
        Villager.setItemMeta(VillagerMeta);


        inv.setItem(20, Creeper);
        inv.setItem(21, Skeleton);
        inv.setItem(22, Sheep);
        inv.setItem(23, Pig);
        inv.setItem(24, Cow);
        inv.setItem(29, Chicken);
        inv.setItem(30, Squid);
        inv.setItem(31, Villager);

        p.openInventory(inv);
    }


    @EventHandler
    public void Shift(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();

        if (p.isSneaking()) {
            if (CreeperMorph.contains(p.getPlayer().getUniqueId())) {


                // Create the Effect and attach it to the Player
                DnaEffect DnaEffect = new DnaEffect(Purge.getInstance().effectManager);
                DnaEffect.setEntity(p);

                // Add a callback to the effect
                DnaEffect.callback = new Runnable() {

                    @Override
                    public void run() {
                        p.sendMessage("BOOOM");

                    }

                };
                // Bleeding takes 15 seconds
                // period * iterations = time of effect
                DnaEffect.iterations = 15 * 20;
                DnaEffect.start();

            }

        }

    }

    @EventHandler
    public void ItemClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory() == null || e.getClickedInventory() == null) {
            return;
        }
        final String invTitle = e.getView().getTitle();
        if (invTitle == null || !invTitle.equals(morph)) {
            return;
        }

        final ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta()) {
            return;
        }

        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§fCreeper")) {
            e.setCancelled(true);
            p.closeInventory();


            CreeperMorph.add(p.getPlayer().getUniqueId());

            Entity en = (Entity) p;
            DisguiseAPI.disguiseToAll(en, new MobDisguise(DisguiseType.CREEPER));
            p.sendMessage("You are now a creeper");
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);

        }
        if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BOLD + "")) {
            e.setCancelled(true);
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1F, 1F);
        }
    }


    @EventHandler
    public void Quit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        DisguiseAPI.undisguiseToAll(p, p.getPlayer());
        CreeperMorph.remove(p.getPlayer().getUniqueId());
        SkeletonMorph.remove(p.getPlayer().getUniqueId());
        SheepMorph.remove(p.getPlayer().getUniqueId());
        PigMorph.remove(p.getPlayer().getUniqueId());
        CowMorph.remove(p.getPlayer().getUniqueId());
        ChickenMorph.remove(p.getPlayer().getUniqueId());
        SquidMorph.remove(p.getPlayer().getUniqueId());
        VillagerMorph.remove(p.getPlayer().getUniqueId());

    }
}